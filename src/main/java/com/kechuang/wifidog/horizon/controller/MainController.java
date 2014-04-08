package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.service.UserService;

@Controller
public class MainController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

	@ResponseBody
	@RequestMapping("/")
	public void getMain(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			response.sendRedirect(request.getContextPath() + "/innerIndex.html");
			/*request.getRequestDispatcher("/innerIndex.html").forward(request, response);*/
		} else {
			response.sendRedirect(request.getContextPath() + "/login.html");
			/*request.getRequestDispatcher("/login.html").forward(request, response);*/
			/*request.getRequestDispatcher("/user/login").forward(request, response);*/
		}
	}
}
