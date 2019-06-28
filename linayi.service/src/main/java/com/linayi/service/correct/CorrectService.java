package com.linayi.service.correct;

import com.linayi.entity.correct.Correct;
import com.linayi.entity.correct.PriceAuditTask;
import com.linayi.entity.goods.SupermarketGoods;
import com.linayi.util.PageResult;

import java.text.ParseException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CorrectService {
    List<Correct> page(Correct correct);

    Correct selectByCorrectId(Long correctId);


    /*添加分享价格申请*/
    Correct share(Correct correct, MultipartFile file, String userType);

    /*添加纠错价格申请*/
    Correct correct(Correct correct, MultipartFile file, String userType);

    /*查看页面*/
    Correct showView(Long correctId);

    /*撤回*/
    Correct recall(Correct correct, String userType);

    /*纠错分享审核*/
    void audit(Correct correct);

    Integer upsertSelective(Correct correct);

    Integer updateCorrect(Correct correct);

    /*根据条件查看商品价格纠错表*/
    List<Correct> getCorrect(Correct correct);

    List<Correct> getCorrectExpire(Correct correct);

    /*生效定时器调用方法*/
    void priceAffect(Correct correct);

    /*过期定时器调用方法*/
    void priceExpire(Correct corrects);

    /**
     * 根据主键userId查询corrcetlist集合
     *
     * @param    correctId    correctId主键
     */
    List<Correct> selectCorrectListByUserId(Integer userId);

    /*历史列表的查看按钮*/
    Correct historyView(Correct correct);

    /**
     * 根据商品名获取纠错表信息
     *
     * @param correct
     * @return
     */
    List<Correct> selectCorrectListByGoodsName(Correct correct);

    List<Correct> getList(Correct correct);


    /**
     * 获取纠错表其它超市价格
     *
     * @param correct
     * @return
     */
    List<Correct> getOtherPrice(Integer goodsSkuId);

    /**
     * 采价员查看待审核纠错记录（指定一家超市）
     *
     * @param correct
     * @return
     */
    List<Correct> getWaitAuditCorrect(Correct correct);

    /**
     * 获取审核历史列表
     *
     * @param correct
     * @return
     */
    List<Correct> getCorrectByAuditerId(Correct correct);

    /**
     * 获取商品已生效最低价列表
     *
     * @param correct
     * @return
     */
    List<Correct> getAffectedMinPrice(Correct correct) throws ParseException;

    /**
     * 前台分享纠错合并
     * @param correct
     * @param file
     * @return
     */
    void updatePriceByApp(Correct correct, MultipartFile file);

    void priceImmediatelyAffect(Correct correct, MultipartFile file);

    void adjustPriceMaxAndMin(Correct corrects);


    /**
     * priceAudit
     * @param correct
     * @return
     */
    List<Correct> priceAudit();


    /**
     * App审核价格生效前2月
     * @param correct
     */
    void updatePriceAudit(Correct correct);

    /**
     * 任务数量和完成数量
     * @param
     * @return
     */
    List<PriceAuditTask> getTotalQuantity(Correct correct);


    /**
     * 根据超市id和任务日期获取待审核商品列表
     * @return
     */
    List<Correct> getTaskGoodsSkuList(Correct correct);

    /**
     * 审核任务点击审核通过
     */
    void taskAuditSuccess(Correct correct);


    /**
     * 审核任务点击价格错误
     */
    Correct taskAuditPriceError(Correct correct);


    /**
     * 点击暂无价格
     */
    void noTimePrice(Correct correct);


    /**
     * 已审核
     * @param correct
     * @return
     */
    List<Correct> getAuditHistory(Correct correct);

}
