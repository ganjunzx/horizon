package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.RouteStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class RouteStatusServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.RouteStatusService")
	private RouteStatusService routeStatusService;
	
	@Test
	public void testListAllUser() {
		List<RouteStatus> listRouteStatus = routeStatusService.listAllRouteStatus(0, -1, -1);
		for (RouteStatus routeStatus : listRouteStatus) {
			System.out.println(routeStatus);
		}
		
	}
}
