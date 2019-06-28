package com.linayi.test;

import com.linayi.service.correct.CorrectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.linayi.dao.user.AuthenticationApplyMapper;
import com.linayi.entity.user.AuthenticationApply;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring-context.xml")
public class AuthenticationTest {
	
	@Autowired
	private AuthenticationApplyMapper authenticationApplyMapper;
	@Autowired
	private CorrectService correctService;

	
	@Test
	public void AuthenticationMapper(){
		AuthenticationApply authenticationApply = new AuthenticationApply();
		authenticationApply.setRealName("温国超");
		authenticationApply.setMobile("13714431235");
		authenticationApply.setUserId(1);
		authenticationApply.setIdCardFront("1.png");
		authenticationApply.setIdCardBack("2.png");
		authenticationApplyMapper.insert(authenticationApply);
	}


	@Test
	public void AuditPrice(){
		correctService.priceAudit();
	}

	
}
