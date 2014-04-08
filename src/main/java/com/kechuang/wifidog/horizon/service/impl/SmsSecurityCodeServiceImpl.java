package com.kechuang.wifidog.horizon.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.SmsSecurityCodeDao;
import com.kechuang.wifidog.horizon.model.SmsSecurityCode;
import com.kechuang.wifidog.horizon.service.SmsSecurityCodeService;

@Service("com.kechuang.wifidog.horizon.service.impl.SmsSecurityCodeService")
public class SmsSecurityCodeServiceImpl implements SmsSecurityCodeService{
	
	private static final Logger LOG = Logger.getLogger(SmsSecurityCodeServiceImpl.class);
	
	@Autowired
	private SmsSecurityCodeDao smsSecurityCodeDao;
	
	public SmsSecurityCodeServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(long id) {
		// TODO Auto-generated method stub
		return smsSecurityCodeDao.deleteByPrimaryKey(id);
	}

	public int insert(SmsSecurityCode smsSecurityCode) {
		// TODO Auto-generated method stub
		return smsSecurityCodeDao.insert(smsSecurityCode);
	}

	public int insertSelective(SmsSecurityCode smsSecurityCode) {
		// TODO Auto-generated method stub
		return smsSecurityCodeDao.insertSelective(smsSecurityCode);
	}

	public SmsSecurityCode selectByMap(long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		return smsSecurityCodeDao.selectByMap(parameter);
	}

	public int updateByPrimaryKeySelective(SmsSecurityCode smsSecurityCode) {
		// TODO Auto-generated method stub
		return smsSecurityCodeDao.updateByPrimaryKeySelective(smsSecurityCode);
	}

	public int updateByPrimaryKey(SmsSecurityCode smsSecurityCode) {
		// TODO Auto-generated method stub
		return smsSecurityCodeDao.updateByPrimaryKey(smsSecurityCode);
	}

	public List<SmsSecurityCode> listAllSmsSecurityCode(long userID, long nodeID,
			String cellPhoneNum, String securityCode, byte mobileType, Date startTime, Date endTime, long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("nodeID", nodeID);
		parameter.put("cellPhoneNum", cellPhoneNum);
		parameter.put("securityCode", securityCode);
		parameter.put("mobileType", mobileType);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return smsSecurityCodeDao.listAllSmsSecurityCode(parameter);
	}

	public long getTotalCount(long userID, long nodeID, String cellPhoneNum, String securityCode, byte mobileType, Date startTime, Date endTime) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("userID", userID);
		parameter.put("nodeID", nodeID);
		parameter.put("cellPhoneNum", cellPhoneNum);
		parameter.put("securityCode", securityCode);
		parameter.put("mobileType", mobileType);
		parameter.put("startTime", startTime);
		parameter.put("endTime", endTime);
		return smsSecurityCodeDao.getTotalCount(parameter);
	}


}
