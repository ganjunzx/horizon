package com.kechuang.wifidog.horizon.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class UserServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;
	
	@Test
	public void testListAllUser() {
		List<User> listUser = userService.listAllUser(10,1);
		for (User user : listUser) {
			System.out.println(user);
		}
		
		System.out.println(userService.selectByMap("admin", "admin"));
		
		User user = new User();
		user.setId(3);
		user.setUpdatedTime(Calendar.getInstance(TimeZone.getDefault()).getTime());
		userService.updateByPrimaryKeySelective(user);
	}
}
