package com.kechuang.wifidog.horizon.dao;

import java.util.List;
import java.util.Map;

import com.kechuang.wifidog.horizon.model.Node;

public interface NodeDao {
	int deleteByPrimaryKey(Long id);
    int insert(Node node);
    int insertSelective(Node node);
    Node selectByPrimaryKey(Long id);
    Node selectByMCode(String mcode);
    Node selectByNodeName(String nodeName);
    int updateByPrimaryKeySelective(Node node);
    int updateByPrimaryKey(Node node);
/*    List<Node> listAllNode();*/
    long getTotalCount(Map<String, Object> map);
    long getTotalRemainSms(Map<String, Object> map);
	List<Node> listAllNode(Map<String, Object> map);
}
