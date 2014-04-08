package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.RouteRecord;

public interface RouteRecordDao {
	int deleteByPrimaryKey(Long id);

	int insert(RouteRecord routeRecord);

	int insertSelective(RouteRecord routeRecord);
	
	RouteRecord selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(RouteRecord routeRecord);

	int updateByPrimaryKey(RouteRecord routeRecord);

	List<RouteRecord> listAllRouteRecord(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
