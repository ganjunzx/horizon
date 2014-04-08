package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.CommonUserDao;
import com.kechuang.wifidog.horizon.dao.RouteRecordDao;
import com.kechuang.wifidog.horizon.dao.RouteStatusDao;
import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.RouteRecord;
import com.kechuang.wifidog.horizon.model.RouteStatus;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.RouteRecordService;
import com.kechuang.wifidog.horizon.service.RouteStatusService;

@Service("com.kechuang.wifidog.horizon.service.impl.RouteStatusService")
public class RouteStatusServiceImpl implements RouteStatusService{
	
	private static final Logger LOG = Logger.getLogger(RouteStatusServiceImpl.class);
	
	@Autowired
	private RouteStatusDao routeStatusDao;
	
	public RouteStatusServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return routeStatusDao.deleteByPrimaryKey(id);
	}

	public int insert(RouteStatus routeStatus) {
		// TODO Auto-generated method stub
		return routeStatusDao.insert(routeStatus);
	}

	public int insertSelective(RouteStatus routeStatus) {
		// TODO Auto-generated method stub
		return routeStatusDao.insertSelective(routeStatus);
	}

	public RouteStatus selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		return routeStatusDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(RouteStatus routeStatus) {
		// TODO Auto-generated method stub
		return routeStatusDao.updateByPrimaryKeySelective(routeStatus);
	}

	public int updateByPrimaryKey(RouteStatus routeStatus) {
		// TODO Auto-generated method stub
		return routeStatusDao.updateByPrimaryKey(routeStatus);
	}

	public List<RouteStatus> listAllRouteStatus(long nodeID, long limit,
			long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		parameter.put("nodeID", nodeID);
		return routeStatusDao.listAllRouteStatus(parameter);
	}

	public long getTotalCount(long nodeId) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("nodeId", nodeId);
		return routeStatusDao.getTotalCount(parameter);
	}

}
