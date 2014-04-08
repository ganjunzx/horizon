package com.kechuang.wifidog.horizon.service;

import java.util.Date;
import java.util.List;

import com.kechuang.wifidog.horizon.model.SmsTrade;

public interface SmsTradeService {
	int deleteByPrimaryKey(long id);
    int insert(SmsTrade smsTrade);
    int insertSelective(SmsTrade smsTrade);
    SmsTrade selectByMap(long id);
    int updateByPrimaryKeySelective(SmsTrade smsTrade);
    int updateByPrimaryKey(SmsTrade smsTrade);
	List<SmsTrade> listAllSmsTrade(long userID, long limit, long offset);
	List<SmsTrade> listAllSmsTrade(long userID, long nodeID, Date startTime, Date endTime, long limit, long offset);
	long getTotalCount(long userID);
	long getTotalCount(long userID, long nodeID, Date startTime, Date endTime);
}
