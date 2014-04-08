package com.kechuang.wifidog.horizon.service;

import java.util.List;

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
public class MenuServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.MenuService")
	private MenuService menuService;
	
	@Test
	public void getMenu() {
	/*	List<User> listUser = menuService.getMenuByUser(user);
		for (User user : listUser) {
			System.out.println(user);
		}*/
		
	}
}
