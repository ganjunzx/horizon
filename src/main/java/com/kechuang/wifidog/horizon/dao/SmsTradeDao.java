package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.SmsTrade;

public interface SmsTradeDao {
	int deleteByPrimaryKey(Long id);

	int insert(SmsTrade smsTrade);

	int insertSelective(SmsTrade smsTrade);
	
	SmsTrade selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(SmsTrade smsTrade);

	int updateByPrimaryKey(SmsTrade smsTrade);

	List<SmsTrade> listAllSmsTrade(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
