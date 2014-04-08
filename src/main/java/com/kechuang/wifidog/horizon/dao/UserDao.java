package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.User;

public interface UserDao {
	int deleteByPrimaryKey(Long id);
    int insert(User user);
    int insertSelective(User user);
    User selectByMap(Map<String, Object> map);
    int updateByPrimaryKeySelective(User user);
    int updateByPrimaryKey(User user);
	List<User> listAllUser(Map<String, Object> map);
	List<User> listAllUserByUserType(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
	double getTotalAcount(Map<String, Object> map);
}
