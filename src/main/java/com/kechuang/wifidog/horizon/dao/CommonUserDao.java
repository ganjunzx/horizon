package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.CommonUser;

public interface CommonUserDao {
	int deleteByPrimaryKey(Long id);

	int insert(CommonUser commonUser);

	int insertSelective(CommonUser commonUser);
	
	CommonUser selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(CommonUser commonUser);

	int updateByPrimaryKey(CommonUser commonUser);

	List<CommonUser> listAllUser(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
