package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;

@Controller
@RequestMapping("/userlog")
public class UserLogController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;
	
	@ResponseBody
	@RequestMapping(value = "/listLog", method = RequestMethod.POST)
	public Map<String, Object> listLog(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String status = request.getParameter("status");
		String nodeID = request.getParameter("nodeID");

		Map<String, Object> listUser = new HashMap<String, Object>();
		if (status != null && !status.equals("")) {
			long page = Long
					.parseLong((strPage == null || strPage.equals("0")) ? "1"
							: strPage);
			long limit = Long
					.parseLong((strRows == null || strRows.equals("0")) ? "1"
							: strRows);
			long offset = (page - 1) * limit;
			listUser.put("total", nodeConnectionService.getTotalCount(Long.parseLong(nodeID), Byte.parseByte(status)));
			listUser.put("rows",
					nodeConnectionService.listAllConnections(Long.parseLong(nodeID), Byte.parseByte(status), limit, offset));
		} else {
			listUser.put("total", 0L);
			listUser.put("rows", new ArrayList<NodeConnection>());
		}
		return listUser;
	}
}
