package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.UserDao;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.service.UserService;

@Service("com.kechuang.wifidog.horizon.service.impl.UserService")
public class UserServiceImpl implements UserService{
	
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	public UserServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return userDao.deleteByPrimaryKey(id);
	}

	public int insert(User user) {
		// TODO Auto-generated method stub
		return userDao.insert(user);
	}

	public int insertSelective(User user) {
		// TODO Auto-generated method stub
		return userDao.insertSelective(user);
	}

	public User selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("userName", null);
		parameter.put("password", null);
		return userDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(User user) {
		// TODO Auto-generated method stub
		return userDao.updateByPrimaryKeySelective(user);
	}

	public int updateByPrimaryKey(User user) {
		// TODO Auto-generated method stub
		return userDao.updateByPrimaryKey(user);
	}

	public List<User> listAllUserByUserType(byte userType, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userType", userType);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return userDao.listAllUserByUserType(parameter);
	}

	public long getTotalCount(byte userType) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userType", userType);
		return userDao.getTotalCount(parameter);
	}

	public User selectByMap(String userName, String password) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("userName", userName);
		parameter.put("password", password);
		return userDao.selectByMap(parameter);
	}
	
	public User selectByMap(String userName) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("userName", userName);
		parameter.put("password", null);
		return userDao.selectByMap(parameter);
	}

	public List<User> listAllUserByUserType(byte userType) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userType", userType);
		parameter.put("limit", -1);
		parameter.put("offset", -1);
		return userDao.listAllUserByUserType(parameter);
	}

	public List<User> listAllUser(long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return userDao.listAllUser(parameter);
	}

	public double getTotalAcount(byte userType) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userType", userType);
		return userDao.getTotalAcount(parameter);
	}
}
