package com.kechuang.wifidog.horizon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.NodeConnectionDao;
import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;

@Service("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
public class NodeConnectionServiceImpl implements NodeConnectionService{
	
	private static final Logger LOG = Logger.getLogger(NodeConnectionServiceImpl.class);
	
	@Autowired
	private NodeConnectionDao nodeConnectionDao;
	
	public NodeConnectionServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return nodeConnectionDao.deleteByPrimaryKey(id);
	}

	public int insert(NodeConnection nodeConnection) {
		// TODO Auto-generated method stub
		return nodeConnectionDao.insert(nodeConnection);
	}

	public int insertSelective(NodeConnection nodeConnection) {
		// TODO Auto-generated method stub
		return nodeConnectionDao.insertSelective(nodeConnection);
	}

	public int updateByPrimaryKeySelective(NodeConnection nodeConnection) {
		// TODO Auto-generated method stub
		return nodeConnectionDao.updateByPrimaryKeySelective(nodeConnection);
	}

	public int updateByPrimaryKey(NodeConnection nodeConnection) {
		// TODO Auto-generated method stub
		return nodeConnectionDao.updateByPrimaryKey(nodeConnection);
	}

	public long getTotalCount(long nodeID, byte status) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", -1);
		parameter.put("connectStart", null);
		parameter.put("connectEnd", null);
		parameter.put("businessID", -1);
		return nodeConnectionDao.getTotalCount(parameter);
	}

	public List<NodeConnection> listAllConnections(long nodeID, byte status,
			long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", -1);
		parameter.put("connectStart", null);
		parameter.put("connectEnd", null);
		parameter.put("businessID", -1);
		parameter.put("cellPhoneNum", null);
		return nodeConnectionDao.listAllConnections(parameter);
	}

	public List<NodeConnection> listAllConnections(long nodeID, byte status,
			long userID, Date connectStart, Date connectEnd, long businessID, long limit,
			long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", userID);
		parameter.put("connectStart", connectStart);
		parameter.put("connectEnd", connectEnd);
		parameter.put("businessID", businessID);
		parameter.put("cellPhoneNum", null);
		return nodeConnectionDao.listAllConnections(parameter);
	}

	public long getTotalCount(long nodeID, byte status, long userID,
			Date connectStart, Date connectEnd, long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", userID);
		parameter.put("connectStart", connectStart);
		parameter.put("connectEnd", connectEnd);
		parameter.put("businessID", businessID);
		return nodeConnectionDao.getTotalCount(parameter);
	}

	public NodeConnection selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("nodeID", -1);
		parameter.put("tokensID", -1);
		parameter.put("businessID", -1);
		parameter.put("status", -1);
		return nodeConnectionDao.selectByMap(parameter);
	}

	public NodeConnection selectByMap(long tokensID, long nodeID,
			long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("nodeID", nodeID);
		parameter.put("tokensID", tokensID);
		parameter.put("businessID", businessID);
		parameter.put("status", -1);
		return nodeConnectionDao.selectByMap(parameter);
	}
	
	public NodeConnection selectByMap(long tokensID, byte status) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("nodeID", -1);
		parameter.put("tokensID", tokensID);
		parameter.put("businessID", -1);
		parameter.put("status", status);
		return nodeConnectionDao.selectByMap(parameter);
	}

	public List<NodeConnection> listAllConnections(long nodeID, byte status,
			String cellPhoneNum, long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("limit", -1);
		parameter.put("offset", -1);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", -1);
		parameter.put("connectStart", null);
		parameter.put("connectEnd", null);
		parameter.put("businessID", businessID);
		parameter.put("cellPhoneNum", cellPhoneNum);
		return nodeConnectionDao.listAllConnections(parameter);
	}

	public List<Long> getTotalSum(long nodeID, byte status, long userID,
			Date connectStart, Date connectEnd, long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("status", status);
		parameter.put("nodeID", nodeID);
		parameter.put("userID", userID);
		parameter.put("connectStart", connectStart);
		parameter.put("connectEnd", connectEnd);
		parameter.put("businessID", businessID);
		Map<String, Long> listSumInOut = nodeConnectionDao.getTotalSum(parameter);
		List<Long> result = new ArrayList<Long>();
		if (listSumInOut != null && listSumInOut.size() != 0) {
			result.add(listSumInOut.get("totalIncoming"));
			result.add(listSumInOut.get("totalOutgoing"));
		}
		return result;
	}

}
