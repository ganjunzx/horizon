package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.Tokens;

public interface TokensDao {
	int deleteByMap(Map<String, Object> map);
    int insert(Tokens tokens);
    int insertSelective(Tokens tokens);
    Tokens selectByMap(Map<String, Object> map);
    int updateByPrimaryKeySelective(Tokens tokens);
    int updateByPrimaryKey(Tokens tokens);
/*    List<Node> listAllNode();*/
    long getTotalCount();
	List<Tokens> listAllTokens(Map<String, Object> map);
}
