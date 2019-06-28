package com.linayi.dao.correct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.linayi.entity.correct.Correct;
import org.apache.ibatis.annotations.Param;

public interface CorrectMapper {
    int insert(Correct record);


    Correct selectByPrimaryKey(Long correctId);

    Integer batchInsert(List<Correct> list);

    Integer batchUpdate(List<Correct> list);


    List<Correct> query(Correct correct);


    List<Correct> queryPage(Correct correct);

    List<Correct> queryAdminPage(Correct correct);

    Integer updateCorrect(Correct correct);

    /**
     * 根据父级id查询correct
     *
     * @param parentId
     * @param
     * @return
     */
    List<Correct> selectCorrectByParentId(Long parentId);

    /**
     * 根据超市id和商品id查询纠错表信息
     *
     * @param supermarketId
     * @param goodsSkuId
     * @return
     */
    List<Correct> selectCorrect(@Param("supermarketId") Integer supermarketId, @Param("goodsSkuId") Long goodsSkuId);

    /*根据条件查看商品价格纠错表*/
    List<Correct> getCorrect(Correct correct);

    List<Correct> getCorrectExpire(Correct correct);

    /**
     * 根据主键userId查询corrcetlist集合
     *
     * @param    correctId    correctId主键
     */
    List<Correct> selectCorrectListByUserId(@Param("userId") Integer userId);

    /**
     * 根据商品名获取纠错表信息
     *
     * @param correct
     * @return
     */
    List<Correct> selectCorrectListByGoodsName(Correct correct);

    /**
     * 获取纠错表其它超市价格
     * @param goodsSkuId
     * @return
     */
    List<Correct> getOtherPrice(Integer goodsSkuId);

    /**
     * 通过超市id获取待审核纠错任务和商品信息
     * @param correct
     * @return
     */
    List<Correct> getWaitAuditCorrectBySupermerketId(Correct correct);

    /**
     * 获取审核历史列表
     * @param correct
     * @return
     */
    List<Correct> getCorrectByAuditerId(Correct correct);

    /**
     * 获取商品已生效最低价列表
     * @param correct
     * @return
     */
    List<Correct> getAffectedMinPrice(Correct correct);

    /**
     * 通过商品id获取纠错信息（非通用）
     */
    List<Correct> getcorrectTimeByGoodsSkuId(@Param("goodsSkuId")Long goodsSkuId,@Param("supermarketId")Integer supermarketId);
    int updateByPrimaryKeySelective(Correct record);


    List<Correct> selectByAll(Correct correct);

    /**
     * -任务记录(定时器)
     * @param correct
     * @return
     */
    List<Correct> getAffectedPrice();

    /**
     * 根据超市id和任务日期获取待审核商品列表
     * @return
     */
    List<Correct> getTaskGoodsSkuList(Correct correct);


    /**
     * 审核历史列表
     * @param correct
     * @return
     */
    List<Correct> getAuditHistory(Correct correct);

}