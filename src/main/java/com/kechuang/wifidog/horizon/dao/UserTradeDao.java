package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.UserTrade;

public interface UserTradeDao {
	int deleteByPrimaryKey(Long id);

	int insert(UserTrade userTrade);

	int insertSelective(UserTrade userTrade);
	
	UserTrade selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(UserTrade userTrade);

	int updateByPrimaryKey(UserTrade userTrade);

	List<UserTrade> listAllUserTrade(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
