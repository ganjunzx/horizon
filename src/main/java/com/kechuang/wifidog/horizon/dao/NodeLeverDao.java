package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.NodeLever;
import com.kechuang.wifidog.horizon.model.SmsContent;

public interface NodeLeverDao {
	int deleteByPrimaryKey(Long id);
	int insert(NodeLever nodeLever);
	int insertSelective(NodeLever nodeLever);
	NodeLever selectByMap(Map<String, Object> map);
	int updateByPrimaryKeySelective(NodeLever nodeLever);
	int updateByPrimaryKey(NodeLever nodeLever);
	List<NodeLever> listAllNodeLever(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
