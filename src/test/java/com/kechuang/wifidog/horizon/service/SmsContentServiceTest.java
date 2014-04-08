package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.RouteRecord;
import com.kechuang.wifidog.horizon.model.SmsContent;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class SmsContentServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsContentService")
	private SmsContentService smsContentService;
	
	@Test
	public void testListAllUser() {
		List<SmsContent> listSmsContent = smsContentService.listAllSmsContent(0, -1, -1);
		for (SmsContent smsContent : listSmsContent) {
			System.out.println(smsContent);
		}
		
	}
}
