package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.Result;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.model.UserInfo;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.UserService;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;
import com.kechuang.wifidog.horizon.utils.SessionUtils;

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Logger LOG = Logger.getLogger(CoreController.class);

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;

	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Result result = new Result();
		if (session.getAttribute("user") != null) {
			result.setSuccess(true);
			return result;
		}
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		if (userName == null || "".equals(userName) || password == null
				|| "".equals(password)) {
			result.setSuccess(false);
			result.setMsg("用户名或者密码不能为空！");
			return result;
		}
		/*String validateCode = request.getParameter("verifyCode");
		if (!SessionUtils.getValidateCode(request).equalsIgnoreCase(
				validateCode)) {
			result.setSuccess(false);
			result.setMsg("验证码错误，请重新输入！");
			return result;
		}*/

		User user = userService.selectByMap(userName, password);

		if (user == null) {
			result.setSuccess(false);
			result.setMsg("用户名或者密码输入错误！");
			return result;
		} else {
			session.setAttribute("user", user);
			result.setSuccess(true);
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/core/login", method = RequestMethod.POST)
	public Result coreLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Result result = new Result();
		String gateWayID = (String)request.getParameter("gw_id");
		String loginType = (String)request.getParameter("loginType");
		if (gateWayID == null || "".equals(gateWayID) || loginType == null || !(Byte.parseByte(loginType) == HorizonConfig.NODE_LOGINTYPE.WEB.getIndex())) {
			result.setSuccess(false);
			result.setMsg("错误登录！");
			return result;
		}
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		if (userName == null || "".equals(userName) || password == null
				|| "".equals(password)) {
			result.setSuccess(false);
			result.setMsg("用户名或者密码不能为空！");
			return result;
		}
		Node node = nodeService.selectByNodeName(gateWayID);
		if (node != null) {
			CommonUser commonUser = commonUserService.selectByMap(userName,
					node.getBusinessID());

			if (commonUser == null) {
				result.setSuccess(false);
				result.setMsg("用户不存在！");
				return result;
			} else {
				if (commonUser.getPassword().equals(password)) {
					if (commonUser.getNodeID() == -2) {
						request.getRequestDispatcher("../../core/login/").forward(request, response);
						return result;
					} else {
						if (commonUser.getNodeID() != node.getId()) {
							result.setSuccess(false);
							result.setMsg("错误登录！");
							return result;
						}
						request.getRequestDispatcher("../../core/login/").forward(request, response);
						/*response.sendRedirect(request.getContextPath() + "/core/login/");*/
						return result;
					}
					
				} else {
					result.setSuccess(false);
					result.setMsg("密码错误！");
					return result;
				}
			}
		} else {
			result.setSuccess(false);
			result.setMsg("错误登录！");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/userInfo", method = RequestMethod.POST)
	public Result userInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Result result = new Result();
		User user = (User) session.getAttribute("user");
		if (user != null) {
/*			UserInfo userInfo = new UserInfo();
			userInfo.setUserName(user.getUserName());
			userInfo.setUserType(user.getUserType());*/
			result.setObj(userService.selectByMap(user.getId()));
			result.setSuccess(true);
			return result;
		} else {
			result.setSuccess(false);
			result.setMsg("用户没有登录！");
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath());
	}
}
