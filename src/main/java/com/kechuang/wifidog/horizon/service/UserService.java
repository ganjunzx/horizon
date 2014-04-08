package com.kechuang.wifidog.horizon.service;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.User;

public interface UserService {
	int deleteByPrimaryKey(long id);
    int insert(User user);
    int insertSelective(User user);
    User selectByMap(long id);
    int updateByPrimaryKeySelective(User user);
    int updateByPrimaryKey(User user);
	List<User> listAllUser(long limit, long offset);
	List<User> listAllUserByUserType(byte userType, long limit, long offset);
	List<User> listAllUserByUserType(byte userType);
	User selectByMap(String userName, String password);
	User selectByMap(String userName);
	long getTotalCount(byte userType);
	double getTotalAcount(byte userType);
}
