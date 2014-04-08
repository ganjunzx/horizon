package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.UserTrade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class UserTradeServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserTradeService")
	private UserTradeService userTradeService;
	
	@Test
	public void testListAllUser() {
		List<UserTrade> listUserTrade = userTradeService.listAllUserTrade(0, (byte)0, null, null, -1, -1);
		for (UserTrade userTrade : listUserTrade) {
			System.out.println(userTrade);
		}
	}
}
