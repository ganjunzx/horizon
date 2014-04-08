package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.CommonUserDao;
import com.kechuang.wifidog.horizon.dao.RouteRecordDao;
import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.RouteRecord;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.RouteRecordService;

@Service("com.kechuang.wifidog.horizon.service.impl.RouteRecordService")
public class RouteRecordServiceImpl implements RouteRecordService{
	
	private static final Logger LOG = Logger.getLogger(RouteRecordServiceImpl.class);
	
	@Autowired
	private RouteRecordDao routeRecordDao;
	
	public RouteRecordServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return routeRecordDao.deleteByPrimaryKey(id);
	}

	public int insert(RouteRecord routeRecord) {
		// TODO Auto-generated method stub
		return routeRecordDao.insert(routeRecord);
	}

	public int insertSelective(RouteRecord routeRecord) {
		// TODO Auto-generated method stub
		return routeRecordDao.insertSelective(routeRecord);
	}

	public RouteRecord selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		return routeRecordDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(RouteRecord routeRecord) {
		// TODO Auto-generated method stub
		return routeRecordDao.updateByPrimaryKeySelective(routeRecord);
	}

	public int updateByPrimaryKey(RouteRecord routeRecord) {
		// TODO Auto-generated method stub
		return routeRecordDao.updateByPrimaryKey(routeRecord);
	}

	public List<RouteRecord> listAllRouteRecord(long nodeId, long limit,
			long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("nodeId", nodeId);
		return routeRecordDao.listAllRouteRecord(parameter);
	}

	public long getTotalCount(long nodeId) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("nodeId", nodeId);
		return routeRecordDao.getTotalCount(parameter);
	}


}
