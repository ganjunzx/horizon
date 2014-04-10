package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.Lever;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.model.NodeLever;
import com.kechuang.wifidog.horizon.model.Result;
import com.kechuang.wifidog.horizon.model.SmsContent;
import com.kechuang.wifidog.horizon.model.SmsSecurityCode;
import com.kechuang.wifidog.horizon.model.SmsTrade;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.model.UserInfo;
import com.kechuang.wifidog.horizon.model.UserTrade;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.LeverService;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;
import com.kechuang.wifidog.horizon.service.NodeLeverService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.SmsContentService;
import com.kechuang.wifidog.horizon.service.SmsSecurityCodeService;
import com.kechuang.wifidog.horizon.service.SmsTradeService;
import com.kechuang.wifidog.horizon.service.UserService;
import com.kechuang.wifidog.horizon.service.UserTradeService;
import com.kechuang.wifidog.horizon.utils.ChineseSmsUtils;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;
import com.kechuang.wifidog.horizon.utils.PropertiesTool;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsContentService")
	private SmsContentService smsContentService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserTradeService")
	private UserTradeService userTradeService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsTradeService")
	private SmsTradeService smsTradeService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsSecurityCodeService")
	private SmsSecurityCodeService smsSecurityCodeService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.LeverService")
	private LeverService leverService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeLeverService")
	private NodeLeverService nodeLeverService;
	
	@ResponseBody
	@RequestMapping(value = "/treeNode", method = RequestMethod.POST)
	public List<Node> treeNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		List<Node> nodes = nodeService.listAllNode(-1L, -1L, null, null,
				(byte) -1, -1L, -1L);
		List<Node> listNodes = new ArrayList<Node>();
		Node initNode = new Node();
		initNode.setId(-2);
		initNode.setmCode("所有热点");
		listNodes.add(initNode);
		listNodes.addAll(nodes);
		return listNodes;
	}
	
	@ResponseBody
	@RequestMapping(value = "/searchSmsTrade", method = RequestMethod.POST)
	public Map<String, Object> searchSmsTrade(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String userName = request.getParameter("userName");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");

		long userID = -1;
		Date startTime = null;
		Date endTime = null;
		if (userName != null && !"".equals(userName)) {
			User user = userService.selectByMap(userName);
			if (user != null) {
				userID = user.getId();
			}
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

		Map<String, Object> mapSmsTrades = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		List<SmsTrade> listSmsTrades = smsTradeService.listAllSmsTrade(userID, -1, startTime, endTime, limit, offset);
		
		for (SmsTrade smsTrade : listSmsTrades) {
			Node nodeTemp = nodeService.selectByPrimaryKey(smsTrade.getNodeID());
			if (nodeTemp != null) {
				if (nodeTemp.getAliasName() != null && !"".equals(nodeTemp.getAliasName())) {
					smsTrade.setNdName(nodeTemp.getAliasName());
				} else {
					smsTrade.setNdName(nodeTemp.getNdName());
				}
			}
			
			User user = userService.selectByMap(smsTrade.getUserID());
			smsTrade.setUserName(user.getUserName());
		}
		mapSmsTrades.put("total", smsTradeService.getTotalCount(
				userID, -1, startTime, endTime));
		mapSmsTrades.put("rows", listSmsTrades);
		return mapSmsTrades;
	}

	@ResponseBody
	@RequestMapping(value = "/sumAcountInfo", method = RequestMethod.POST)
	public Result sumAcountInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}
		Result result = new Result();
		if (loginUser.getUserType() == User.USER_TYPE.SUPERUSER.ordinal()) {
			double sumAcount = userService
					.getTotalAcount((byte) User.USER_TYPE.BUSINESSUSER
							.ordinal());
			result.setSuccess(true);
			result.setObj(sumAcount);
		} else {
			result.setSuccess(false);
			result.setMsg("获取账户信息失败！");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/remainSystemSms", method = RequestMethod.POST)
	public Result remainSystemSms(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}
		Result result = new Result();
		if (loginUser.getUserType() == User.USER_TYPE.SUPERUSER.ordinal()) {
			PropertiesTool propertiesTool = new PropertiesTool();
			propertiesTool.loadFile("horizon.properties", "UTF-8");
			String userName = propertiesTool.getString("horizon.chinese.sms.uid");
			String key = propertiesTool.getString("horizon.chinese.sms.key");
			ChineseSmsUtils chineseSysUtils = new ChineseSmsUtils();
			long remainSystemSms = chineseSysUtils.remainSystemSms(userName, key);
			result.setSuccess(true);
			result.setObj(remainSystemSms);
		} else {
			result.setSuccess(false);
			result.setMsg("获取信息失败！");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/sumUserSms", method = RequestMethod.POST)
	public Result sumUserSms(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}
		Result result = new Result();
		if (loginUser.getUserType() == User.USER_TYPE.SUPERUSER.ordinal()) {
			long totaoCountNode = nodeService.getTotalCount();
			if (totaoCountNode != 0) {
				long sumUserSms = nodeService.getTotalRemainSms();
				result.setSuccess(true);
				result.setObj(sumUserSms);
			} else {
				result.setSuccess(true);
				result.setObj(0);
			}
			
		} else {
			result.setSuccess(false);
			result.setMsg("获取信息失败！");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "/searchSmsSecurityCode", method = RequestMethod.POST)
	public Map<String, Object> searchSmsSecurityCode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String strMobileType = request.getParameter("mobileType");
		String userName = request.getParameter("userName");
		String strLastTime = request.getParameter("lastTime");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");
		String cellPhoneNum = request.getParameter("cellPhoneNum");

		long userID = -1;
		byte mobileType = -1;
		Date startTime = null;
		Date endTime = null;

		Result result = new Result();

		if (userName != null && !"".equals(userName)) {
			User user = userService.selectByMap(userName);
			if (user != null) {
				userID = user.getId();
			}
		}

		if (strMobileType != null && !"".equals(strMobileType)) {
			mobileType = Byte.parseByte(strMobileType);
		}

		try {
			if (strLastTime != null && !"".equals(strLastTime)) {
				if ("hour".equals(strLastTime)) {
					long currentTime = Calendar.getInstance(
							TimeZone.getDefault()).getTimeInMillis();
					startTime = new Date(currentTime - 60 * 60 * 1000);
					endTime = new Date(currentTime);
				} else if ("4hr".equals(strLastTime)) {
					long currentTime = Calendar.getInstance(
							TimeZone.getDefault()).getTimeInMillis();
					startTime = new Date(currentTime - 4 * 60 * 60 * 1000);
					endTime = new Date(currentTime);
				} else if ("day".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.DAY_OF_MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("week".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.WEEK_OF_MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("month".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("year".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.YEAR, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else {
					result.setSuccess(false);
					result.setMsg("搜索失败！内部错误!");
				}
			} else {
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
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> mapNodeConnections = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		List<SmsSecurityCode> listSmsSecurityCodes = smsSecurityCodeService
				.listAllSmsSecurityCode(userID, -1, cellPhoneNum, null, mobileType, startTime, endTime, limit, offset);
		for (SmsSecurityCode smsSecurityCode : listSmsSecurityCodes) {
			Node node = nodeService.selectByPrimaryKey(smsSecurityCode
					.getNodeID());
			if (node != null) {
				String nodeName = node.getAliasName();
				if (nodeName == null || nodeName.equals("")) {
					nodeName = node.getNdName();
				}
				smsSecurityCode.setNdName(nodeName);
			} else {
				smsSecurityCode.setNdName("未知路由");
			}
		}
		mapNodeConnections.put("total", smsSecurityCodeService.getTotalCount(
				userID, -1, cellPhoneNum, null, mobileType, startTime, endTime));
		mapNodeConnections.put("rows", listSmsSecurityCodes);
		return mapNodeConnections;
	}
	
	@ResponseBody
	@RequestMapping(value = "/businessAcountInfo", method = RequestMethod.POST)
	public Result businessAcountInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String userID = request.getParameter("userID");
		Result result = new Result();
		if (userID != null && !"".equals(userID)) {
			User user = userService.selectByMap(Long.parseLong(userID));
			if (user != null) {
				result.setSuccess(true);
				result.setObj(user.getAcount());
			} else {
				result.setSuccess(false);
				result.setMsg("获取账户信息失败！");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("获取账户信息失败！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/searchUserTrade", method = RequestMethod.POST)
	public Map<String, Object> searchUserTrade(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String strTradeType = request.getParameter("tradeType");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");
		String strUserTradeStatus = request.getParameter("userTradeStatus");

		byte tradeType = -1;
		Date startTime = null;
		Date endTime = null;
		byte tradeStatus = -1;
		if (strTradeType != null && !"".equals(strTradeType)) {
			tradeType = Byte.parseByte(strTradeType);
		}

		if (strUserTradeStatus != null && !"".equals(strUserTradeStatus)) {
			tradeStatus = Byte.parseByte(strUserTradeStatus);
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

		Map<String, Object> mapUserTrades = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		List<UserTrade> listUserTrades = userTradeService.listAllUserTrade(
				tradeType, tradeStatus, startTime, endTime, limit, offset);
		
		for (UserTrade userTrade : listUserTrades) {
			User user = userService.selectByMap(userTrade.getUserID());
			userTrade.setUserName(user.getUserName());
		}
		mapUserTrades.put("total", userTradeService.getTotalCount(tradeType,
				tradeStatus, startTime, endTime));
		mapUserTrades.put("rows", listUserTrades);
		return mapUserTrades;
	}
	
	@ResponseBody
	@RequestMapping(value = "/cancelTrade", method = RequestMethod.POST)
	public Result cancelTrade(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String strID = request.getParameter("id");

		Result result = new Result();
		long id = -1;
		try {
			if (strID != null && !"".equals(strID)) {
				id = Long.parseLong(strID);
				UserTrade userTrade = userTradeService.selectByMap(id);
				if (userTrade != null) {
					if (userTrade.getStatus() == HorizonConfig.USERTRADE_TRADE_STATUS.CHECKING
							.getIndex()) {
								User user = userService.selectByMap(userTrade
										.getUserID());
								if (user != null) {
										UserTrade editUserTrade = new UserTrade();
										editUserTrade.setId(userTrade.getId());


										editUserTrade
												.setStatus((byte) HorizonConfig.USERTRADE_TRADE_STATUS.CANCEL
														.getIndex());

										userTradeService
												.updateByPrimaryKeySelective(editUserTrade);
										result.setSuccess(true);
								} else {
									result.setSuccess(false);
									result.setMsg("操作失败！用户登录过期或者没有此用户！");
								}
					} else {
						result.setSuccess(false);
						result.setMsg("操作失败！交易状态错误，必须是审核状态！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("操作失败！没有此记录！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("操作失败！没有此记录！");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("操作失败！内部错误");
			e.printStackTrace();
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/recharge", method = RequestMethod.POST)
	public Result recharge(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String strTotalPrice = request.getParameter("totalPrice");
		String description = request.getParameter("description");
		String strID = request.getParameter("id");

		Result result = new Result();
		double totalPrice = -1;
		long id = -1;
		try {
			if (strID != null && !"".equals(strID)) {
				id = Long.parseLong(strID);
				UserTrade userTrade = userTradeService.selectByMap(id);
				if (userTrade != null) {
					if (userTrade.getStatus() == HorizonConfig.USERTRADE_TRADE_STATUS.CHECKING
							.getIndex()) {
						if (userTrade.getTradeType() == HorizonConfig.USERTRADE_TRADE_TYPE.RECHARGE
								.getIndex()) {
							if (userTrade.getTradeWay() == HorizonConfig.USERTRADE_TRADE_WAY.BANK
									.getIndex()) {
								User user = userService.selectByMap(userTrade
										.getUserID());
								if (user != null) {
									if (strTotalPrice != null
											&& !"".equals(strTotalPrice)) {
										totalPrice = Double
												.parseDouble(strTotalPrice);
									}
									if (totalPrice > 0) {
										UserTrade editUserTrade = new UserTrade();
										editUserTrade.setId(userTrade.getId());
										if (totalPrice != userTrade
												.getTotalPrice()) {
											editUserTrade
													.setTotalPrice(totalPrice);
										}

										if (!description.equals(userTrade
												.getDescription())) {
											editUserTrade
													.setDescription(description);
										}

										editUserTrade
												.setStatus((byte) HorizonConfig.USERTRADE_TRADE_STATUS.SUCESSED
														.getIndex());

										userTradeService
												.updateByPrimaryKeySelective(editUserTrade);
										User editUser = new User();
										editUser.setId(user.getId());
										editUser.setAcount(user.getAcount()
												+ totalPrice);
										userService
												.updateByPrimaryKeySelective(editUser);
										result.setSuccess(true);
									} else {
										result.setSuccess(false);
										result.setMsg("充值失败！充值金额不能为0！");
									}
								} else {
									result.setSuccess(false);
									result.setMsg("充值失败！用户登录过期或者没有此用户！");
								}
							} else {
								result.setSuccess(false);
								result.setMsg("充值失败！交易方式，必须是银行转账！");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("充值失败！交易类型错误，必须是充值类型！");
						}

					} else {
						result.setSuccess(false);
						result.setMsg("充值失败！交易状态错误，必须是审核状态！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("充值失败！没有此记录！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("充值失败！没有此记录！");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("充值失败！内部错误");
			e.printStackTrace();
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/enchashment", method = RequestMethod.POST)
	public Result enchashment(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String strTotalPrice = request.getParameter("totalPrice");
		String description = request.getParameter("description");
		String strID = request.getParameter("id");

		Result result = new Result();
		double totalPrice = -1;
		long id = -1;
		try {
			if (strID != null && !"".equals(strID)) {
				id = Long.parseLong(strID);
				UserTrade userTrade = userTradeService.selectByMap(id);
				if (userTrade != null) {
					if (userTrade.getStatus() == HorizonConfig.USERTRADE_TRADE_STATUS.CHECKING
							.getIndex()) {
						if (userTrade.getTradeType() == HorizonConfig.USERTRADE_TRADE_TYPE.ENCHASHMENT
								.getIndex()) {
							if (userTrade.getTradeWay() == HorizonConfig.USERTRADE_TRADE_WAY.BANK
									.getIndex()) {
								User user = userService.selectByMap(userTrade
										.getUserID());
								if (user != null) {
									if (strTotalPrice != null
											&& !"".equals(strTotalPrice)) {
										totalPrice = Double
												.parseDouble(strTotalPrice);
									}

									if (totalPrice > 0) {
										UserTrade editUserTrade = new UserTrade();
										editUserTrade.setId(userTrade.getId());
										if (totalPrice != userTrade
												.getTotalPrice()) {
											editUserTrade
													.setTotalPrice(totalPrice);
										}

										if (!description.equals(userTrade
												.getDescription())) {
											editUserTrade
													.setDescription(description);
										}

										editUserTrade
												.setStatus((byte) HorizonConfig.USERTRADE_TRADE_STATUS.SUCESSED
														.getIndex());

										if (totalPrice <= user.getAcount()) {
											userTradeService
													.updateByPrimaryKeySelective(editUserTrade);
											User editUser = new User();
											editUser.setId(user.getId());
											editUser.setAcount(user.getAcount()
													- totalPrice);
											userService
													.updateByPrimaryKeySelective(editUser);
											result.setSuccess(true);
										} else {
											result.setSuccess(false);
											result.setMsg("取现失败！取现金额大于账户剩余金额！");
										}

									} else {
										result.setSuccess(false);
										result.setMsg("取现失败！取现金额不能为0！");
									}
								} else {
									result.setSuccess(false);
									result.setMsg("取现失败！用户登录过期或者没有此用户！");
								}
							} else if (userTrade.getTradeWay() == HorizonConfig.USERTRADE_TRADE_WAY.ALIPAY
									.getIndex()) {

							} else {
								result.setSuccess(false);
								result.setMsg("取现失败！交易方式错误！");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("取现失败！交易类型错误，必须是取现类型！");
						}

					} else {
						result.setSuccess(false);
						result.setMsg("取现失败！交易状态错误，必须是审核状态！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("取现失败！没有此记录！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("取现失败！没有此记录！");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("取现失败！内部错误");
			e.printStackTrace();
			return result;
		}
	}

	@ResponseBody
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
			for (Node node : nodes) {
				long venderID = node.getVenderID();
				long businessID = node.getBusinessID();
				if (venderID != -1) {
					node.setVenderName(userService.selectByMap(venderID)
							.getCompanyName());
				}

				if (businessID != -1) {
					node.setBusinessName(userService.selectByMap(businessID)
							.getCompanyName());
				}
			}

			listNode.put("total", nodeService.getTotalCount());
			listNode.put("rows", nodes);
		}
		return listNode;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteNode", method = RequestMethod.POST)
	public Result deleteNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String id = request.getParameter("id");

		Result result = new Result();
		if (id != null && !"".equals(id)) {
			nodeService.deleteByPrimaryKey(Long.parseLong(id));
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMsg("删除路由失败！");
			// throw new
			// ServletException("Add user errer! Maybe user name or password is null!");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/searchLog", method = RequestMethod.POST)
	public Map<String, Object> searchLog(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String strStatus = request.getParameter("status");
		String strNodeID = request.getParameter("nodeID");
		String strLastTime = request.getParameter("lastTime");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");
		String userName = request.getParameter("userName");

		long nodeID = -1;
		byte status = -1;
		Date startTime = null;
		Date endTime = null;
		long userID = -1;

		Result result = new Result();
		if (userName != null && !"".equals(userName)) {
			User user = userService.selectByMap(userName);
			if (user == null) {
				result.setSuccess(false);
				result.setMsg("搜索失败！用户\"" + userName + "\"不存在!");
			} else {
				userID = user.getId();
			}
		}

		if (strNodeID != null && !"".equals(strNodeID)) {
			if (!strNodeID.equals("-2")) {
				Node node = nodeService.selectByPrimaryKey(Long
						.parseLong(strNodeID));
				if (node == null) {
					result.setSuccess(false);
					result.setMsg("搜索失败！节点不存在!");
				} else {
					nodeID = Long.parseLong(strNodeID);
				}
			}
		}

		if (strStatus != null && !"".equals(strStatus)) {
			status = Byte.parseByte(strStatus);
		}

		try {
			if (strLastTime != null && !"".equals(strLastTime)) {
				if ("hour".equals(strLastTime)) {
					long currentTime = Calendar.getInstance(
							TimeZone.getDefault()).getTimeInMillis();
					startTime = new Date(currentTime - 60 * 60 * 1000);
					endTime = new Date(currentTime);
				} else if ("4hr".equals(strLastTime)) {
					long currentTime = Calendar.getInstance(
							TimeZone.getDefault()).getTimeInMillis();
					startTime = new Date(currentTime - 4 * 60 * 60 * 1000);
					endTime = new Date(currentTime);
				} else if ("day".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.DAY_OF_MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("week".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.WEEK_OF_MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("month".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.MONTH, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else if ("year".equals(strLastTime)) {
					Calendar cal = Calendar.getInstance(TimeZone.getDefault());
					long currentTime = cal.getTimeInMillis();
					cal.add(Calendar.YEAR, -1);
					startTime = new Date(cal.getTimeInMillis());
					endTime = new Date(currentTime);
				} else {
					result.setSuccess(false);
					result.setMsg("搜索失败！内部错误!");
				}
			} else {
				if ((strStartTime != null && !strStartTime.equals(""))) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							HorizonConfig.DATA_FORMAT_STRING);
					startTime = sdf.parse(strStartTime);
				}

				if ((strEndTime != null && !strEndTime.equals(""))) {
					SimpleDateFormat sdf = new SimpleDateFormat(
							HorizonConfig.DATA_FORMAT_STRING);
					endTime = sdf.parse(strEndTime);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Map<String, Object> mapNodeConnections = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		List<NodeConnection> listNodeConnections = nodeConnectionService
				.listAllConnections(nodeID, status, -1, startTime, endTime,
						userID, limit, offset);
		for (NodeConnection nodeConnection : listNodeConnections) {
			Node node = nodeService.selectByPrimaryKey(nodeConnection
					.getNodeID());
			if (node != null) {
				nodeConnection.setNdName(node.getmCode());
			} else {
				nodeConnection.setNdName("未知路由");
			}

			CommonUser commonUser = commonUserService
					.selectByMap(nodeConnection.getUserID());
			if (commonUser != null) {
				nodeConnection.setUserName(commonUser.getUserName());
			} else {
				nodeConnection
						.setUserName(HorizonConfig.NODECONNECTION_DEFAULT_LOGIN_USER);
			}
			User businessUser = userService.selectByMap(nodeConnection
					.getBusinessID());
			if (businessUser != null) {
				nodeConnection.setBusinessName(businessUser.getUserName());
			}

			if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.DIRECTORY
					.getIndex()) {
				nodeConnection
						.setConnectTypeName(HorizonConfig.NODE_LOGINTYPE.DIRECTORY
								.getName());
			} else if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.WEB
					.getIndex()) {
				nodeConnection
						.setConnectTypeName(HorizonConfig.NODE_LOGINTYPE.WEB
								.getName());
			} else if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE
					.getIndex()) {
				nodeConnection
						.setConnectTypeName(HorizonConfig.NODE_LOGINTYPE.CELLPHONE
								.getName());
			}
		}
		mapNodeConnections.put("total", nodeConnectionService.getTotalCount(
				nodeID, status, userID, startTime, endTime, userID));
		mapNodeConnections.put("rows", listNodeConnections);
		return mapNodeConnections;
	}

	@ResponseBody
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public Result addUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");
		String userStatus = request.getParameter("userStatus");
		/* String country = request.getParameter("country"); */
		String country = "中国";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String streeName = request.getParameter("streeName");
		String companyName = request.getParameter("companyName");
		String description = request.getParameter("description");
		String vipLevel = request.getParameter("vipLevel");
		String cellPhoneNum = request.getParameter("cellPhoneNum");
		String qqNum = request.getParameter("qqNum");
		String strAcount = request.getParameter("acount");
		double acount = HorizonConfig.USER_DEFAULT_ACOUNT;

		Result result = new Result();
		if (userName != null && !"".equals(userName) && password != null
				&& !"".equals(password) && userType != null
				&& !"".equals(userType)) {
			if (userService.selectByMap(userName) == null) {
				if (strAcount != null && !"".equals(strAcount)) {
					acount = Double.parseDouble(strAcount);
				}
				User user = new User(userName, password, email,
						Byte.parseByte(userStatus), Byte.parseByte(userType),
						country, province, city, streeName, companyName,
						description, null, Byte.parseByte(vipLevel),
						cellPhoneNum, qqNum, acount);
				userService.insert(user);

				if (user.getUserType() == User.USER_TYPE.BUSINESSUSER.ordinal()) {
					SmsContent smsContent = new SmsContent(user.getId(),
							HorizonConfig.SMSRECORD_DEFAULT_SMS_CONTENT,
							(byte) HorizonConfig.SMSCONTENT_STATUS.CHECK
									.getIndex());
					smsContentService.insert(smsContent);
				}
				result.setSuccess(true);
				result.setObj(user);
			} else {
				result.setSuccess(false);
				result.setMsg("增加用户失败！用户名或者密码为空！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("增加用户失败！用户名或者密码为空！");
			// throw new
			// ServletException("Add user errer! Maybe user name or password is null!");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Result register(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		byte userType = (byte) User.USER_TYPE.BUSINESSUSER.ordinal();
		byte userStatus = (byte) User.USER_STATUS.NORMAL.ordinal();
		/* String country = request.getParameter("country"); */
		String country = "中国";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String streeName = request.getParameter("streeName");
		String companyName = request.getParameter("companyName");
		String description = request.getParameter("description");
		byte vipLevel = (byte) User.VIP_LEVEL.VIP1.ordinal();
		String cellPhoneNum = request.getParameter("cellPhoneNum");
		String qqNum = request.getParameter("qqNum");
		double acount = HorizonConfig.USER_DEFAULT_ACOUNT;

		Result result = new Result();
		if (userName != null && !"".equals(userName) && password != null
				&& !"".equals(password)) {
			if (email != null && !"".equals(email)) {
				if (province != null && !"".equals(province) && city != null
						&& !"".equals(city) && streeName != null
						&& !"".equals(streeName)) {
					if (cellPhoneNum != null && !"".equals(cellPhoneNum)
							&& qqNum != null && !"".equals(qqNum)) {
						if (userService.selectByMap(userName) == null) {
							User user = new User(userName, password, email,
									userStatus, userType, country, province,
									city, streeName, companyName, description,
									null, vipLevel, cellPhoneNum, qqNum, acount);
							userService.insert(user);
							SmsContent smsContent = new SmsContent(
									user.getId(),
									HorizonConfig.SMSRECORD_DEFAULT_SMS_CONTENT,
									(byte) HorizonConfig.SMSCONTENT_STATUS.CHECK
											.getIndex());
							smsContentService.insert(smsContent);
							result.setSuccess(true);
							/* result.setObj(user); */
						} else {
							result.setSuccess(false);
							result.setMsg("注册失败！用户名已经存在，不能注册！");
						}

					} else {
						result.setSuccess(false);
						result.setMsg("注册失败！手机号或者QQ号不能为空！");
					}

				} else {
					result.setSuccess(false);
					result.setMsg("注册失败！地址不能为空！");
				}

			} else {
				result.setSuccess(false);
				result.setMsg("注册失败！电子邮箱不能为空！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("注册失败！用户名或者密码为空！");
			// throw new
			// ServletException("Add user errer! Maybe user name or password is null!");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public Result editUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String id = request.getParameter("id");
		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		String userType = request.getParameter("userType");
		String userStatus = request.getParameter("userStatus");
		/* String country = request.getParameter("country"); */
		String country = "中国";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String streeName = request.getParameter("streeName");
		String companyName = request.getParameter("companyName");
		String description = request.getParameter("description");
		String vipLevel = request.getParameter("vipLevel");

		Result result = new Result();
		if (id != null && !"".equals(id)) {
			User user = userService.selectByMap(Long.parseLong(id));
			if (user != null) {
				User editUser = new User();
				editUser.setId(user.getId());
				if (user.getUserName().equalsIgnoreCase(userName)) {
					if (!user.getEmail().equalsIgnoreCase(email)) {
						editUser.setEmail(email);
					}

					if (user.getUserType() != Byte.parseByte(userType)) {
						editUser.setUserType(Byte.parseByte(userType));
					}

					if (user.getUserStatus() != Byte.parseByte(userStatus)) {
						editUser.setUserStatus(Byte.parseByte(userStatus));
					}

					if (!user.getCountry().equalsIgnoreCase(country)) {
						editUser.setCountry(country);
					}

					if (!user.getProvince().equalsIgnoreCase(province)) {
						editUser.setProvince(province);
					}

					if (!user.getCity().equalsIgnoreCase(city)) {
						editUser.setProvince(city);
					}

					if (!user.getStreeName().equalsIgnoreCase(streeName)) {
						editUser.setStreeName(streeName);
					}

					if (!user.getCompanyName().equalsIgnoreCase(companyName)) {
						editUser.setCompanyName(companyName);
					}

					if (!user.getDescription().equalsIgnoreCase(description)) {
						editUser.setDescription(description);
					}

					if (user.getVipLevel() != Byte.parseByte(vipLevel)) {
						editUser.setVipLevel((Byte.parseByte(vipLevel)));
					}

					if (editUser.getPassword() == null
							&& editUser.getEmail() == null
							&& editUser.getUserType() == -1
							&& editUser.getUserStatus() == -1
							&& editUser.getCountry() == null
							&& editUser.getProvince() == null
							&& editUser.getCity() == null
							&& editUser.getStreeName() == null
							&& editUser.getCompanyName() == null
							&& editUser.getDescription() == null) {
						result.setSuccess(true);
					} else {
						editUser.setUpdatedTime(Calendar.getInstance(
								TimeZone.getDefault()).getTime());
						userService.updateByPrimaryKeySelective(editUser);
						result.setSuccess(true);
					}

				} else {
					result.setSuccess(false);
					result.setMsg("增加用户失败！用户名不能修改");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("增加用户失败！没有此用户");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("增加用户失败！");
			// throw new
			// ServletException("Add user errer! Maybe user name or password is null!");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/validUserName", method = RequestMethod.POST)
	public Result validUserName(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {

		String userName = request.getParameter("userName");

		Result result = new Result();
		if (userName != null && !"".equals(userName)) {
			User user = userService.selectByMap(userName);
			if (user == null) {
				result.setSuccess(true);
				result.setMsg("");
			} else {
				result.setSuccess(false);
				result.setMsg("用户名已经存在！");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("请输入用户名！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/modifyPassword", method = RequestMethod.POST)
	public Result modifyPassword(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String id = request.getParameter("id");
		String userName = request.getParameter("userName");
		String mpassword = request.getParameter("mpassword");
		String rpassword = request.getParameter("rpassword");

		Result result = new Result();
		try {
			if (id != null && !"".equals(id)) {
				User user = userService.selectByMap((Long.parseLong(id)));
				if (user != null) {
					User editUser = new User();
					editUser.setId(user.getId());
					if (user.getUserName().equalsIgnoreCase(userName)) {
						if (mpassword != null
								&& !mpassword.equalsIgnoreCase("")) {
							if (rpassword != null
									&& rpassword.equals(mpassword)) {
								if (!user.getPassword().equals(mpassword)) {
									editUser.setPassword(mpassword);
									editUser.setUpdatedTime(Calendar
											.getInstance(TimeZone.getDefault())
											.getTime());
									userService
											.updateByPrimaryKeySelective(editUser);
									result.setSuccess(true);
								} else {
									result.setSuccess(false);
									result.setMsg("修改密码失败！新密码和原始密码相同！");
								}
							} else {
								result.setSuccess(false);
								result.setMsg("修改密码失败！两次输入密码不一致！");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("修改密码失败！密码不能为空");
						}
					} else {
						result.setSuccess(false);
						result.setMsg("修改密码失败！用户名不能修改");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("修改密码失败！没有此用户");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("修改密码户失败！内部错误");
				// throw new
				// ServletException("Add user errer! Maybe user name or password is null!");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("修改密码失败！内部错误");
			e.printStackTrace();
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/listUser", method = RequestMethod.POST)
	public Map<String, Object> listUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strPage = request.getParameter("page");
		String strRows = request.getParameter("rows");
		String userType = request.getParameter("userType");

		Map<String, Object> listUser = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;
		if (userType != null && !userType.equals("")) {
			listUser.put("total",
					userService.getTotalCount(Byte.parseByte(userType)));
			listUser.put("rows", userService.listAllUserByUserType(
					Byte.parseByte(userType), limit, offset));
		} else {
			listUser.put("total", userService.getTotalCount((byte) -1));
			listUser.put("rows", userService.listAllUser(limit, offset));
		}
		return listUser;
	}

	@ResponseBody
	@RequestMapping(value = "/listUserInfo", method = RequestMethod.POST)
	public List<UserInfo> listUserInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String userType = request.getParameter("userType");

		List<UserInfo> listUser = new ArrayList<UserInfo>();
		if (userType != null && !userType.equals("")) {
			List<User> users = userService.listAllUserByUserType(Byte
					.parseByte(userType));
			for (User use : users) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(use.getId());
				userInfo.setCompanyName(use.getCompanyName());
				listUser.add(userInfo);
			}
		}
		return listUser;
	}

	@ResponseBody
	@RequestMapping(value = "/addNode", method = RequestMethod.POST)
	public Result addNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String ndName = "Horizon" + System.currentTimeMillis();
		String aliasName = request.getParameter("aliasName");
		String mCode = request.getParameter("mCode");
		String industry = request.getParameter("industry");
		String turnOffTime = request.getParameter("turnOffTime");
		String turnOffFreeTime = request.getParameter("turnOffFreeTime");
		String multiTerminalLogin = request.getParameter("multiTerminalLogin");
		String limitSpeed = request.getParameter("limitSpeed");
		String totalLimitIncoming = request.getParameter("totalLimitIncoming");
		String totalLimitOutgoing = request.getParameter("totalLimitOutgoing");
		String eachLimitIncoming = request.getParameter("eachLimitIncoming");
		String eachLimitOutgoing = request.getParameter("eachLimitOutgoing");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String portalUrl = request.getParameter("portalUrl");
		// String updateTime = request.getParameter("updateTime");
		String loginType = request.getParameter("loginType");
		String nodeStatus = request.getParameter("nodeStatus");
		String venderID = request.getParameter("venderUser");
		String businessID = request.getParameter("businessUser");
		String strRemainSms = request.getParameter("remainSms");
		String strSmsCodeValidTime = request.getParameter("smsCodeValidTime");
		String strSmsType = request.getParameter("smsType");
		String strSmsCodeLength = request.getParameter("smsCodeLength");
		String strSmsCodeDayNum = request.getParameter("smsCodeDayNum");
		String strSmsCodeObtainInterval = request
				.getParameter("smsCodeObtainInterval");

		byte byteIndustry = -1;
		long longTurnOffTime = HorizonConfig.NODE_DEFAULT_TURNOFFFREETIME;
		long longTurnOffFreeTime = HorizonConfig.NODE_DEFAULT_TURNOFFTIME;
		byte byteMultiTerminalLogin = (byte) Node.MULTITERMINALLOGIN.NO
				.ordinal();
		byte byteLimitSpeed = (byte) Node.LIMITSPEED.NO.ordinal();
		long longTotalLimitIncoming = HorizonConfig.NODE_DEFAULT_TOTALLIMITINCOMING;
		long longTotalLimitOutgoing = HorizonConfig.NODE_DEFAULT_TOTALLIMITOUTGOING;
		long longEachLimitIncoming = HorizonConfig.NODE_DEFAULT_EACHLIMITINCOMING;
		long longEachLimitOutgoing = HorizonConfig.NODE_DEFAULT_EACHLIMITOUTGOING;
		Time timeStartTime = Time.valueOf("00:00:00");
		Time timeEndTime = Time.valueOf("23:59:59");
		byte byteLoginType = (byte) HorizonConfig.NODE_LOGINTYPE.DIRECTORY
				.ordinal();
		byte byteNodeStatus = (byte) Node.NODESTATUS.NORMAL.ordinal();
		byte running = (byte) HorizonConfig.NODE_RUNNING.STOP.getIndex();
		long longVenderID = -1;
		long longBusinessID = -1;
		long remainSms = HorizonConfig.NODE_DEFAULT_REMAINSMS;
		long smsCodeValidTime = HorizonConfig.NODE_DEFAULT_SMSCODEVALIDTIME;
		byte smsType = (byte) HorizonConfig.NODE_SMSTYPE.ONCE.getIndex();
		int smsCodeLength = HorizonConfig.NODE_DEFAULT_SMSCODELENGTH;
		int smsCodeDayNum = HorizonConfig.NODE_DEFAULT_SMSCODEDAYNUM;
		long smsCodeObtainInterval = HorizonConfig.NODE_DEFAULT_SMSCODEOBTAININTERVAL;

		Result result = new Result();
		if (ndName != null && !"".equals(ndName) && mCode != null
				&& !"".equals(mCode) && venderID != null
				&& !"".equals(venderID)) {
			if (industry != null && !"".equals(industry)) {
				byteIndustry = Byte.parseByte(industry);
			}

			if (turnOffTime != null && !"".equals(turnOffTime)) {
				longTurnOffTime = Long.parseLong(turnOffTime);
			}

			if (turnOffFreeTime != null && !"".equals(turnOffFreeTime)) {
				longTurnOffFreeTime = Long.parseLong(turnOffFreeTime);
			}

			if (multiTerminalLogin != null && !"".equals(multiTerminalLogin)) {
				byteMultiTerminalLogin = Byte.parseByte(multiTerminalLogin);
			}

			if (limitSpeed != null && !"".equals(limitSpeed)) {
				byteLimitSpeed = Byte.parseByte(limitSpeed);
			}

			if (totalLimitIncoming != null && !"".equals(totalLimitIncoming)) {
				longTotalLimitIncoming = Long.parseLong(totalLimitIncoming);
			}

			if (totalLimitOutgoing != null && !"".equals(totalLimitOutgoing)) {
				longTotalLimitOutgoing = Long.parseLong(totalLimitOutgoing);
			}

			if (eachLimitIncoming != null && !"".equals(eachLimitIncoming)) {
				longEachLimitIncoming = Long.parseLong(eachLimitIncoming);
			}

			if (eachLimitOutgoing != null && !"".equals(eachLimitOutgoing)) {
				longEachLimitOutgoing = Long.parseLong(eachLimitOutgoing);
			}

			if (startTime != null && !"".equals(startTime)) {
				timeStartTime = Time.valueOf(startTime);
			}

			if (endTime != null && !"00:00:00".equals(endTime)) {
				timeEndTime = Time.valueOf(endTime);
			}

			if (loginType != null && !"".equals(loginType)) {
				byteLoginType = Byte.parseByte(loginType);
			}

			if (nodeStatus != null && !"".equals(nodeStatus)) {
				byteNodeStatus = Byte.parseByte(nodeStatus);
			}

			if (venderID != null && !"".equals(venderID)) {
				longVenderID = Byte.parseByte(venderID);
			}

			if (businessID != null && !"".equals(businessID)) {
				longBusinessID = Byte.parseByte(businessID);
			}

			if (aliasName != null && "".equals(aliasName)) {
				aliasName = null;
			}

			if (portalUrl != null && "".equals(portalUrl)) {
				portalUrl = null;
			}

			/*
			 * if (strRemainSms != null && !"".equals(strRemainSms)) { remainSms
			 * = Long.parseLong(strRemainSms); }
			 */
			if (strSmsCodeValidTime != null && !"".equals(strSmsCodeValidTime)) {
				smsCodeValidTime = Long.parseLong(strSmsCodeValidTime);
			}
			if (strSmsType != null && !"".equals(strSmsType)) {
				smsType = Byte.parseByte(strSmsType);
			}
			if (strSmsCodeLength != null && !"".equals(strSmsCodeLength)) {
				smsCodeLength = Integer.parseInt(strSmsCodeLength);
			}
			if (strSmsCodeDayNum != null && !"".equals(strSmsCodeDayNum)) {
				smsCodeDayNum = Integer.parseInt(strSmsCodeDayNum);
			}
			if (strSmsCodeObtainInterval != null
					&& !"".equals(strSmsCodeObtainInterval)) {
				smsCodeObtainInterval = Long
						.parseLong(strSmsCodeObtainInterval);
			}

			if (timeStartTime.compareTo(timeEndTime) >= 0) {
				result.setSuccess(false);
				result.setMsg("增加路由失败！开放开始时间不能大于开放小于时间！");
			} else {
				Node node = new Node(ndName, aliasName, mCode, byteIndustry,
						longTurnOffTime, longTurnOffFreeTime,
						byteMultiTerminalLogin, byteLimitSpeed,
						longTotalLimitIncoming, longTotalLimitOutgoing,
						longEachLimitIncoming, longEachLimitOutgoing,
						timeStartTime, timeEndTime, portalUrl, byteLoginType,
						byteNodeStatus, longVenderID, longBusinessID,running, remainSms,
						smsCodeValidTime, (byte)-1, smsType, smsCodeLength,
						smsCodeDayNum, smsCodeObtainInterval, -1);
				nodeService.insert(node);
				result.setSuccess(true);
			}
		} else {
			result.setSuccess(false);
			result.setMsg("增加路由失败！必要字段可能为空！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/addNodeSimple", method = RequestMethod.POST)
	public Result addNodeSimple(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}
		String mcode = request.getParameter("mcode");
		String venderID = request.getParameter("venderUser");
		String strVipID = request.getParameter("vipID");
		String strValidTime = request.getParameter("validTime");
		
		Result result = new Result();
		if (mcode != null && !"".equals(mcode) && venderID != null
				&& !"".equals(venderID)) {
			/*
			 * Node node = new Node(); Date createTime =
			 * Calendar.getInstance(TimeZone.getDefault()) .getTime();
			 * node.setNdName(ndName);
			 * node.setVenderID(Long.parseLong(venderID));
			 * node.setCreateTime(createTime); node.setUpdateTime(createTime);
			 */
			long longTurnOffTime = HorizonConfig.NODE_DEFAULT_TURNOFFFREETIME;
			long longTurnOffFreeTime = HorizonConfig.NODE_DEFAULT_TURNOFFTIME;
			byte byteMultiTerminalLogin = (byte) Node.MULTITERMINALLOGIN.NO
					.ordinal();
			byte byteLimitSpeed = (byte) Node.LIMITSPEED.NO.ordinal();
			long longTotalLimitIncoming = HorizonConfig.NODE_DEFAULT_TOTALLIMITINCOMING;
			long longTotalLimitOutgoing = HorizonConfig.NODE_DEFAULT_TOTALLIMITOUTGOING;
			long longEachLimitIncoming = HorizonConfig.NODE_DEFAULT_EACHLIMITINCOMING;
			long longEachLimitOutgoing = HorizonConfig.NODE_DEFAULT_EACHLIMITOUTGOING;
			Time timeStartTime = Time.valueOf("00:00:00");
			Time timeEndTime = Time.valueOf("23:59:59");
			byte byteLoginType = (byte) HorizonConfig.NODE_LOGINTYPE.DIRECTORY
					.ordinal();
			byte byteNodeStatus = (byte) Node.NODESTATUS.NORMAL.ordinal();
			byte running = (byte) HorizonConfig.NODE_RUNNING.STOP.getIndex();
			long remainSms = HorizonConfig.NODE_DEFAULT_REMAINSMS;
			long smsCodeValidTime = HorizonConfig.NODE_DEFAULT_SMSCODEVALIDTIME;
			byte smsType = (byte) HorizonConfig.NODE_SMSTYPE.ONCE.getIndex();
			int smsCodeLength = HorizonConfig.NODE_DEFAULT_SMSCODELENGTH;
			int smsCodeDayNum = HorizonConfig.NODE_DEFAULT_SMSCODEDAYNUM;
			long smsCodeObtainInterval = HorizonConfig.NODE_DEFAULT_SMSCODEOBTAININTERVAL;
			long vipID = -1;
			Lever lever = null;
			
			if (strVipID != null && !"".equals(strVipID)) {
				vipID = Long.parseLong(strVipID);
				lever = leverService.selectByMap(vipID);
			} else {
				lever = leverService.selectByMap("vip1");
				vipID = lever.getId();
			}
			
			int validTime = -1;
			PropertiesTool propertiesTool = new PropertiesTool();
			propertiesTool.loadFile("horizon.properties", "UTF-8");
			if (strValidTime != null && !"".equals(strValidTime)) {
				if (lever.getVip().equalsIgnoreCase("vip1")) {
					/*SimpleDateFormat sdf = new SimpleDateFormat(
							HorizonConfig.DATA_FORMAT);
					try {
						endTime = sdf.parse("2030-12-31");
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					validTime = 1000;
				} else {
					validTime = Integer.parseInt(strValidTime);
					int foreverTime = propertiesTool.getInteger("horizon.node.lever.forever.valid");
					if (foreverTime <= validTime) {
						/*SimpleDateFormat sdf = new SimpleDateFormat(
								HorizonConfig.DATA_FORMAT);
						try {
							endTime = sdf.parse("2030-12-31");
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}*/
						validTime = 1000;
					} /*else {
						SimpleDateFormat sdf = new SimpleDateFormat(
								HorizonConfig.DATA_FORMAT);
						Date currentTime = new Date();
						Calendar calendar=Calendar.getInstance();   
					    try {
							calendar.setTime(sdf.parse(currentTime.getYear() + "-" + currentTime.getMonth() + "-" + currentTime.getDay()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					    calendar.add(Calendar.MONTH, validTime);
					    endTime = calendar.getTime();
						
					}*/
				}
			} else {
				/*SimpleDateFormat sdf = new SimpleDateFormat(
						HorizonConfig.DATA_FORMAT);
				try {
					endTime = sdf.parse("2030-12-31");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				validTime = 1000;
			}

			Node node = new Node("Horizon" + System.currentTimeMillis(), null,
					mcode, Byte.parseByte("-1"), longTurnOffTime,
					longTurnOffFreeTime, byteMultiTerminalLogin,
					byteLimitSpeed, longTotalLimitIncoming,
					longTotalLimitOutgoing, longEachLimitIncoming,
					longEachLimitOutgoing, timeStartTime, timeEndTime, null,
					byteLoginType, byteNodeStatus, Long.parseLong(venderID),
					-1, running, remainSms,
					smsCodeValidTime, (byte)-1, smsType, smsCodeLength, smsCodeDayNum,
					smsCodeObtainInterval, -1);
			nodeService.insert(node);
			
			NodeLever nodeLever = new NodeLever(node.getId(), vipID, null, validTime);
			nodeLeverService.insert(nodeLever);
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMsg("增加路由失败！必要属性可能为空！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/editNode", method = RequestMethod.POST)
	public Result editNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String id = request.getParameter("id");
		String ndName = request.getParameter("ndName");
		String aliasName = request.getParameter("aliasName");
		String mCode = request.getParameter("mCode");
		String industry = request.getParameter("industry");
		String turnOffTime = request.getParameter("turnOffTime");
		String turnOffFreeTime = request.getParameter("turnOffFreeTime");
		String multiTerminalLogin = request.getParameter("multiTerminalLogin");
		String limitSpeed = request.getParameter("limitSpeed");
		String totalLimitIncoming = request.getParameter("totalLimitIncoming");
		String totalLimitOutgoing = request.getParameter("totalLimitOutgoing");
		String eachLimitIncoming = request.getParameter("eachLimitIncoming");
		String eachLimitOutgoing = request.getParameter("eachLimitOutgoing");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String portalUrl = request.getParameter("portalUrl");
		String loginType = request.getParameter("loginType");
		String nodeStatus = request.getParameter("nodeStatus");
		String venderID = request.getParameter("venderUser");
		String businessID = request.getParameter("businessUser");
		/*
		 * String strRemainSms = request .getParameter("remainSms");
		 */
		String strSmsCodeValidTime = request.getParameter("smsCodeValidTime");
		String strSmsType = request.getParameter("smsType");
		String strSmsCodeLength = request.getParameter("smsCodeLength");
		String strSmsCodeDayNum = request.getParameter("smsCodeDayNum");
		String strSmsCodeObtainInterval = request
				.getParameter("smsCodeObtainInterval");

		Result result = new Result();
		if (id != null && !"".equals(id) && ndName != null
				&& !"".equals(ndName) && mCode != null && !"".equals(mCode)
				&& venderID != null && !"".equals(venderID)) {
			Node node = nodeService.selectByPrimaryKey(Long.parseLong(id));
			if (node != null && node.getNdName().equals(ndName)) {
				if (aliasName != null && "".equals(aliasName)) {
					aliasName = null;
				}

				if (portalUrl != null && "".equals(portalUrl)) {
					portalUrl = null;
				}

				node.setmCode(mCode);
				node.setAliasName(aliasName);
				node.setPortalUrl(portalUrl);

				if (industry != null && !"".equals(industry)) {
					node.setIndustry(Byte.parseByte(industry));
				}

				if (turnOffTime != null && !"".equals(turnOffTime)) {
					node.setTurnOffTime(Long.parseLong(turnOffTime));
				}

				if (turnOffFreeTime != null && !"".equals(turnOffFreeTime)) {
					node.setTurnOffFreeTime(Long.parseLong(turnOffFreeTime));
				}

				if (multiTerminalLogin != null
						&& !"".equals(multiTerminalLogin)) {
					node.setMultiTerminalLogin(Byte
							.parseByte(multiTerminalLogin));
				}

				if (limitSpeed != null && !"".equals(limitSpeed)) {
					node.setLimitSpeed(Byte.parseByte(limitSpeed));
				}

				if (totalLimitIncoming != null
						&& !"".equals(totalLimitIncoming)) {
					node.setTotalLimitIncoming(Long
							.parseLong(totalLimitIncoming));
				}

				if (totalLimitOutgoing != null
						&& !"".equals(totalLimitOutgoing)) {
					node.setTotalLimitOutgoing(Long
							.parseLong(totalLimitOutgoing));
				}

				if (eachLimitIncoming != null && !"".equals(eachLimitIncoming)) {
					node.setEachLimitIncoming(Long.parseLong(eachLimitIncoming));
				}

				if (eachLimitOutgoing != null && !"".equals(eachLimitOutgoing)) {
					node.setEachLimitOutgoing(Long.parseLong(eachLimitOutgoing));
				}

				if (startTime != null && !"".equals(startTime)) {
					node.setStartTime(Time.valueOf(startTime));
				}

				if (endTime != null && !"00:00:00".equals(endTime)) {
					node.setEndTime(Time.valueOf(endTime));
				}

				if (loginType != null && !"".equals(loginType)) {
					node.setLoginType(Byte.parseByte(loginType));
				}

				if (nodeStatus != null && !"".equals(nodeStatus)) {
					node.setNodeStatus(Byte.parseByte(nodeStatus));
				}

				if (venderID != null && !"".equals(venderID)) {
					node.setVenderID(Byte.parseByte(venderID));
				}

				if (businessID != null && !"".equals(businessID)) {
					node.setBusinessID(Byte.parseByte(businessID));
				}

				/*
				 * if (strRemainSms != null && !"".equals(strRemainSms)) {
				 * node.setRemainSms(Long.parseLong(strRemainSms)); }
				 */
				if (strSmsCodeValidTime != null
						&& !"".equals(strSmsCodeValidTime)) {
					node.setSmsCodeValidTime(Long
							.parseLong(strSmsCodeValidTime));
				}
				if (strSmsType != null && !"".equals(strSmsType)) {
					node.setSmsType(Byte.parseByte(strSmsType));
				}
				if (strSmsCodeLength != null && !"".equals(strSmsCodeLength)) {
					node.setSmsCodeLength(Integer.parseInt(strSmsCodeLength));
				}
				if (strSmsCodeDayNum != null && !"".equals(strSmsCodeDayNum)) {
					node.setSmsCodeDayNum(Integer.parseInt(strSmsCodeDayNum));
				}
				if (strSmsCodeObtainInterval != null
						&& !"".equals(strSmsCodeObtainInterval)) {
					node.setSmsCodeObtainInterval(Long
							.parseLong(strSmsCodeObtainInterval));
				}

				if (node.getStartTime().compareTo(node.getEndTime()) >= 0) {
					result.setSuccess(false);
					result.setMsg("增加路由失败！开放开始时间不能大于开放小于时间！");
				} else {
					node.setUpdateTime(Calendar.getInstance(
							TimeZone.getDefault()).getTime());
					nodeService.updateByPrimaryKeySelective(node);
					result.setSuccess(true);
				}
			}

		} else {
			result.setSuccess(false);
			result.setMsg("增加路由失败！必要字段可能为空！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public Result deleteUser(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String id = request.getParameter("id");

		Result result = new Result();
		if (id != null && !"".equals(id)) {
			userService.deleteByPrimaryKey(Long.parseLong(id));
			result.setSuccess(true);
		} else {
			result.setSuccess(false);
			result.setMsg("删除用户失败！");
			// throw new
			// ServletException("Add user errer! Maybe user name or password is null!");
		}
		return result;
	}
}
