package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.CommonUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class CommonUserServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;
	
	@Test
	public void testListAllUser() {
		List<CommonUser> listUser = commonUserService.listAllUser(2, 10, 0);
		for (CommonUser commonUser : listUser) {
			System.out.println(commonUser);
		}
		
	}
}
