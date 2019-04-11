package com.linayi.service.user;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.linayi.entity.user.AuthenticationApply;

public interface AuthenticationApplyService {
	
	/**
	 * 
	 * @param	apply	apply对象a
	 * @param	file	图片数组(正面在前，反面在后)
	 * @return	ResponseData对象json字符串
	 */
	public Object applySharer(AuthenticationApply apply, MultipartFile[] file);

	/**
	 * 
	 * @param	apply	apply对象
	 * @return	apply对象对象集合
	 */
	public List<AuthenticationApply> selectAuthenticationApplyList(AuthenticationApply apply);

	/**
	 *	根据applyId查询AuthenticationApply对象
	 * @param applyId	applyId主键
	 * @return	AuthenticationApply对象
	 */
	public AuthenticationApply getAuthenticationApplyByapplyId(Integer applyId);

	/**
	 *	根据status状态修改authentication_apply表和user表
	 * @param	apply	apply对象
	 */
	public void updateAuthenticationApplyAndUserInfo(AuthenticationApply apply);

	/**
	 * 
	 * @param	apply	apply对象
	 * @param	file	图片数组(正面在前，反面在后)
	 * @return	ResponseData对象json字符串
	 */
	public Object applyProcurer(AuthenticationApply apply, MultipartFile[] file);

	/**
	 * 
	 * @param	apply	apply对象
	 * @param	file	图片数组(正面在前，反面在后)
	 * @return	ResponseData对象json字符串
	 */
	public Object applyDeliverer(AuthenticationApply apply, MultipartFile[] file);

}
