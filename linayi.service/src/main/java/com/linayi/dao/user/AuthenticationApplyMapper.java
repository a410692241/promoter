package com.linayi.dao.user;

import com.linayi.entity.user.AuthenticationApply;

import java.util.List;

public interface AuthenticationApplyMapper {
	
	/**
	 *	插入数据
	 * @param	record	record对象
	 * @return	影响的行数
	 */
    int insert(AuthenticationApply record);

    /**
     *	查询对象集合
     * @param	apply对象
     * @return	对象集合
     */
	List<AuthenticationApply> selectAuthenticationApplyList(AuthenticationApply apply);

	/**
	 *	根据applyId查询AuthenticationApply对象
	 * @param applyId	applyId主键
	 * @return	AuthenticationApply对象
	 */
	AuthenticationApply getAuthenticationApplyByapplyId(Integer applyId);

	/**
	 * 	根据applyid修改status
	 */
	void updateStatusByApplyId(AuthenticationApply apply);

	/**
	 *	根据userId查询AuthenticationApply对象
	 * @param	apply
	 */
	List<AuthenticationApply> getAuthenticationApplyByUserIdAndType(AuthenticationApply apply);

	/**
	 * 根据ID修改通过审核的家庭服务师信息
	 * @param apply
	 */
	void updateApplyOrederManInfoById(AuthenticationApply apply);

	AuthenticationApply selectByApplyId(AuthenticationApply apply);

	Integer updateStatusByApplyId2(AuthenticationApply apply);

	void update(AuthenticationApply authenticationApply);

	List<AuthenticationApply> getorderManApplyByMobile(String mobile);
}