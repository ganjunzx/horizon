package com.kechuang.wifidog.horizon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.NodeDao;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.service.NodeService;

@Service("com.kechuang.wifidog.horizon.service.impl.NodeService")
public class NodeServiceImpl implements NodeService{
	
	private static final Logger LOG = Logger.getLogger(NodeServiceImpl.class);
	
	@Autowired
	private NodeDao nodeDao;
	
	public NodeServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return nodeDao.deleteByPrimaryKey(id);
	}

	public int insert(Node node) {
		// TODO Auto-generated method stub
		return nodeDao.insert(node);
	}

	public int insertSelective(Node node) {
		// TODO Auto-generated method stub
		return nodeDao.insertSelective(node);
	}

	public Node selectByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return nodeDao.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Node node) {
		// TODO Auto-generated method stub
		return nodeDao.updateByPrimaryKeySelective(node);
	}

	public int updateByPrimaryKey(Node node) {
		// TODO Auto-generated method stub
		return nodeDao.updateByPrimaryKey(node);
	}

/*	public List<Node> listAllNode() {
		// TODO Auto-generated method stub
		return nodeDao.listAllNode();
	}*/

	public List<Node> listAllNode(long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("startTime", null);
		parameter.put("endTime", null);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", -1);
		return nodeDao.listAllNode(parameter);
	}

	public long getTotalCount(long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startTime", null);
		parameter.put("endTime", null);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", businessID);
		return nodeDao.getTotalCount(parameter);
	}

	
	public long getTotalCount() {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startTime", null);
		parameter.put("endTime", null);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", -1);
		return nodeDao.getTotalCount(parameter);
	}
	public List<Node> listAllNode(Date startTime, Date endTime, long limit,
			long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", -1);
		return nodeDao.listAllNode(parameter);
	}

	public List<Node> listAllNode(long venderID, Date startTime, Date endTime,
			long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("venderID", venderID);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", -1);
		return nodeDao.listAllNode(parameter);
	}

	public List<Node> listAllNode(Date startTime, Date endTime,
			byte nodeStatus, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", nodeStatus);
		parameter.put("businessID", -1);
		return nodeDao.listAllNode(parameter);
	}

	public List<Node> listAllNode(long venderID, long businessID, Date startTime, Date endTime,
			byte nodeStatus, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("venderID", venderID);
		parameter.put("nodeStatus", nodeStatus);
		parameter.put("businessID", businessID);
		parameter.put("ndName", null);
		return nodeDao.listAllNode(parameter);
	}

	public long getTotalCount(long venderID, long businessID, Date startTime,
			Date endTime, byte nodeStatus) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("venderID", venderID);
		parameter.put("nodeStatus", nodeStatus);
		parameter.put("businessID", businessID);
		parameter.put("ndName", null);
		return nodeDao.getTotalCount(parameter);
	}

	public Node selectByMCode(String mcode) {
		// TODO Auto-generated method stub
		return nodeDao.selectByMCode(mcode);
	}

	public long getTotalCount(long businessID, String ndName) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startTime", null);
		parameter.put("endTime", null);
		parameter.put("venderID", -1);
		parameter.put("nodeStatus", -1);
		parameter.put("businessID", businessID);
		parameter.put("ndName", ndName);
		return nodeDao.getTotalCount(parameter);
	}

	public Node selectByNodeName(String nodeName) {
		// TODO Auto-generated method stub
		return nodeDao.selectByNodeName(nodeName);
	}

	public long getTotalRemainSms() {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("startTime", null);
		parameter.put("endTime", null);
		parameter.put("businessID", -1);
		return nodeDao.getTotalRemainSms(parameter);
	}

}
