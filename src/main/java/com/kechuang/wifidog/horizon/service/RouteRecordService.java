package com.kechuang.wifidog.horizon.service;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.RouteRecord;

public interface RouteRecordService {
	int deleteByPrimaryKey(long id);
    int insert(RouteRecord routeRecord);
    int insertSelective(RouteRecord routeRecord);
    RouteRecord selectByMap(long id);
    int updateByPrimaryKeySelective(RouteRecord routeRecord);
    int updateByPrimaryKey(RouteRecord routeRecord);
	List<RouteRecord> listAllRouteRecord(long nodeId, long limit, long offset);
	long getTotalCount(long nodeId);
}
