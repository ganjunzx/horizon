package com.kechuang.wifidog.horizon.service;

import java.util.Date;
import java.util.List;

import com.kechuang.wifidog.horizon.model.Node;

public interface NodeService {
	int deleteByPrimaryKey(long id);
    int insert(Node node);
    int insertSelective(Node node);
    Node selectByPrimaryKey(long id);
    Node selectByMCode(String mcode);
    Node selectByNodeName(String nodeName);
    int updateByPrimaryKeySelective(Node node);
    int updateByPrimaryKey(Node node);
/*	List<Node> listAllNode();*/
	List<Node> listAllNode(long limit, long offset);
	List<Node> listAllNode(Date startTime, Date endTime, long limit, long offset);
	List<Node> listAllNode(long venderID, Date startTime, Date endTime, long limit, long offset);
	List<Node> listAllNode(Date startTime, Date endTime, byte nodeStatus, long limit, long offset);
	List<Node> listAllNode(long venderID, long businessID, Date startTime, Date endTime, byte nodeStatus, long limit, long offset);
	long getTotalCount();
	long getTotalCount(long businessID);
	long getTotalCount(long venderID, long businessID, Date startTime, Date endTime, byte nodeStatus);
	long getTotalCount(long businessID, String ndName);
	long getTotalRemainSms();
}
