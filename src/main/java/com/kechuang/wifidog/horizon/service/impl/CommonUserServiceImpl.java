package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.CommonUserDao;
import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.service.CommonUserService;

@Service("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
public class CommonUserServiceImpl implements CommonUserService{
	
	private static final Logger LOG = Logger.getLogger(CommonUserServiceImpl.class);
	
	@Autowired
	private CommonUserDao commonUserDao;
	
	public CommonUserServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return commonUserDao.deleteByPrimaryKey(id);
	}

	public int insert(CommonUser commonUser) {
		// TODO Auto-generated method stub
		return commonUserDao.insert(commonUser);
	}

	public int insertSelective(CommonUser commonUser) {
		// TODO Auto-generated method stub
		return commonUserDao.insertSelective(commonUser);
	}

	public int updateByPrimaryKeySelective(CommonUser commonUser) {
		// TODO Auto-generated method stub
		return commonUserDao.updateByPrimaryKeySelective(commonUser);
	}

	public int updateByPrimaryKey(CommonUser commonUser) {
		// TODO Auto-generated method stub
		return commonUserDao.updateByPrimaryKey(commonUser);
	}

	public List<CommonUser> listAllUser(long businessID, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("userID", businessID);
		return commonUserDao.listAllUser(parameter);
	}

	public long getTotalCount(long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", businessID);
		return commonUserDao.getTotalCount(parameter);
	}

	public CommonUser selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("userName", null);
		parameter.put("userID", -1);
		return commonUserDao.selectByMap(parameter);
	}

	public CommonUser selectByMap(String userName, long businessID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("userName", userName);
		parameter.put("userID", businessID);
		return commonUserDao.selectByMap(parameter);
	}

}
