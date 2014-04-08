package com.kechuang.wifidog.horizon.service;

import java.util.Date;
import java.util.List;

import com.kechuang.wifidog.horizon.model.UserTrade;

public interface UserTradeService {
	int deleteByPrimaryKey(long id);
    int insert(UserTrade userTrade);
    int insertSelective(UserTrade userTrade);
    UserTrade selectByMap(long id);
    int updateByPrimaryKeySelective(UserTrade userTrade);
    int updateByPrimaryKey(UserTrade userTrade);
	List<UserTrade> listAllUserTrade(long userID, byte tradeType, Date startTime, Date endTime, long limit, long offset);
	List<UserTrade> listAllUserTrade(byte tradeType, byte status, Date startTime, Date endTime, long limit, long offset);
	long getTotalCount(long userID, byte tradeType, Date startTime, Date endTime);
	long getTotalCount(byte tradeType, byte status, Date startTime, Date endTime);
}
