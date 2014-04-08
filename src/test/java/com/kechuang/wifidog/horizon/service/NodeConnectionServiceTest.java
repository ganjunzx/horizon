package com.kechuang.wifidog.horizon.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.kechuang.wifidog.horizon.model.NodeConnection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml"})
@TransactionConfiguration(transactionManager = "wifidogTransactionManager", defaultRollback = true)
public class NodeConnectionServiceTest {

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;
	
	@Test
	public void testListAllUser() {
		List<NodeConnection> listSuperUser = nodeConnectionService.listAllConnections(0,(byte)0, 10, 1);
		for (NodeConnection nodeConnection : listSuperUser) {
			System.out.println(nodeConnection);
		}
		NodeConnection nodeConnection = new NodeConnection();
		
		nodeConnectionService.insert(nodeConnection);
		
	}
}
