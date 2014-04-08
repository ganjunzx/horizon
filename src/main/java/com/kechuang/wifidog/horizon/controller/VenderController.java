package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.model.VenderNode;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.UserService;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;

@Controller
@RequestMapping("/vender")
public class VenderController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

/*	@ResponseBody
	@RequestMapping(value = "/listNode", method = RequestMethod.POST)
	public Map<String, Object> listNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String nodeStatus = request.getParameter("nodeStatus");

		Map<String, Object> listNode = new HashMap<String, Object>();
		if (nodeStatus != null && !nodeStatus.equals("")) {
			listNode.put("total", 0L);
			listNode.put("rows", new ArrayList<Node>());
		} else {
			long page = Long
					.parseLong((strPage == null || strPage.equals("0")) ? "1"
							: strPage);
			long limit = Long
					.parseLong((strRows == null || strRows.equals("0")) ? "1"
							: strRows);
			long offset = (page - 1) * limit;

			List<Node> nodes = nodeService.listAllNode(limit, offset);
			List<VenderNode> venderNodes = new ArrayList<VenderNode>();
			for (Node node : nodes) {
				long venderID = node.getVenderID();
				long businessID = node.getBusinessID();
				if (venderID != -1) {
					node.setVenderName(userService.selectByPrimaryKey(venderID)
							.getCompanyName());
				}

				if (businessID != -1) {
					node.setBusinessName(userService.selectByPrimaryKey(
							businessID).getCompanyName());
				}
				VenderNode venderNode = new VenderNode();
				venderNode.setId(node.getId());
				venderNode.setmCode(node.getmCode());
				venderNode.setNdName(node.getNdName());
				venderNode.setUniqueName(node.getUniqueName());
				if (venderID != -1) {
					venderNode.setVenderName(userService.selectByPrimaryKey(
							venderID).getCompanyName());
				}
				venderNode.setCreateTime(node.getCreateTime());

				venderNodes.add(venderNode);
			}
			listNode.put("total", nodeService.getTotalCount());
			listNode.put("rows", venderNodes);
		}
		return listNode;
	}*/

	@ResponseBody
	@RequestMapping(value = "/searchNode", method = RequestMethod.POST)
	public Map<String, Object> searchNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String nodeStatus = request.getParameter("nodeStatus");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");

		Date startTime = null;
		Date endTime = null;

		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		Map<String, Object> listNode = new HashMap<String, Object>();
		if (nodeStatus == null || nodeStatus.equals("")) {
			nodeStatus = "-1";
		}

		try {
			if ((strStartTime != null && !strStartTime.equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						HorizonConfig.DATA_FORMAT);
				startTime = sdf.parse(strStartTime);
			}

			if ((strEndTime != null && !strEndTime.equals(""))) {
				SimpleDateFormat sdf = new SimpleDateFormat(
						HorizonConfig.DATA_FORMAT);
				endTime = sdf.parse(strEndTime);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Node> nodes = nodeService.listAllNode(loginUser.getId(), -1,
				startTime, endTime, Byte.parseByte(nodeStatus), limit, offset);
		List<VenderNode> venderNodes = new ArrayList<VenderNode>();
		for (Node node : nodes) {
			long venderID = node.getVenderID();
			if (venderID != -1) {
				node.setVenderName(userService.selectByMap(venderID)
						.getCompanyName());
			}

			VenderNode venderNode = new VenderNode();
			venderNode.setId(node.getId());
			venderNode.setmCode(node.getmCode());
			venderNode.setNdName(node.getNdName());
			if (venderID != -1) {
				venderNode.setVenderName(userService.selectByMap(
						venderID).getCompanyName());
			}
			venderNode.setCreateTime(node.getCreateTime());

			venderNodes.add(venderNode);
		}
		listNode.put("total", nodeService.getTotalCount(loginUser.getId(), -1,
				startTime, endTime, Byte.parseByte(nodeStatus)));
		listNode.put("rows", venderNodes);
		return listNode;
	}
}