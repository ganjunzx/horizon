package com.kechuang.wifidog.horizon.service;

import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.NodeConnection;


public interface CoreUtilService {
	boolean checkConnectOne(NodeConnection nodeConnection, NodeConnection editNodeConnection, Node node);
	boolean checkConnectTwo(NodeConnection nodeConnection, NodeConnection editNodeConnection, Node node);
}
