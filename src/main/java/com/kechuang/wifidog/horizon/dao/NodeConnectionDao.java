package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.model.User;

public interface NodeConnectionDao {
	int deleteByPrimaryKey(Long id);

	int insert(NodeConnection nodeConnection);

	int insertSelective(NodeConnection nodeConnection);

	NodeConnection selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(NodeConnection nodeConnection);

	int updateByPrimaryKey(NodeConnection nodeConnection);
	long getTotalCount(Map<String, Object> map);
	Map<String, Long> getTotalSum(Map<String, Object> map);
	List<NodeConnection> listAllConnections(Map<String, Object> map);
}
