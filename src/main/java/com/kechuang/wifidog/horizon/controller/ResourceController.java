package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.Result;
import com.kechuang.wifidog.horizon.model.Tree;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.service.MenuService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.UserService;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.MenuService")
	private MenuService menuService;

	@ResponseBody
	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	public List<Tree> tree(HttpSession session) throws Exception {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			List<Tree> treeList = null;
			try {
				treeList = menuService.getMenuByUser(user);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return treeList;
		}
		return null;
	}
}
