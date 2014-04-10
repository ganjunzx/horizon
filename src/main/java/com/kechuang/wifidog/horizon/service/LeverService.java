package com.kechuang.wifidog.horizon.service;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.Lever;
import com.kechuang.wifidog.horizon.model.SmsContent;

public interface LeverService {
	int deleteByPrimaryKey(long id);
	int insert(Lever lever);
	int insertSelective(Lever lever);
	Lever selectByMap(long id);
	Lever selectByMap(String vip);
	int updateByPrimaryKeySelective(Lever lever);
	int updateByPrimaryKey(Lever lever);
	List<Lever> listAllLever(long limit, long offset);
	long getTotalCount();
}
