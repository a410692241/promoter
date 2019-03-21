package com.linayi.service.correct;

import com.linayi.entity.correct.Correct;
import com.linayi.util.PageResult;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CorrectService {
    PageResult<Correct> page(Correct correct);
    Correct selectByCorrectId(Long correctId);


    /*添加分享价格申请*/
    Correct share(Correct correct,MultipartFile file,String userType);

    /*添加纠错价格申请*/
    Correct correct(Correct correct,MultipartFile file,String userType);

    /*查看页面*/
    Correct showView(Long correctId);

    /*撤回*/
    Correct recall(Correct correct,String userType);

    /*纠错分享审核*/
    void audit(Correct correct);

    Integer upsertSelective(Correct correct);

    Integer updateCorrect(Correct correct);

    /*根据条件查看商品价格纠错表*/
    List<Correct> getCorrect(Correct correct);

    /*生效定时器调用方法*/
    void priceAffect(Correct correct);

    /*过期定时器调用方法*/
    void priceExpire(Correct corrects);
    
    /**
     *	根据主键userId查询corrcetlist集合
     * @param	correctId	correctId主键
     */
	List<Correct> selectCorrectListByUserId(Integer userId);
	
	/*历史列表的查看按钮*/
	Correct historyView(Correct correct);
	
	/**
	 * 根据商品名获取纠错表信息
	 * @param correct
	 * @return
	 */
	List<Correct> selectCorrectListByGoodsName(Correct correct);
    List<Correct> getList(Correct correct);

//    void updatePriceForAdmin(Correct correct);
}
