package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.SmsContentDao;
import com.kechuang.wifidog.horizon.model.SmsContent;
import com.kechuang.wifidog.horizon.service.SmsContentService;

@Service("com.kechuang.wifidog.horizon.service.impl.SmsContentService")
public class SmsContentServiceImpl implements SmsContentService{
	
	private static final Logger LOG = Logger.getLogger(SmsContentServiceImpl.class);
	
	@Autowired
	private SmsContentDao smsContentDao;
	
	public SmsContentServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return smsContentDao.deleteByPrimaryKey(id);
	}

	public int insert(SmsContent smsRecord) {
		// TODO Auto-generated method stub
		return smsContentDao.insert(smsRecord);
	}

	public int insertSelective(SmsContent smsRecord) {
		// TODO Auto-generated method stub
		return smsContentDao.insertSelective(smsRecord);
	}

	public SmsContent selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("userID", -1);
		return smsContentDao.selectByMap(parameter);
	}

	public SmsContent selectByUseId(long useID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("userID", useID);
		return smsContentDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(SmsContent smsRecord) {
		// TODO Auto-generated method stub
		return smsContentDao.updateByPrimaryKeySelective(smsRecord);
	}

	public int updateByPrimaryKey(SmsContent smsRecord) {
		// TODO Auto-generated method stub
		return smsContentDao.updateByPrimaryKey(smsRecord);
	}

	public List<SmsContent> listAllSmsContent(long userID, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("userID", userID);
		parameter.put("offset", offset);
		return smsContentDao.listAllSmsContent(parameter);
	}

	public long getTotalCount(long userID) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		return smsContentDao.getTotalCount(parameter);
	}


}
