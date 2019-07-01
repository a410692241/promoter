package com.linayi.service.correct.impl;

import com.linayi.dao.correct.CorrectLogMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.correct.PriceAuditTaskMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.account.AdminAccount;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.CorrectLog;
import com.linayi.entity.correct.PriceAuditTask;
import com.linayi.entity.correct.SupermarketGoodsVersion;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.entity.supermarket.Supermarket;
import com.linayi.entity.user.User;
import com.linayi.enums.CorrectStatus;
import com.linayi.enums.CorrectType;
import com.linayi.enums.OperatorType;
import com.linayi.enums.PriceType;
import com.linayi.exception.BusinessException;
import com.linayi.exception.ErrorType;
import com.linayi.service.community.CommunitySupermarketService;
import com.linayi.service.correct.CorrectService;
import com.linayi.service.correct.SupermarketGoodsVersionService;
import com.linayi.service.goods.GoodsSkuService;
import com.linayi.service.goods.SupermarketGoodsService;
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.service.user.UserService;
import com.linayi.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CorrectServiceImpl implements CorrectService {
    @Autowired
    private CorrectMapper correctMapper;
    @Autowired
    private SupermarketService supermarketService;
    @Autowired
    private UserService userService;
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private SupermarketMapper supermarketMapper;
    @Autowired
    private CorrectService correctService;
    @Autowired
    private CorrectLogMapper correctLogMapper;
    @Autowired
    private SupermarketGoodsMapper supermarketGoodsMapper;
    @Autowired
    private CommunitySupermarketService communitySupermarketService;
    @Resource
    private SupermarketGoodsVersionService supermarketGoodsVersionService;
    @Autowired
    private SupermarketGoodsService supermarketGoodsService;
    @Autowired
    private PriceAuditTaskMapper priceAuditTaskMapper;


    /*添加分享价格申请*/
    @Override
    @Transactional
    public Correct share(Correct correct, MultipartFile file, String userType) {
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(correct.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        if (OperatorType.USER.toString().equals(userType)) {
            User currentUser = userService.selectUserByuserId(correct.getUserId());
            if ("FALSE".equals(currentUser.getIsSharer())) {
                throw new BusinessException(ErrorType.NOT_SHARER);
            }
        }

        Date now = new Date();
        Correct param = new Correct();
        param.setSupermarketId(correct.getSupermarketId());
        param.setGoodsSkuId(correct.getGoodsSkuId());
        List<String> statusList = new ArrayList<>();
        statusList.add(CorrectStatus.WAIT_AUDIT.toString());
        statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
        statusList.add(CorrectStatus.AFFECTED.toString());
        param.setStatusList(statusList);

        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);

        if (currentCorrect != null) {
            throw new BusinessException(ErrorType.HAVE_MAN_SHARE_ERROR);

        }

        String path = null;
        try {
            path = ImageUtil.handleUpload(file);
            correct.setImage(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入correct表
        //判断分享纠错价格是否是所有生效价格的最低价，是：状态为待审核，否：为审核通过；
        SupermarketGoods supermarketGoods = new SupermarketGoods();
        supermarketGoods.setGoodsSkuId(correct.getGoodsSkuId());
        List<SupermarketGoods> supermarketGoodsList = supermarketGoodsMapper.getSupermarketGoods(supermarketGoods);
        if (supermarketGoodsList.size() == 0 || correct.getPrice() < supermarketGoodsList.get(0).getPrice()) {
            correct.setStatus(CorrectStatus.WAIT_AUDIT.toString());
        } else {
            correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
        }

        //立即生效专用
        if ("immediatelyAffect".equals(correct.getName())) {
            correct.setStatus(CorrectStatus.AFFECTED.toString());
            correct.setActualStartTime(new Date());
        }
        correct.setAuditLastTime(now);
        correct.setCreateTime(now);
        correct.setUpdateTime(now);
        correct.setType(CorrectType.SHARE.toString());
        if (OperatorType.USER.toString().equals(userType)) {
            correct.setUserType(OperatorType.USER.getOperatorTypeName());
        }
        if (OperatorType.ADMIN.toString().equals(userType)) {
            correct.setUserType(OperatorType.ADMIN.getOperatorTypeName());
        }
        correctMapper.insert(correct);

        //插入correct_log表
        CorrectLog correctLog = new CorrectLog();
        correctLog.setCorrectId(correct.getCorrectId());
        correctLog.setOperateStatus(CorrectStatus.WAIT_AUDIT.toString());
        correctLog.setOperatorId(correct.getUserId());
        if (OperatorType.ADMIN.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        if (OperatorType.USER.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        correctLog.setCreateTime(now);
        correctLogMapper.insert(correctLog);

        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }

        return correct;
    }

    /*添加纠错价格申请*/
    @Override
    @Transactional
    public Correct correct(Correct correct, MultipartFile file, String userType) {
//        Correct paramSamePrice = new Correct();
//        paramSamePrice.setCorrectId(correct.getParentId());
//        Correct currentCorrect2 = correctMapper.query(paramSamePrice).stream().findFirst().orElse(null);
//        if(correct.getPrice() == currentCorrect2.getPrice()){
//            throw new BusinessException(ErrorType.CORRECT_SAME_PRICE);
//        }
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(correct.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        if (OperatorType.USER.toString().equals(userType)) {
            User currentUser = userService.selectUserByuserId(correct.getUserId());
            if ("FALSE".equals(currentUser.getIsSharer())) {
                throw new BusinessException(ErrorType.NOT_SHARER);
            }
        }

        Date now = new Date();

        Correct param = new Correct();
        param.setCorrectId(correct.getParentId());
        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);
        if (!CorrectStatus.AFFECTED.toString().equals(currentCorrect.getStatus())) {
            throw new BusinessException(ErrorType.HAVE_MAN_CORRECT_ERROR);
        }

//        //判断用户只能纠错自己分享的生效价格
//        if(!(correct.getUserId().equals(currentCorrect.getUserId()))){
//        	throw new BusinessException(ErrorType.JUST_CORRECT_MINE);
//        }

        //图片处理
        if (OperatorType.USER.toString().equals(userType)) {
            String path = null;
            try {
                path = ImageUtil.handleUpload(file);
                correct.setImage(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        //插入correct表
        //判断分享纠错价格是否是所有生效价格的最低价，是：状态为待审核，否：为审核通过；
        SupermarketGoods supermarketGoods = new SupermarketGoods();
        supermarketGoods.setGoodsSkuId(correct.getGoodsSkuId());
        List<SupermarketGoods> supermarketGoodsList = supermarketGoodsMapper.getSupermarketGoods(supermarketGoods);
        if (supermarketGoodsList.size() == 0 || correct.getPrice() < supermarketGoodsList.get(0).getPrice()) {
            correct.setStatus(CorrectStatus.WAIT_AUDIT.toString());
        } else {
            correct.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
        }

        //立即生效专用
        if ("immediatelyAffect".equals(correct.getName())) {
            correct.setStatus(CorrectStatus.AFFECTED.toString());
            correct.setActualStartTime(new Date());
            Correct correctExpire = new Correct();
            correctExpire.setCorrectId(correct.getParentId());
            correctExpire.setActualEndTime(new Date());
            correctExpire.setUpdateTime(new Date());
            correctExpire.setStatus(CorrectStatus.EXPIRED.toString());
            this.updateCorrect(correctExpire);
            //插入纠错日志数据
            CorrectLog correctLog = new CorrectLog();
            correctLog.setCorrectId(correctExpire.getCorrectId());
            correctLog.setOperatorId(correct.getUserId());
            correctLog.setOperateStatus(CorrectStatus.EXPIRED.toString());
            correctLog.setOperatorType(OperatorType.ADMIN.toString());
            correctLog.setCreateTime(now);
            correctLogMapper.insert(correctLog);
        }
        correct.setAuditLastTime(now);
        correct.setCreateTime(now);
        correct.setUpdateTime(now);
        correct.setType(CorrectType.CORRECT.toString());
        if (OperatorType.USER.toString().equals(userType)) {
            correct.setUserType(OperatorType.USER.getOperatorTypeName());
        }
        if (OperatorType.ADMIN.toString().equals(userType)) {
            correct.setUserType(OperatorType.ADMIN.getOperatorTypeName());
        }
        correctMapper.insert(correct);

        //插入correct_log表
        CorrectLog correctLog = new CorrectLog();
        correctLog.setCorrectId(correct.getCorrectId());

        //立即生效专用
        if ("immediatelyAffect".equals(correct.getName())) {
            correctLog.setOperateStatus(correct.getStatus());
        } else {
            correctLog.setOperateStatus(CorrectStatus.WAIT_AUDIT.toString());
        }

        correctLog.setOperatorId(correct.getUserId());
        if (OperatorType.ADMIN.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        if (OperatorType.USER.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        correctLog.setCreateTime(now);
        correctLogMapper.insert(correctLog);

        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }

        return correct;
    }

    /*查看页面*/
    @Override
    public Correct showView(Long correctId) {
        Correct currentCorrect = correctMapper.selectByPrimaryKey(correctId);
        User user = userService.selectUserByuserId(currentCorrect.getUserId());
        currentCorrect.setNickName(user.getNickname());

        if (PriceType.NORMAL.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("正常价");
        }
        if (PriceType.PROMOTION.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("促销价");
        }
        if (PriceType.DEAL.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("处理价");
        }
        if (PriceType.MEMBER.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("会员价");
        }

        return currentCorrect;
    }

    /*撤回*/
    /*纠错分享审核*/

    @Override
    @Transactional
    public Correct recall(Correct correct, String userType) {
        Correct correctParam = correctMapper.selectByPrimaryKey(correct.getCorrectId());
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(correctParam.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(correctParam.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        if (OperatorType.USER.toString().equals(userType)) {
            User currentUser = userService.selectUserByuserId(correct.getUserId());
            if ("FALSE".equals(currentUser.getIsSharer())) {
                throw new BusinessException(ErrorType.NOT_SHARER);
            }
        }

        Date now = new Date();

        Correct param = new Correct();
        param.setCorrectId(correct.getCorrectId());
        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);

       /* //判断用户不能撤回别人正在纠错的记录
        if (OperatorType.USER.toString().equals(userType) && !(correct.getUserId().equals(currentCorrect.getUserId()))) {
            throw new BusinessException(ErrorType.NOT_YOUR_CORRECT);
        }
*/
        if (!(CorrectStatus.WAIT_AUDIT.toString().equals(currentCorrect.getStatus()) ||
                CorrectStatus.AUDIT_SUCCESS.toString().equals(currentCorrect.getStatus()))) {
            throw new BusinessException(ErrorType.HAVE_MAN_RECALL_ERROR);
        }
        //修改correct数据状态
        Correct correct1 = new Correct();
        correct1.setCorrectId(correct.getCorrectId());
        correct1.setStatus(CorrectStatus.RECALL.toString());
        correctMapper.updateCorrect(correct1);
        //插入correct_log表
        CorrectLog correctLog = new CorrectLog();
        correctLog.setCorrectId(correct.getCorrectId());
        correctLog.setOperateStatus(CorrectStatus.RECALL.toString());
        correctLog.setOperatorId(correct.getUserId());
        if (OperatorType.ADMIN.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        if (OperatorType.USER.toString().equals(userType)) {
            correctLog.setOperatorType(userType);
        }
        correctLog.setCreateTime(now);
        correctLogMapper.insert(correctLog);

        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }
        return correctParam;
    }

    @Override
    @Transactional
    public void audit(Correct correct) {
        Correct newCorrect = correctMapper.selectByPrimaryKey(correct.getCorrectId());

        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(newCorrect.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(newCorrect.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        Date now = new Date();
        if (CorrectStatus.WAIT_AUDIT.toString().equals(newCorrect.getStatus())) {
            Correct param = new Correct();
            param.setCorrectId(correct.getCorrectId());
            param.setStatus(correct.getStatus());
            param.setUpdateTime(now);
            param.setAuditTime(now);
            param.setAuditLastTime(now);
            param.setAuditerId(correct.getUserId());
            param.setAuditType(correct.getAuditType());
            param.setStatusAfterAffect(correct.getStatus());
            correctMapper.updateCorrect(param);

            //插入correct_log表
            CorrectLog correctLog = new CorrectLog();
            correctLog.setCorrectId(correct.getCorrectId());
            correctLog.setOperateStatus(correct.getStatus());
            correctLog.setOperatorId(correct.getUserId());
            correctLog.setOperatorType(OperatorType.ADMIN.toString());
            correctLog.setCreateTime(now);
            correctLogMapper.insert(correctLog);

            if (CorrectStatus.AUDIT_FAIL.toString().equals(correct.getStatus()) && OperatorType.USER.toString().equals(correct.getAuditType())) {
                if (correct.getParentId() == null) {
                    Correct param2 = new Correct();
                    param2.setSupermarketId(correct.getSupermarketId());
                    param2.setGoodsSkuId(correct.getGoodsSkuId());
                    List<String> statusList = new ArrayList<>();
                    statusList.add(CorrectStatus.WAIT_AUDIT.toString());
                    statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
                    statusList.add(CorrectStatus.AFFECTED.toString());
                    param2.setStatusList(statusList);
                    Correct currentCorrect = correctMapper.query(param2).stream().findFirst().orElse(null);
                    if (currentCorrect != null) {
                        throw new BusinessException(ErrorType.HAVE_MAN_SHARE_ERROR);
                    } else {
                        correct.setType(CorrectType.SHARE.toString());
                    }
                }

                if (correct.getParentId() != null) {
                    Correct param3 = new Correct();
                    param3.setCorrectId(correct.getParentId());
                    Correct currentCorrect2 = correctMapper.query(param3).stream().findFirst().orElse(null);
                    if (!CorrectStatus.AFFECTED.toString().equals(currentCorrect2.getStatus())) {
                        throw new BusinessException(ErrorType.HAVE_MAN_CORRECT_ERROR);
                    } else {
                        correct.setType(CorrectType.CORRECT.toString());
                    }
                }

            }


        } else {
            throw new BusinessException(ErrorType.AUDIT_ERROR);
        }

        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }

    }


    @Override
    public List<Correct> page(Correct correct) {


   /*     correct.setStartTime(correct.getStartTim());
        correct.setEndTime(correct.getEndTim());
        Integer currentPage = correct.getCurrentPage();
        Integer pageSize = correct.getPageSize();
        correct.setCurrentPage(null);
        correct.setPageSize(null);
        List<Correct> corrects = correctMapper.queryPage(correct);
        List<Correct> correctList1 = correctMapper.queryAdminPage(correct);
        correctList1.stream().forEach(item -> {
            AdminAccount adminAccount = item.getAdminAccount();
            User user = new User();
            if (adminAccount != null) {
                user.setRealName(adminAccount.getUserName());
                user.setMobile(item.getMobile());
            }else {
                user.setRealName("");
                user.setMobile("");
            }

            item.setUser(user);
        });
        corrects.addAll(correctList1);
        List<Supermarket> supermarketList = supermarketMapper.selectAll(new Supermarket());
        //转化成key为supermarketId的hashMap
        Map<Integer, List<Supermarket>> supermarketMap = supermarketList.stream().collect(Collectors.groupingBy(Supermarket::getSupermarketId));
        corrects.parallelStream().forEach(item -> {
            Supermarket supermarket = supermarketMap.get(item.getSupermarketId()).stream().findFirst().orElse(null);

            if (supermarket != null) {
                String name = supermarket.getName();
                item.getSupermarket().setName(name);
            }
        });
        corrects.sort((a, b) -> {
            return b.getCreateTime().compareTo(a.getCreateTime());
        });
        int fromIndex = (currentPage - 1) * pageSize;
        List<Correct> correctResp = null;
        int toIndex = currentPage * pageSize;

        if (corrects.size() <= toIndex) {
            correctResp = corrects.subList(fromIndex, fromIndex + (corrects.size() % pageSize));
        }else{
            correctResp = corrects.subList(fromIndex, toIndex);
        }
        PageResult<Correct> page = new PageResult<>(correctResp, corrects.size());*/
        return correctMapper.queryPage(correct);
    }


    @Override
    public List<Correct> getList(Correct correct) {
        List<Correct> corrects = correctMapper.queryPage(correct);
        List<Correct> correctList1 = correctMapper.queryAdminPage(correct);
        correctList1.stream().forEach(item -> {
            AdminAccount adminAccount = item.getAdminAccount();
            User user = new User();
            if (adminAccount != null) {
                user.setRealName(adminAccount.getUserName());
                user.setMobile(item.getMobile());
            } else {
                user.setRealName("");
                user.setMobile("");
            }

            item.setUser(user);
        });
        corrects.addAll(correctList1);
        List<Supermarket> supermarketList = supermarketMapper.selectAll(new Supermarket());
        //转化成key为supermarketId的hashMap
        Map<Integer, List<Supermarket>> supermarketMap = supermarketList.stream().collect(Collectors.groupingBy(Supermarket::getSupermarketId));
        corrects.parallelStream().forEach(item -> {
            Supermarket supermarket = supermarketMap.get(item.getSupermarketId()).stream().findFirst().orElse(null);

            if (supermarket != null) {
                String name = supermarket.getName();
                item.getSupermarket().setName(name);
            }
        });
        corrects.sort((a, b) -> {
            return b.getCreateTime().compareTo(a.getCreateTime());
        });
        return corrects;
    }


    @Override
    public Correct selectByCorrectId(Long correctId) {
        Correct correct = correctMapper.selectByPrimaryKey(correctId);
        if (correct != null) {
            correct.setSupermarket(supermarketService.selectSupermarketBysupermarketId(correct.getSupermarketId()));
            correct.setUser(userService.selectUserByuserId(correct.getUserId()));
            correct.setGoodsSku(goodsSkuService.getGoodsSkuById(correct.getGoodsSkuId()));
        }
        if (correct.getImage() != null) {
            correct.setImage(Configuration.getConfig().getValue("imageServer") + "/" + correct.getImage());
        }
        //设置上次纠错人的证据照片
        if (correct != null && correct.getParentId() != null) {
            /*if (correct.getParentId() == -1) {
                correct.setParentImage(Configuration.getConfig().getValue("imageServer") + "/" +correct.getImage());
            } else {*/
            Correct correctPare = correctMapper.selectByPrimaryKey(correct.getParentId());
            if (correctPare != null && correctPare.getImage() != null) {
                correct.setParentImage(Configuration.getConfig().getValue("imageServer") + "/" + correctPare.getImage());
            }
            /*}*/

        } else if (correct.getParentId() == null) {
            correct.setParentImage("http://www.laykj.cn/wherebuy/images/2019/04/26/10/0b3db243-6338-4c4c-99a8-ccb26b623837.jpg");
        }
        if (CheckUtil.isNullEmpty(correct.getParentImage())) {
            correct.setParentImage("http://www.laykj.cn/wherebuy/images/2019/04/26/10/0b3db243-6338-4c4c-99a8-ccb26b623837.jpg");
        }
        if (CheckUtil.isNullEmpty(correct.getImage())) {
            correct.setImage("http://www.laykj.cn/wherebuy/images/2019/04/26/10/0b3db243-6338-4c4c-99a8-ccb26b623837.jpg");
        }
        return correct;
    }


    @Override
    public Integer upsertSelective(Correct correct) {
        Integer integer = correctMapper.insert(correct);
        return integer;
    }

    @Override
    public Integer updateCorrect(Correct correct) {
        return correctMapper.updateCorrect(correct);
    }

    public List<Correct> getCorrect(Correct correct) {
        return correctMapper.getCorrect(correct);
    }

    public List<Correct> getCorrectExpire(Correct correct) {
        return correctMapper.getCorrectExpire(correct);
    }

    /*生效定时器*/
    @Override
    @Transactional
    public void priceAffect(Correct corrects) {
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(corrects.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(corrects.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);
        long goodsId = 0l;
        //再次判断是否审核通过
        if (CorrectStatus.AUDIT_SUCCESS.toString().equals(corrects.getStatus())) {
            Date now = new Date();
            //判断是否为生效和过期时间之间
            if (now.after(corrects.getStartTime()) && now.before(corrects.getEndTime())) {
                //删除旧超市商品价格表
                supermarketGoodsMapper.deleteSupermarketGoods(corrects.getSupermarketId(), corrects.getGoodsSkuId());
                //插入新超市商品价格表
                SupermarketGoods supermarketGoods = new SupermarketGoods();
                supermarketGoods.setSupermarketId(corrects.getSupermarketId());
                supermarketGoods.setGoodsSkuId(corrects.getGoodsSkuId());
                goodsId = corrects.getGoodsSkuId();
                supermarketGoods.setPrice(corrects.getPrice());
                supermarketGoods.setCorrectId(corrects.getCorrectId());
                supermarketGoodsMapper.insert(supermarketGoods);
                //修改状态为生效
                corrects.setStatus(CorrectStatus.AFFECTED.toString());
                corrects.setActualStartTime(now);
                corrects.setUpdateTime(now);
                this.updateCorrect(corrects);

                //判断表格是否有纠错父id
                if (corrects.getParentId() != null) {
                    if (CorrectStatus.AFFECTED.toString().equals(correctService.selectByCorrectId(corrects.getParentId()).getStatus())) {
                        Correct correct = new Correct();
                        correct.setStatus(CorrectStatus.EXPIRED.toString());
                        correct.setActualEndTime(now);
                        correct.setUpdateTime(now);
                        correct.setCorrectId(corrects.getParentId());
                        this.updateCorrect(correct);
                        //插入纠错日志数据
                        CorrectLog correctLog = new CorrectLog();
                        correctLog.setCorrectId(correct.getCorrectId());
                        correctLog.setOperateStatus(correct.getStatus());
                        correctLog.setOperatorType(OperatorType.ADMIN.toString());
                        correctLog.setCreateTime(now);
                        correctLogMapper.insert(correctLog);
                    }
                }
                //插入纠错日志数据
                CorrectLog correctLog = new CorrectLog();
                correctLog.setCorrectId(corrects.getCorrectId());
                correctLog.setOperateStatus(CorrectStatus.AFFECTED.toString());
                correctLog.setOperatorType(OperatorType.ADMIN.toString());
                correctLog.setCreateTime(now);
                correctLogMapper.insert(correctLog);
                //调整商品的最高价和最低价
                List<Integer> communityIdList = communitySupermarketService.getCommunityIdBysupermarketId(corrects.getSupermarketId());
                for (Integer communityId : communityIdList) {
                    communitySupermarketService.toUpdateCommunityPrice(communityId, corrects.getGoodsSkuId().intValue());
                }
            } else {
                throw new BusinessException(ErrorType.SYSTEM_ERROR);
            }
        }

        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }
    }

    /*过期定时器*/
    @Override
    @Transactional
    public void priceExpire(Correct corrects) {
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(corrects.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(corrects.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        //再次判断是否审核通过
        if (CorrectStatus.AFFECTED.toString().equals(corrects.getStatus())) {
            Date now = new Date();
            //删除旧超市商品价格表
            supermarketGoodsMapper.deleteSupermarketGoods(corrects.getSupermarketId(), corrects.getGoodsSkuId());
            //修改状态为过期
            corrects.setStatus(CorrectStatus.EXPIRED.toString());
            corrects.setActualEndTime(now);
            corrects.setUpdateTime(now);
            this.updateCorrect(corrects);
            //插入纠错日志数据
            CorrectLog correctLog = new CorrectLog();
            correctLog.setCorrectId(corrects.getCorrectId());
            correctLog.setOperateStatus(CorrectStatus.EXPIRED.toString());
            correctLog.setOperatorType(OperatorType.ADMIN.toString());
            correctLog.setCreateTime(now);
            correctLogMapper.insert(correctLog);
            System.out.println("changeCorrectId:" + corrects.getCorrectId());
            //调整商品的最高价和最低价
            List<Integer> communityIdList = communitySupermarketService.getCommunityIdBysupermarketId(corrects.getSupermarketId());
            for (Integer communityId : communityIdList) {
                communitySupermarketService.toUpdateCommunityPrice(communityId, corrects.getGoodsSkuId().intValue());
            }
        }
        // 线程安全并发处理
        int count = supermarketGoodsVersionService.updateVersion(version);
        if (count <= 0) {
            throw new BusinessException(ErrorType.OPERATION_FAIL);
        }
    }

    @Override
    public List<Correct> selectCorrectListByUserId(Integer userId) {
        List<Correct> correctList = correctMapper.selectCorrectListByUserId(userId);
        if (correctList.size() > 0) {
            //根据状态set历史按钮类型
            for (Correct cc : correctList) {

                cc.setImage(ImageUtil.dealToShow(cc.getImage()));
                if (CorrectStatus.WAIT_AUDIT.toString().equals(cc.getStatus()) || CorrectStatus.AUDIT_SUCCESS.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("RECALL");
                } else if (CorrectStatus.AFFECTED.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("CORRECT");
                } else if (CorrectStatus.AUDIT_FAIL.toString().equals(cc.getStatus()) || CorrectStatus.RECALL.toString().equals(cc.getStatus()) || CorrectStatus.EXPIRED.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("VIEW");
                }
            }
        }
        return correctList;
    }

    @Override
    public Correct historyView(Correct correct) {
        Correct currentCorrect = correctMapper.query(correct).stream().findFirst().orElse(null);

        if (PriceType.NORMAL.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("正常价");
        }
        if (PriceType.PROMOTION.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("促销价");
        }
        if (PriceType.DEAL.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("处理价");
        }
        if (PriceType.MEMBER.toString().equals(currentCorrect.getPriceType())) {
            currentCorrect.setPriceTypeForCha("会员价");
        }
        return currentCorrect;
    }


    @Override
    public List<Correct> selectCorrectListByGoodsName(Correct correct) {
        List<Correct> correctList = correctMapper.selectCorrectListByGoodsName(correct);

        if (correctList.size() > 0) {
            // 根据状态set历史按钮类型
            for (Correct cc : correctList) {

                cc.setGoodsImage(ImageUtil.dealToShow(cc.getGoodsImage()));
                if (CorrectStatus.WAIT_AUDIT.toString().equals(cc.getStatus())
                        || CorrectStatus.AUDIT_SUCCESS.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("RECALL");
                } else if (CorrectStatus.AFFECTED.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("CORRECT");
                } else if (CorrectStatus.AUDIT_FAIL.toString().equals(cc.getStatus())
                        || CorrectStatus.RECALL.toString().equals(cc.getStatus())
                        || CorrectStatus.EXPIRED.toString().equals(cc.getStatus())) {
                    cc.setHistoryButtonType("VIEW");
                }
            }
        }
        return correctList;
    }

    @Override
    public List<Correct> getOtherPrice(Integer goodsSkuId) {


        return correctMapper.getOtherPrice(goodsSkuId);
    }

    //采价员查看待审核纠错记录（指定一家超市）
    @Override
    public List<Correct> getWaitAuditCorrect(Correct correct) {
        //TODO
        //获取采价员绑定的超市id
//        Supermarket supermarket = supermarketService.getSupermarketByProcurerId(correct.getUserId());
//        if(correct.getSupermarket() == null){
//            throw new BusinessException(ErrorType.NOT_PROCURER_NO_AUDIT);
//        }
        correct.setSupermarketId(correct.getSupermarket().getSupermarketId());
        //获取待审核列表
        List<Correct> correctList = correctMapper.getWaitAuditCorrectBySupermerketId(correct);

        //图片处理
        for (Correct currentCorrect : correctList) {
            String Image = ImageUtil.dealToShow(currentCorrect.getGoodsImage());
            currentCorrect.setGoodsImage(Image);
//            currentCorrect.setSupermarkerName(supermarket.getName());
        }
        return correctList;
    }

    @Override
    public List<Correct> getCorrectByAuditerId(Correct correct) {

        List<Correct> correctList = correctMapper.getCorrectByAuditerId(correct);

        //图片处理
        for (Correct currentCorrect : correctList) {
            String Image = ImageUtil.dealToShow(currentCorrect.getGoodsImage());
            currentCorrect.setGoodsImage(Image);
//            currentCorrect.setSupermarkerName(supermarket.getName());
        }
        return correctList;
    }

    @Override
    public List<Correct> getAffectedMinPrice(Correct correct) throws ParseException {
        List<Correct> correctList = correctMapper.getAffectedMinPrice(correct);
//        List<Correct> resultList = new ArrayList<Correct>();

        for (Correct thisCorrect : correctList) {
            Correct currentCorrect = correctMapper.getcorrectTimeByGoodsSkuId(thisCorrect.getGoodsSkuId(), supermarketMapper.getSupermarketIdByName(thisCorrect.getName())).stream().findFirst().orElse(null);
            if (currentCorrect != null) {
                thisCorrect.setActualStartTime(currentCorrect.getActualStartTime());
                thisCorrect.setCreateTime(currentCorrect.getCreateTime());
                System.out.println("开始时间：" + thisCorrect.getActualStartTime());

//               resultList.add(thisCorrect);
//
//               if(!"".equals(correct.getCreateTimeStart()) && correct.getCreateTimeStart() != null && !"".equals(correct.getCreateTimeEnd()) && correct.getCreateTimeEnd() !=null){
//                   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                   Date startTime=simpleDateFormat.parse(correct.getCreateTimeStart());
//                   Date endTime=simpleDateFormat.parse(correct.getCreateTimeEnd());
//
//                    if( thisCorrect.getActualStartTime().after(endTime) || thisCorrect.getActualStartTime().before(startTime)){
//                        resultList.remove(thisCorrect);
//                    }
//               }
            }
        }

        //排序
//            Collections.sort(correctList, (o1, o2) -> (int) (o2.getActualStartTime().compareTo(o1.getActualStartTime())));

        return correctList;
    }

    @Override
    @Transactional
    public void priceImmediatelyAffect(Correct correct, MultipartFile file) {
        correct.setName("immediatelyAffect");
        String userType = OperatorType.ADMIN.toString();
        if ("SHARE".equals(correct.getCorrectType())) {
            if (correct.getCorrectId() != null) {
                correct.setCorrectId(null);
            }
            // 线程安全并发处理
            initVersion(correct);
            correctService.share(correct, file, OperatorType.ADMIN.getOperatorTypeName());
            //插入后，调整商品的最高价和最低价
            correctService.adjustPriceMaxAndMin(correct);
        }
        if ("CORRECT".equals(correct.getCorrectType())) {
            if (correct.getCorrectId() != null) {
                correct.setParentId(correct.getCorrectId());
                correct.setCorrectId(null);
            }
            correctService.correct(correct, file, OperatorType.ADMIN.getOperatorTypeName());
            //插入后，调整商品的最高价和最低价
            correctService.adjustPriceMaxAndMin(correct);
        }

        if ("VIEW".equals(correct.getCorrectType())) {
            //调用撤回方法
            Correct currentCorrect = correctService.recall(correct, userType);

            //撤回后,重新通过超市id和商品id查询该商品的状态(可纠错,可分享,可查看)
            Supermarket supermarket = supermarketGoodsService.getCorrectTypeBySupermarketIdAndgoodsSkuId(correct.getGoodsSkuId(), correct.getSupermarketId());
            if ("CORRECT".equals(supermarket.getCorrectType())) {
                if (correct.getCorrectId() != null) {
                    correct.setCorrectId(null);
                }
                correct.setParentId(currentCorrect.getParentId());
                correctService.correct(correct, file, OperatorType.ADMIN.getOperatorTypeName());
                //插入后，调整商品的最高价和最低价
                correctService.adjustPriceMaxAndMin(correct);
            } else if ("SHARE".equals(supermarket.getCorrectType())) {
                if (correct.getCorrectId() != null) {
                    correct.setCorrectId(null);
                }
                // 线程安全并发处理
                initVersion(correct);
                correctService.share(correct, file, OperatorType.ADMIN.getOperatorTypeName());
                //插入后，调整商品的最高价和最低价
                correctService.adjustPriceMaxAndMin(correct);
            } else {
                throw new BusinessException(ErrorType.SYSTEM_ERROR);
            }
        }

    }

    @Override
    public void adjustPriceMaxAndMin(Correct corrects) {
        //删除旧超市商品价格表
        supermarketGoodsMapper.deleteSupermarketGoods(corrects.getSupermarketId(), corrects.getGoodsSkuId());
        //插入新超市商品价格表
        SupermarketGoods supermarketGoods = new SupermarketGoods();
        supermarketGoods.setSupermarketId(corrects.getSupermarketId());
        supermarketGoods.setGoodsSkuId(corrects.getGoodsSkuId());
        supermarketGoods.setPrice(corrects.getPrice());
        supermarketGoods.setCorrectId(corrects.getCorrectId());
        supermarketGoodsMapper.insert(supermarketGoods);
        //调整商品的最高价和最低价
        List<Integer> communityIdList = communitySupermarketService.getCommunityIdBysupermarketId(corrects.getSupermarketId());
        for (Integer communityId : communityIdList) {
            communitySupermarketService.toUpdateCommunityPrice(communityId, corrects.getGoodsSkuId().intValue());
        }
    }



    public void initVersion(Correct correct) {
            try {
                SupermarketGoodsVersion version = new SupermarketGoodsVersion();
                version.setSupermarketId(correct.getSupermarketId());
                version.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
                supermarketGoodsVersionService.insert(version);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Transactional
        @Override
        public void updatePriceByApp(Correct correct, MultipartFile file){
            if (
                    (correct.getPrice() == null || correct.getPrice() <= 0) ||
                            (correct.getGoodsSkuId() == null || correct.getGoodsSkuId() <= 0) ||
                            (correct.getSupermarketId() == null || correct.getSupermarketId() <= 0)||
                            (correct.getPriceType() == null )||
            (correct.getStartTime() == null )||
            (correct.getEndTime() == null )
            ) {
                throw new BusinessException(ErrorType.INCOMPLETE_INFO);
            }
            if(correct.getPrice()>=210000000){
                System.out.println("分享价格参数溢出,:");
                throw new BusinessException(ErrorType.SHAREPRICEOVERFLOW);
            }

            if ("SHARE".equals(correct.getCorrectType())) {
                if(correct.getCorrectId() != null ){
                    correct.setCorrectId(null);
                }
                // 线程安全并发处理
                initVersion(correct);
                correctService.share(correct, file, OperatorType.USER.getOperatorTypeName());
            }
            if ("CORRECT".equals(correct.getCorrectType())) {
                if(correct.getCorrectId() != null ){
                    correct.setParentId(correct.getCorrectId());
                    correct.setCorrectId(null);
                }
                correctService.correct(correct, file, OperatorType.USER.getOperatorTypeName());
            }

            if ("VIEW".equals(correct.getCorrectType())) {
                //
                //调用撤回方法
                Correct currentCorrect = correctService.recall(correct, OperatorType.USER.toString());

                //撤回后,重新通过超市id和商品id查询该商品的状态(可纠错,可分享,可查看)
                Supermarket supermarket = supermarketGoodsService.getCorrectTypeBySupermarketIdAndgoodsSkuId(correct.getGoodsSkuId(),correct.getSupermarketId());
                if("CORRECT".equals(supermarket.getCorrectType())){
                    if(correct.getCorrectId() != null ){
                        correct.setCorrectId(null);
                    }
                    correct.setParentId(currentCorrect.getParentId());
                    correctService.correct(correct, file, OperatorType.USER.getOperatorTypeName());
                }else if("SHARE".equals(supermarket.getCorrectType())){
                    if(correct.getCorrectId() != null ){
                        correct.setCorrectId(null);
                    }

                    // 线程安全并发处理
                    initVersion(correct);
                    correctService.share(correct, file, OperatorType.USER.getOperatorTypeName());
                }else{
                    throw new BusinessException(ErrorType.SYSTEM_ERROR);
                }
            }

        }


    /**
     * 获取任务(定时器)
     * @param
     * @return
     */
    @Override
    @Transactional
    public List<Correct> priceAudit() {
        //获取待审核列表
        List<Correct> correctList = correctMapper.getAffectedPrice();
        Date now = new Date();
        for (Correct correct1 : correctList) {
            PriceAuditTask priceAuditTask = new PriceAuditTask();
            priceAuditTask.setCorrectId(correct1.getCorrectId());
            priceAuditTask.setPriceType(correct1.getStatus());
            priceAuditTask.setTaskDate(now);
            priceAuditTask.setCreateTime(now);
            priceAuditTaskMapper.insert(priceAuditTask);
        }
        return correctList;
    }

    @Override
    @Transactional
    public void updatePriceAudit(Correct correct) {
        Correct newCorrect = correctMapper.selectByPrimaryKey(correct.getCorrectId());

        Date now = new Date();
        if (CorrectStatus.AFFECTED.toString().equals(newCorrect.getStatus())) {
            Correct param = new Correct();
            param.setCorrectId(correct.getCorrectId());
            param.setStatusAfterAffect(correct.getStatus());
            param.setAuditLastTime(now);
            param.setAuditerAfterAffect(correct.getUserId());
            param.setAuditTimeAfterAffect(now);
            correctMapper.updateCorrect(param);

            if (CorrectStatus.AUDIT_FAIL.toString().equals(correct.getStatus()) && OperatorType.USER.toString().equals(correct.getAuditType())) {

                        correct.setType(CorrectType.CORRECT.toString());
                    }
        } else {
            throw new BusinessException(ErrorType.AUDIT_ERROR);
        }


    }

    /**
     * 获取任务总数和完成数量
     * @param
     * @return
     */
    @Override
    public List<PriceAuditTask> getTotalQuantity(Correct correct) {
        List<PriceAuditTask> totalQuantity = priceAuditTaskMapper.getTotalQuantity(correct);
        if (totalQuantity.size()==0){
            return new ArrayList<>();
        }
        List<PriceAuditTask> completeQuantity = priceAuditTaskMapper.getCompleteQuantity(correct);
        if (completeQuantity.size()==0) {
           for (PriceAuditTask priceAuditTask : totalQuantity) {
                priceAuditTask.setCompleteQuantity(priceAuditTask.getTotalQuantity());
                return totalQuantity;
            }
        }
        for (PriceAuditTask priceAuditTask : totalQuantity) {
            if (!completeQuantity.contains(priceAuditTask.getTaskDate())){
                priceAuditTask.setCompleteQuantity(priceAuditTask.getTotalQuantity());
            }
            for (PriceAuditTask auditTask : completeQuantity) {
                if (priceAuditTask.getTaskDate().getTime()-auditTask.getTaskDate().getTime()==0){
                    priceAuditTask.setCompleteQuantity(priceAuditTask.getTotalQuantity()-auditTask.getTotalQuantity());
                }
            }

        }
        return totalQuantity;
    }


    /**
     * 根据超市id和任务日期获取待审核商品列表
     * @param correct
     * @return
     */
    @Override
    public List<Correct> getTaskGoodsSkuList(Correct correct) {
        List<Correct> corrects = correctMapper.getTaskGoodsSkuList(correct);
        for (Correct correct1 : corrects) {
            correct1.setGoodsImage(ImageUtil.dealToShow(correct1.getGoodsImage()));
        }
        return corrects;
    }


    /**
     * 审核任务点击审核通过
     * @param correct
     */
    @Override
    @Transactional
    public void taskAuditSuccess(Correct correct) {
        //通过任务id获取原来的状态
        PriceAuditTask priceAuditTask = priceAuditTaskMapper.selectByPrimaryKey(correct.getTaskId());
        //通过纠错id获取状态
        Correct correct1 = correctMapper.selectByPrimaryKey(correct.getCorrectId());
        Correct correct2 = new Correct();
        Date now = new Date();
        if (CorrectStatus.WAIT_AUDIT.toString().equals(priceAuditTask.getPriceType())) { //如果任务状态是待审核
            if (CorrectStatus.WAIT_AUDIT.toString().equals(correct1.getStatus())) { //如果纠错状态没改变
                correct2.setCorrectId(correct.getCorrectId());
                correct2.setStatus(CorrectStatus.AUDIT_SUCCESS.toString());
                correct2.setAuditerId(correct.getUserId());
                correct2.setAuditTime(now);
                correct2.setAuditType("USER");
                correct2.setManualAuditStatus(CorrectStatus.AUDIT_SUCCESS.toString());
            }else{
                throw new BusinessException(ErrorType.AUDIT_ERROR); //已经被审核
            }
        }else if(CorrectStatus.AFFECTED.toString().equals(priceAuditTask.getPriceType()) && CorrectStatus.AFFECTED.toString().equals(correct1.getStatus())){
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.MONTH, -2);
            Date m3 = c.getTime();
            if (CorrectStatus.NOT_AUDIT.toString().equals(correct1.getManualAuditStatus()) || correct1.getAuditTime().compareTo(m3)<0){
                correct2.setCorrectId(correct.getCorrectId());
                correct2.setManualAuditStatus(CorrectStatus.AUDIT_SUCCESS.toString());
                correct2.setAuditTime(now);
                correct2.setAuditerAfterAffect(correct.getUserId());
            }else{
                throw new BusinessException(ErrorType.AUDIT_ERROR); //已经被审核
            }
        }
        correctMapper.updateCorrect(correct2); //更新纠错表
    }


    /**
     * 审核任务点击价格错误/修改价格
     * @param correct
     */
    @Override
    public Correct taskAuditPriceError(Correct correct) {
        correct.setPriceErrorType("UPDATEPRICE");
        //通过任务id获取原来的状态
        PriceAuditTask priceAuditTask = priceAuditTaskMapper.selectByPrimaryKey(correct.getTaskId());
        //通过纠错id获取状态
        Correct correct2 = correctMapper.selectByPrimaryKey(correct.getCorrectId());
        priceError(correct,correct2,priceAuditTask); //调用通用方法修改状态
        Correct correct3 = new Correct(); //返回状态
        Correct param = new Correct();
        param.setSupermarketId(correct.getSupermarketId());
        param.setGoodsSkuId(Long.valueOf(correct.getGoodsSkuId()));
        param.setStatus(CorrectStatus.AFFECTED.toString());
        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);
        // 当查询结果不为null时，表示该商品有价格
        if (currentCorrect != null) {
            Correct param1 = new Correct();
            param1.setSupermarketId(correct.getSupermarketId());
            param1.setGoodsSkuId(Long.valueOf(correct.getGoodsSkuId()));
            List<String> statusList = new ArrayList<>();
            statusList.add(CorrectStatus.WAIT_AUDIT.toString());
            statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
            param1.setStatusList(statusList);
            Correct currentCorrect1 = correctMapper.query(param1).stream().findFirst().orElse(null);
            if (currentCorrect1 != null) {
                correct3.setCorrectType("VIEW");
                correct3.setCorrectId(currentCorrect1.getCorrectId());
            } else {
                correct3.setCorrectType("CORRECT");
                //获取该商品的超市价格
                SupermarketGoods currentSupermarketGoods = supermarketGoodsMapper.getSupermarketGoodsBysupermarketIdAndgoodsSkuId(currentCorrect.getGoodsSkuId().intValue(),currentCorrect.getSupermarketId());
                if (currentSupermarketGoods!=null) {
                    correct3.setCorrectId(currentCorrect.getCorrectId());
                    correct3.setGoodsSkuId(currentCorrect.getGoodsSkuId());
                }
            }
            // 否则表示该商品无价格
        } else {
            // 通过超市id和商品id查询纠错表，如果结果为null则显示分享按钮
            List<Correct> correctList = correctMapper.selectCorrect(correct.getSupermarketId(),
                    Long.valueOf(correct.getGoodsSkuId()));
            if (correctList.size() <= 0) {
                correct3.setCorrectType("SHARE");
                // 否则遍历集合
            } else {
                Correct param2 = new Correct();
                param2.setSupermarketId(correct.getSupermarketId());
                param2.setGoodsSkuId(Long.valueOf(correct.getGoodsSkuId()));
                List<String> statusList = new ArrayList<>();
                statusList.add(CorrectStatus.WAIT_AUDIT.toString());
                statusList.add(CorrectStatus.AUDIT_SUCCESS.toString());
                param2.setStatusList(statusList);
                Correct currentCorrect2 = correctMapper.query(param2).stream().findFirst().orElse(null);
                if (currentCorrect2 != null) {
                    correct3.setCorrectType("VIEW");
                    correct3.setCorrectId(currentCorrect2.getCorrectId());
                } else {
                    correct3.setCorrectType("SHARE");
                }
            }
        }
        return  correct3;
    }


    /**
     * 点击价格错误/暂无价格
     * @param correct
     */
    @Override
    @Transactional
    public void noTimePrice(Correct correct) {
        correct.setPriceErrorType("NOTPRICE");
        //通过任务id获取原来的状态
        PriceAuditTask priceAuditTask = priceAuditTaskMapper.selectByPrimaryKey(correct.getTaskId());
        //通过纠错id获取状态
        Correct correct2 = correctMapper.selectByPrimaryKey(correct.getCorrectId());
        priceError(correct,correct2,priceAuditTask); //调用通用方法修改状态
        if(CorrectStatus.AFFECTED.toString().equals(priceAuditTask.getPriceType())){//如果原来是已生效的状态
        Correct correct1 = new Correct();
        correct1.setCorrectId(correct.getCorrectId());
        correct1.setStatus(CorrectStatus.RECALL.toString());
        //撤回本条记录
        correctMapper.updateCorrect(correct1);

        //根据超市id商品id删除超市商品表信息
        supermarketGoodsMapper.deleteSupermarketGoods(correct.getSupermarketId(),correct.getGoodsSkuId());

        //根据超市id获取绑定的社区id集合
        List<Integer> communityIdList = communitySupermarketService.getCommunityIdBysupermarketId(correct.getSupermarketId());

        //更新社区价格表信息
        for (Integer integer : communityIdList) {
            communitySupermarketService.toUpdateCommunityPrice(integer, correct.getGoodsSkuId().intValue());
        }
        }
    }


    /**
     * 价格审核通用方法
     * @param correct
     */
    public void priceError(Correct correct,Correct correct1,PriceAuditTask priceAuditTask) {

        Correct correct2 = new Correct();
        Date now = new Date();
        if (CorrectStatus.WAIT_AUDIT.toString().equals(priceAuditTask.getPriceType())) { //如果任务状态是待审核
            if (CorrectStatus.WAIT_AUDIT.toString().equals(correct1.getStatus())) { //如果纠错状态没改变
                correct2.setCorrectId(correct.getCorrectId());
                correct2.setStatus(CorrectStatus.AUDIT_FAIL.toString());
                correct2.setAuditerId(correct.getUserId());
                correct2.setAuditTime(now);
                correct2.setAuditType("USER");
                if ("UPDATEPRICE".equals(correct.getPriceErrorType())){
                    correct2.setManualAuditStatus(CorrectStatus.AUDIT_FAIL_CORRECT.toString());
                }else {
                    correct2.setManualAuditStatus(CorrectStatus.AUDIT_FAIL_EXPIRED.toString());
                }
            }else{
                throw new BusinessException(ErrorType.AUDIT_ERROR); //已经被审核
            }
        }else if(CorrectStatus.AFFECTED.toString().equals(priceAuditTask.getPriceType()) && CorrectStatus.AFFECTED.toString().equals(correct1.getStatus())){
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.MONTH, -2);
            Date m3 = c.getTime();
            if (CorrectStatus.NOT_AUDIT.toString().equals(correct1.getManualAuditStatus()) || correct1.getAuditTime().compareTo(m3)<0){
                correct2.setCorrectId(correct.getCorrectId());
                if ("UPDATEPRICE".equals(correct.getPriceErrorType())){
                    correct2.setManualAuditStatus(CorrectStatus.AUDIT_FAIL_CORRECT.toString());
                }else {
                    correct2.setManualAuditStatus(CorrectStatus.AUDIT_FAIL_EXPIRED.toString());
                }
                correct2.setAuditTime(now);
                correct2.setAuditerAfterAffect(correct.getUserId());
            }else{
                throw new BusinessException(ErrorType.AUDIT_ERROR); //已经被审核
            }
        }
        correctMapper.updateCorrect(correct2); //更新纠错表
        }


    /**
     * 已审核
     * @param correct
     * @return
     */
    @Override
    public List<Correct> getAuditHistory(Correct correct) {
        List<Correct> corrects = correctMapper.getAuditHistory(correct);
        for (Correct correct1 : corrects) {
            correct1.setGoodsImage(ImageUtil.dealToShow(correct1.getGoodsImage()));
        }
        return corrects;
    }


    //后台任务列表
    @Override
    public List<Correct> getTaskList(Correct correct) {

        return correctMapper.getTaskList(correct);
    }

}
