package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.SmsTrade;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class SmsTradeServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsTradeService")
	private SmsTradeService smsTradeService;
	
	@Test
	public void testListAllUser() {
		List<SmsTrade> listSmsTrade = smsTradeService.listAllSmsTrade(0, -1, -1);
		for (SmsTrade smsTrade : listSmsTrade) {
			System.out.println(smsTrade);
		}
	}
}
