package com.kechuang.wifidog.horizon.service;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.CommonUser;

public interface CommonUserService {
	int deleteByPrimaryKey(long id);
    int insert(CommonUser commonUser);
    int insertSelective(CommonUser commonUser);
    CommonUser selectByMap(long id);
    CommonUser selectByMap(String userName, long businessID);
    int updateByPrimaryKeySelective(CommonUser commonUser);
    int updateByPrimaryKey(CommonUser commonUser);
	List<CommonUser> listAllUser(long businessID, long limit, long offset);
	long getTotalCount(long businessID);
}
