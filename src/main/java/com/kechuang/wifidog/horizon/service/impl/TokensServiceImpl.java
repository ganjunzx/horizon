package com.kechuang.wifidog.horizon.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.dao.NodeDao;
import com.kechuang.wifidog.horizon.dao.TokensDao;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.Tokens;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.TokensService;

@Service("com.kechuang.wifidog.horizon.service.impl.TokensService")
public class TokensServiceImpl implements TokensService{
	
	private static final Logger LOG = Logger.getLogger(TokensServiceImpl.class);
	
	@Autowired
	private TokensDao tokensDao;
	
	public TokensServiceImpl() {
		super();
	}

	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("token", null);
		parameter.put("status", -1);
		return tokensDao.deleteByMap(parameter);
	}

	public int insert(Tokens tokens) {
		// TODO Auto-generated method stub
		return tokensDao.insert(tokens);
	}

	public int insertSelective(Tokens tokens) {
		// TODO Auto-generated method stub
		return tokensDao.insertSelective(tokens);
	}

	public int updateByPrimaryKeySelective(Tokens tokens) {
		// TODO Auto-generated method stub
		return tokensDao.updateByPrimaryKeySelective(tokens);
	}

	public int updateByPrimaryKey(Tokens tokens) {
		// TODO Auto-generated method stub
		return tokensDao.updateByPrimaryKey(tokens);
	}

	public long getTotalCount() {
		// TODO Auto-generated method stub
		return tokensDao.getTotalCount();
	}

	public List<Tokens> listAllTokens(long limit, long offset) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("limit", limit);
		parameter.put("offset", offset);
		return tokensDao.listAllTokens(parameter);
	}

	public Tokens selectByMap(Long id) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", id);
		parameter.put("token", null);
		return tokensDao.selectByMap(parameter);
	}

	public Tokens selectByMap(String token) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("token", token);
		return tokensDao.selectByMap(parameter);
	}

	public int deleteByStatus(byte status) {
		// TODO Auto-generated method stub
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("id", -1);
		parameter.put("token", null);
		parameter.put("status", status);
		return tokensDao.deleteByMap(parameter);
	}


}
