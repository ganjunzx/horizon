package com.kechuang.wifidog.horizon.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.NodeConnection;

public interface NodeConnectionService {
	int deleteByPrimaryKey(long id);

	int insert(NodeConnection nodeConnection);

	int insertSelective(NodeConnection nodeConnection);

	NodeConnection selectByMap(long id);
	NodeConnection selectByMap(long tokensID, long nodeID, long businessID);
	NodeConnection selectByMap(long tokensID, byte status);

	int updateByPrimaryKeySelective(NodeConnection nodeConnection);

	int updateByPrimaryKey(NodeConnection nodeConnection);

	List<NodeConnection> listAllConnections(long nodeID, byte status, long limit, long offset);
	List<NodeConnection> listAllConnections(long nodeID, byte status, long userID, Date connectStart, Date connectEnd, long businessID, long limit, long offset);
	long getTotalCount(long nodeID, byte status);
	long getTotalCount(long nodeID, byte status, long userID, Date connectStart, Date connectEnd, long businessID);
	List<Long> getTotalSum(long nodeID, byte status, long userID, Date connectStart, Date connectEnd, long businessID);
	List<NodeConnection> listAllConnections(long nodeID, byte status, String cellPhoneNum, long businessID);
}
