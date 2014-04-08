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
import com.kechuang.wifidog.horizon.model.SmsSecurityCode;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class SmsSecurityCodeServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsSecurityCodeService")
	private SmsSecurityCodeService smsSecurityCodeService;
	
	@Test
	public void testListAllUser() {
		List<SmsSecurityCode> listSmsSecurityCode = smsSecurityCodeService.listAllSmsSecurityCode(0, 0, "asdf", "dsf", (byte)-1, null, null, -1, -1);
		for (SmsSecurityCode smsSecurityCode : listSmsSecurityCode) {
			System.out.println(smsSecurityCode);
		}
		
	}
}
