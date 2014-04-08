package com.kechuang.wifidog.horizon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.UserTradeDao;
import com.kechuang.wifidog.horizon.model.UserTrade;
import com.kechuang.wifidog.horizon.service.UserTradeService;

@Service("com.kechuang.wifidog.horizon.service.impl.UserTradeService")
public class UserTradeServiceImpl implements UserTradeService{
	
	private static final Logger LOG = Logger.getLogger(UserTradeServiceImpl.class);
	
	@Autowired
	private UserTradeDao userTradeDao;
	
	public UserTradeServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return userTradeDao.deleteByPrimaryKey(id);
	}

	public int insert(UserTrade userTrade) {
		// TODO Auto-generated method stub
		return userTradeDao.insert(userTrade);
	}

	public int insertSelective(UserTrade userTrade) {
		// TODO Auto-generated method stub
		return userTradeDao.insertSelective(userTrade);
	}

	public UserTrade selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		return userTradeDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(UserTrade userTrade) {
		// TODO Auto-generated method stub
		return userTradeDao.updateByPrimaryKeySelective(userTrade);
	}

	public int updateByPrimaryKey(UserTrade userTrade) {
		// TODO Auto-generated method stub
		return userTradeDao.updateByPrimaryKey(userTrade);
	}

	public List<UserTrade> listAllUserTrade(long userID, byte tradeType, Date startTime, Date endTime, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("tradeType", tradeType);
		parameter.put("status", -1);
		parameter.put("tradeWay", -1);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return userTradeDao.listAllUserTrade(parameter);
	}

	public long getTotalCount(long userID, byte tradeType, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("tradeType", tradeType);
		parameter.put("status", -1);
		parameter.put("tradeWay", -1);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		return userTradeDao.getTotalCount(parameter);
	}

	public List<UserTrade> listAllUserTrade(byte tradeType, byte status,
			Date startTime, Date endTime, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", -1);
		parameter.put("tradeType", tradeType);
		parameter.put("status", status);
		parameter.put("tradeWay", -1);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return userTradeDao.listAllUserTrade(parameter);
	}

	public long getTotalCount(byte tradeType, byte status, Date startTime,
			Date endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", -1);
		parameter.put("tradeType", tradeType);
		parameter.put("status", status);
		parameter.put("tradeWay", -1);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		return userTradeDao.getTotalCount(parameter);
	}

}
