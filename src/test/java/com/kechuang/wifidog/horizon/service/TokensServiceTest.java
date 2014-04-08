package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.Tokens;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml" })
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class TokensServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.TokensService")
	private TokensService tokensService;

	@Test
	public void testListAllUser() {
		List<Tokens> listTokens = tokensService.listAllTokens(0, 10);
		for (Tokens tokens : listTokens) {
			System.out.println(tokens);
		}

	}
}
