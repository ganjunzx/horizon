package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.RouteStatus;

public interface RouteStatusDao {
	int deleteByPrimaryKey(Long id);

	int insert(RouteStatus routeStatus);

	int insertSelective(RouteStatus routeStatus);
	
	RouteStatus selectByMap(Map<String, Object> map);

	int updateByPrimaryKeySelective(RouteStatus routeStatus);

	int updateByPrimaryKey(RouteStatus routeStatus);

	List<RouteStatus> listAllRouteStatus(Map<String, Object> map);
	long getTotalCount(Map<String, Object> map);
}
