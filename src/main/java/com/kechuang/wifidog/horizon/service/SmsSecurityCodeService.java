package com.kechuang.wifidog.horizon.service;

import java.util.Date;
import java.util.List;

import com.kechuang.wifidog.horizon.model.SmsSecurityCode;

public interface SmsSecurityCodeService {
	int deleteByPrimaryKey(long id);
    int insert(SmsSecurityCode smsSecurityCode);
    int insertSelective(SmsSecurityCode smsSecurityCode);
    SmsSecurityCode selectByMap(long id);
    int updateByPrimaryKeySelective(SmsSecurityCode smsSecurityCode);
    int updateByPrimaryKey(SmsSecurityCode smsSecurityCode);
	List<SmsSecurityCode> listAllSmsSecurityCode(long userID, long nodeID, String cellPhoneNum, String securityCode, byte mobileType, Date startTime, Date endTime, long limit, long offset);
	long getTotalCount(long userID, long nodeID, String cellPhoneNum, String securityCode, byte mobileType, Date startTime, Date endTime);
}
