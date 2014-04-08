package com.kechuang.wifidog.horizon.service;

import java.util.List;

import com.kechuang.wifidog.horizon.model.Tree;
import com.kechuang.wifidog.horizon.model.User;

public interface MenuService {
	List<Tree> getMenuByUser(User user) throws Exception;
}
