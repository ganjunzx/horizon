package com.kechuang.wifidog.horizon.service;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.Tokens;

public interface TokensService {

	int deleteByPrimaryKey(Long id);
	
	int deleteByStatus(byte status);

	int insert(Tokens tokens);

	int insertSelective(Tokens tokens);

	Tokens selectByMap(Long id);
	Tokens selectByMap(String token);

	int updateByPrimaryKeySelective(Tokens tokens);

	int updateByPrimaryKey(Tokens tokens);

	long getTotalCount();

	List<Tokens> listAllTokens(long limit, long offset);
}
