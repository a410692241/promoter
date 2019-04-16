package com.linayi.service.spokesman;

import com.linayi.dao.spokesman.SpokesmanMapper;
import com.linayi.entity.spokesman.Spokesman;
import com.linayi.entity.supermarket.Supermarket;
import org.springframework.stereotype.Service;

import java.util.List;


public interface SpokesmanService {
	/**
	 * 添加代言人信息
	 * @return
	 */
	Spokesman addSpokesman(Spokesman spokesman);

	/**
	 * 根据主键id获取代言人信息
	 * @return
	 */
	Spokesman selectSpokesmanBySpokesmanId(Integer spokesmanId);

	/**
	 * 根据用户id获取代言人信息
	 * @return
	 */
	Spokesman selectSpokesmanByUserId(Integer userId);

	/**
	 * 修改代言人状态
	 * @return
	 */
	void updateSpokesmanStatus(Spokesman spokesman);
}
