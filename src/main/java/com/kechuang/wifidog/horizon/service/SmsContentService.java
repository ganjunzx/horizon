package com.kechuang.wifidog.horizon.service;

import java.util.List;

import com.kechuang.wifidog.horizon.model.SmsContent;

public interface SmsContentService {
	int deleteByPrimaryKey(long id);
    int insert(SmsContent smsRecord);
    int insertSelective(SmsContent smsRecord);
    SmsContent selectByMap(long id);
    SmsContent selectByUseId(long userID);
    int updateByPrimaryKeySelective(SmsContent smsRecord);
    int updateByPrimaryKey(SmsContent smsRecord);
	List<SmsContent> listAllSmsContent(long userID, long limit, long offset);
	long getTotalCount(long userID);
}
