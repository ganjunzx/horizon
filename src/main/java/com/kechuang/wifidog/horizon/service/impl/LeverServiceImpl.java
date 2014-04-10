package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.LeverDao;
import com.kechuang.wifidog.horizon.model.Lever;
import com.kechuang.wifidog.horizon.service.LeverService;

@Service("com.kechuang.wifidog.horizon.service.impl.LeverService")
public class LeverServiceImpl implements LeverService{
	
	private static final Logger LOG = Logger.getLogger(LeverServiceImpl.class);
	
	@Autowired
	private LeverDao leverDao;
	
	public LeverServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return leverDao.deleteByPrimaryKey(id);
	}

	public int insert(Lever lever) {
		// TODO Auto-generated method stub
		return leverDao.insert(lever);
	}

	public int insertSelective(Lever lever) {
		// TODO Auto-generated method stub
		return leverDao.insertSelective(lever);
	}

	public Lever selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("vip", null);
		return leverDao.selectByMap(parameter);
	}

	public Lever selectByUseId(long useID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		return leverDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(Lever lever) {
		// TODO Auto-generated method stub
		return leverDao.updateByPrimaryKeySelective(lever);
	}

	public int updateByPrimaryKey(Lever lever) {
		// TODO Auto-generated method stub
		return leverDao.updateByPrimaryKey(lever);
	}

	public List<Lever> listAllLever(long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return leverDao.listAllLever(parameter);
	}

	public long getTotalCount() {
		// TODO Auto-generated method stub
		return leverDao.getTotalCount();
	}

	public Lever selectByMap(String vip) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("vip", vip);
		parameter.put("id", -1);
		return leverDao.selectByMap(parameter);
	}

}
