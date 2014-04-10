package com.kechuang.wifidog.horizon.service;

import java.util.List;

import com.kechuang.wifidog.horizon.model.NodeLever;

public interface NodeLeverService {
	int deleteByPrimaryKey(long id);
	int insert(NodeLever nodeLever);
	int insertSelective(NodeLever nodeLever);
	NodeLever selectByMap(long id);
	NodeLever selectByNodeID(long nodeID);
	int updateByPrimaryKeySelective(NodeLever nodeLever);
	int updateByPrimaryKey(NodeLever nodeLever);
	List<NodeLever> listAllNodeLever(long vipID,long limit, long offset);
	long getTotalCount(long vipID);
}
