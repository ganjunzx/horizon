package com.kechuang.wifidog.horizon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.SmsTradeDao;
import com.kechuang.wifidog.horizon.model.SmsTrade;
import com.kechuang.wifidog.horizon.service.SmsTradeService;

@Service("com.kechuang.wifidog.horizon.service.impl.SmsTradeService")
public class SmsTradeServiceImpl implements SmsTradeService{
	
	private static final Logger LOG = Logger.getLogger(SmsTradeServiceImpl.class);
	
	@Autowired
	private SmsTradeDao smsTradeDao;
	
	public SmsTradeServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return smsTradeDao.deleteByPrimaryKey(id);
	}

	public int insert(SmsTrade smsTrade) {
		// TODO Auto-generated method stub
		return smsTradeDao.insert(smsTrade);
	}

	public int insertSelective(SmsTrade smsTrade) {
		// TODO Auto-generated method stub
		return smsTradeDao.insertSelective(smsTrade);
	}

	public SmsTrade selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		return smsTradeDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(SmsTrade smsTrade) {
		// TODO Auto-generated method stub
		return smsTradeDao.updateByPrimaryKeySelective(smsTrade);
	}

	public int updateByPrimaryKey(SmsTrade smsTrade) {
		// TODO Auto-generated method stub
		return smsTradeDao.updateByPrimaryKey(smsTrade);
	}

	public List<SmsTrade> listAllSmsTrade(long userID, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("nodeID", -1);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return smsTradeDao.listAllSmsTrade(parameter);
	}

	public long getTotalCount(long userID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("nodeID", -1);
		return smsTradeDao.getTotalCount(parameter);
	}

	public List<SmsTrade> listAllSmsTrade(long userID, long nodeID, Date startTime, Date endTime, long limit,
			long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("nodeID", nodeID);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		return smsTradeDao.listAllSmsTrade(parameter);
	}

	public long getTotalCount(long userID, long nodeID, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("nodeID", nodeID);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		return smsTradeDao.getTotalCount(parameter);
	}


}
