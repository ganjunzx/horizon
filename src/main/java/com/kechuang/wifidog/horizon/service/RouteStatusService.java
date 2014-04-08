package com.kechuang.wifidog.horizon.service;

import java.util.List;

import com.kechuang.wifidog.horizon.model.RouteStatus;

public interface RouteStatusService {
	int deleteByPrimaryKey(long id);
    int insert(RouteStatus routeStatus);
    int insertSelective(RouteStatus routeStatus);
    RouteStatus selectByMap(long id);
    int updateByPrimaryKeySelective(RouteStatus routeStatus);
    int updateByPrimaryKey(RouteStatus routeStatus);
	List<RouteStatus> listAllRouteStatus(long nodeID, long limit, long offset);
	long getTotalCount(long nodeId);
}
