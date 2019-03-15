package com.linayi.service.correct.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.linayi.entity.account.AdminAccount;
import com.linayi.util.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.linayi.dao.correct.CorrectLogMapper;
import com.linayi.dao.correct.CorrectMapper;
import com.linayi.dao.goods.SupermarketGoodsMapper;
import com.linayi.dao.supermarket.SupermarketMapper;
import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.CorrectLog;
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
import com.linayi.service.supermarket.SupermarketService;
import com.linayi.service.user.UserService;
import com.linayi.util.ImageUtil;
import com.linayi.util.PageResult;

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
        correct.setStatus(CorrectStatus.WAIT_AUDIT.toString());
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
        if (OperatorType.USER.toString().equals(userType)) {
            correctLog.setOperatorId(correct.getUserId());
        }
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
    public Correct correct(Correct correct, MultipartFile file) {
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(correct.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(correct.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        User currentUser = userService.selectUserByuserId(correct.getUserId());

        if ("FALSE".equals(currentUser.getIsSharer())) {
            throw new BusinessException(ErrorType.NOT_SHARER);
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

        String path = null;
        try {
            path = ImageUtil.handleUpload(file);
            correct.setImage(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //插入correct表
        correct.setStatus(CorrectStatus.WAIT_AUDIT.toString());
        correct.setCreateTime(now);
        correct.setUpdateTime(now);
        correct.setType(CorrectType.CORRECT.toString());
        correct.setUserType(OperatorType.USER.getOperatorTypeName());
        correctMapper.insert(correct);

        //插入correct_log表
        CorrectLog correctLog = new CorrectLog();
        correctLog.setCorrectId(correct.getCorrectId());
        correctLog.setOperateStatus(CorrectStatus.WAIT_AUDIT.toString());
        correctLog.setOperatorId(correct.getUserId());
        correctLog.setOperatorType(OperatorType.USER.toString());
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
    @Override
    @Transactional
    public void recall(Correct correct, String userType) {

        Correct correctParam = correctMapper.selectByPrimaryKey(correct.getCorrectId());
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(correctParam.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(correctParam.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

        User currentUser = userService.selectUserByuserId(correct.getUserId());

        if (OperatorType.USER.toString().equals(userType)) {
            if ("FALSE".equals(currentUser.getIsSharer())) {
                throw new BusinessException(ErrorType.NOT_SHARER);
            }
        }

        Date now = new Date();

        Correct param = new Correct();
        param.setCorrectId(correct.getCorrectId());
        Correct currentCorrect = correctMapper.query(param).stream().findFirst().orElse(null);

        //判断用户不能撤回别人正在纠错的记录
        if (OperatorType.USER.toString().equals(userType) && !(correct.getUserId().equals(currentCorrect.getUserId()))) {
            throw new BusinessException(ErrorType.NOT_YOUR_CORRECT);
        }

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
    }

    /*纠错分享审核*/
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
            correctMapper.updateCorrect(param);

            //插入correct_log表
            CorrectLog correctLog = new CorrectLog();
            correctLog.setCorrectId(correct.getCorrectId());
            correctLog.setOperateStatus(correct.getStatus());
            correctLog.setOperatorId(correct.getUserId());
            correctLog.setOperatorType(OperatorType.ADMIN.toString());
            correctLog.setCreateTime(now);
            correctLogMapper.insert(correctLog);
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
    public PageResult<Correct> page(Correct correct) {
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
            ;
            if (supermarket != null) {
                String name = supermarket.getName();
                item.getSupermarket().setName(name);
            }
        });
        corrects.sort((a, b) -> {
            return b.getCreateTime().compareTo(a.getCreateTime());
        });
        int fromIndex = (currentPage - 1) * pageSize;
        List<Correct> correctResp = corrects.subList(fromIndex, currentPage * pageSize);
        PageResult<Correct> page = new PageResult<>(correctResp, corrects.size());
        return page;
    }

    @Override
    public Correct selectByCorrectId(Long correctId) {
        Correct correct = correctMapper.selectByPrimaryKey(correctId);
        if (correct != null) {
            correct.setSupermarket(supermarketService.selectSupermarketBysupermarketId(correct.getSupermarketId()));
            correct.setUser(userService.selectUserByuserId(correct.getUserId()));
            correct.setGoodsSku(goodsSkuService.getGoodsSkuById(correct.getGoodsSkuId()));
        }

        //设置上次纠错人的证据照片
        if (correct != null && correct.getParentId() != null) {
            /*if (correct.getParentId() == -1) {
                correct.setParentImage(Configuration.getConfig().getValue("imageServer") + "/" +correct.getImage());
            } else {*/
            Correct correctPare = correctMapper.selectByPrimaryKey(correct.getParentId());
            if (correctPare != null) {
                correct.setImage(Configuration.getConfig().getValue("imageServer") + "/" + correct.getImage());
                correct.setParentImage(Configuration.getConfig().getValue("imageServer") + "/" + correctPare.getImage());
            }
            /*}*/

        } else if (correct.getParentId() == null) {
            correct.setParentImage(Configuration.getConfig().getValue("imageServer") + "/" + correct.getImage());
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

    /*生效定时器*/
    @Override
    @Transactional
    public void priceAffect(Correct corrects) {
        // 线程安全并发处理
        SupermarketGoodsVersion param1 = new SupermarketGoodsVersion();
        param1.setSupermarketId(corrects.getSupermarketId());
        param1.setGoodsSkuId(Integer.parseInt(corrects.getGoodsSkuId() + ""));
        SupermarketGoodsVersion version = supermarketGoodsVersionService.getVersion(param1);

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
                        correct.setActualStartTime(now);
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
            //判断是否已过期
            if (now.after(corrects.getEndTime())) {
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
                //调整商品的最高价和最低价
                List<Integer> communityIdList = communitySupermarketService.getCommunityIdBysupermarketId(corrects.getSupermarketId());
                for (Integer communityId : communityIdList) {
                    communitySupermarketService.toUpdateCommunityPrice(communityId, corrects.getGoodsSkuId().intValue());
                }
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

}
