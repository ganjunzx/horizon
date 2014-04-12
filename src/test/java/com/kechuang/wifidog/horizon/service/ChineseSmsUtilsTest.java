package com.kechuang.wifidog.horizon.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.utils.ChineseSmsUtils;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;
import com.kechuang.wifidog.horizon.utils.MobileLocationUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class ChineseSmsUtilsTest {
	@Test
	public void testGetRemainSystemSms() {
		ChineseSmsUtils chineseSysUtils = new ChineseSmsUtils();
		System.out.println(chineseSysUtils.remainSystemSms("ganjunzx", "13cda88591470424b536"));
		
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(HorizonConfig.DATA_FORMAT);
		System.out.println(currentTime.getTime());
		String date = sdf.format(currentTime);
		System.out.println(date);
		
		try {
			System.out.println(sdf.parse(date).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*System.out.println(chineseSysUtils.sendVerifyCodeSms("ganjunzx", "13cda88591470424b536", "18676719508", "验证码【1234】"));*/
		
		try {
			System.out.println(MobileLocationUtil.getMobileLocation2("18676719508"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
