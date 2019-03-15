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

}