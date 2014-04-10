package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.NodeLeverDao;
import com.kechuang.wifidog.horizon.model.NodeLever;
import com.kechuang.wifidog.horizon.service.NodeLeverService;

@Service("com.kechuang.wifidog.horizon.service.impl.NodeLeverService")
public class NodeLeverServiceImpl implements NodeLeverService{
	
	private static final Logger LOG = Logger.getLogger(NodeLeverServiceImpl.class);
	
	@Autowired
	private NodeLeverDao nodeLeverDao;
	
	public NodeLeverServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return nodeLeverDao.deleteByPrimaryKey(id);
	}

	public int insert(NodeLever nodeLever) {
		// TODO Auto-generated method stub
		return nodeLeverDao.insert(nodeLever);
	}

	public int insertSelective(NodeLever nodeLever) {
		// TODO Auto-generated method stub
		return nodeLeverDao.insertSelective(nodeLever);
	}

	public NodeLever selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("nodeID", -1);
		return nodeLeverDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(NodeLever nodeLever) {
		// TODO Auto-generated method stub
		return nodeLeverDao.updateByPrimaryKeySelective(nodeLever);
	}

	public int updateByPrimaryKey(NodeLever nodeLever) {
		// TODO Auto-generated method stub
		return nodeLeverDao.updateByPrimaryKey(nodeLever);
	}

	public List<NodeLever> listAllNodeLever(long vipID, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("vipID", vipID);
		parameter.put("offset", offset);
		return nodeLeverDao.listAllNodeLever(parameter);
	}

	public long getTotalCount(long vipID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("vipID", vipID);
		return nodeLeverDao.getTotalCount(parameter);
	}

	public NodeLever selectByNodeID(long nodeID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("nodeID", nodeID);
		return nodeLeverDao.selectByMap(parameter);
	}


}
