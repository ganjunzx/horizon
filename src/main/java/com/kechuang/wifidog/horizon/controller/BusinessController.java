package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

import com.kechuang.wifidog.horizon.model.BusinessNode;
import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.model.Result;
import com.kechuang.wifidog.horizon.model.SmsContent;
import com.kechuang.wifidog.horizon.model.SmsPackage;
import com.kechuang.wifidog.horizon.model.SmsSecurityCode;
import com.kechuang.wifidog.horizon.model.SmsTrade;
import com.kechuang.wifidog.horizon.model.Tree;
import com.kechuang.wifidog.horizon.model.User;
import com.kechuang.wifidog.horizon.model.UserTrade;
import com.kechuang.wifidog.horizon.model.VenderNode;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.MenuService;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.SmsContentService;
import com.kechuang.wifidog.horizon.service.SmsSecurityCodeService;
import com.kechuang.wifidog.horizon.service.SmsTradeService;
import com.kechuang.wifidog.horizon.service.UserService;
import com.kechuang.wifidog.horizon.service.UserTradeService;
import com.kechuang.wifidog.horizon.utils.CommonUtils;
import com.kechuang.wifidog.horizon.utils.ExportExcel;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;

@Controller
@RequestMapping("/business")
public class BusinessController {
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

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
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsContentService")
	private SmsContentService smsContentService;

	@ResponseBody
	@RequestMapping(value = "/treeNode", method = RequestMethod.POST)
	public List<BusinessNode> treeNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		List<Node> nodes = nodeService.listAllNode(-1L, loginUser.getId(),
				null, null, (byte) -1, -1L, -1L);
		List<BusinessNode> businessNodes = new ArrayList<BusinessNode>();
		/*
		 * if (nodes != null && nodes.size() != 0) {
		 */
		BusinessNode businessNodeInit = new BusinessNode();
		String type = request.getParameter("type");//如果type为1,则不加入所有热点，否则加入
		if (type != null && "1".equals(type)) {
			for (Node node : nodes) {
				BusinessNode businessNode = new BusinessNode();
				businessNode.setId(node.getId());
				String nodeName = node.getAliasName();
				if (nodeName == null || nodeName.equals("")) {
					businessNode.setNdName(node.getNdName());
				} else {
					businessNode.setNdName(node.getAliasName());
				}
				businessNode.setCreateTime(node.getCreateTime());
				businessNode.setNodeStatus(node.getNodeStatus());

				businessNodes.add(businessNode);
			}
		} else {
			businessNodeInit.setId(-2);
			businessNodeInit.setNdName("所有热点");
			businessNodes.add(businessNodeInit);
			
			for (Node node : nodes) {
				BusinessNode businessNode = new BusinessNode();
				businessNode.setId(node.getId());
				String nodeName = node.getAliasName();
				if (nodeName == null || nodeName.equals("")) {
					businessNode.setNdName(node.getNdName());
				} else {
					businessNode.setNdName(node.getAliasName());
				}
				businessNode.setCreateTime(node.getCreateTime());
				businessNode.setNodeStatus(node.getNodeStatus());

				businessNodes.add(businessNode);
			}
		}
		
		/* } */
		
		return businessNodes;
	}
	
	@ResponseBody
	@RequestMapping(value = "/smsPackage", method = RequestMethod.POST)
	public List<SmsPackage> smsPackage(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		List<SmsPackage> listSmsPackage = new ArrayList<SmsPackage>();
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED.getName()));
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_HUNDRED.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_HUNDRED.getName()));
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_THOUSAND.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_THOUSAND.getName()));
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_THOUSAND.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_THOUSAND.getName()));
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.TEN_THOUSAND.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.TEN_THOUSAND.getName()));
		listSmsPackage.add(new SmsPackage(HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED_THOUSAND.getIndex(), HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED_THOUSAND.getName()));
		return listSmsPackage;
	}


	@ResponseBody
	@RequestMapping(value = "/editSelfInfo", method = RequestMethod.POST)
	public Result editSelfInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String userName = request.getParameter("userName");
		String email = request.getParameter("email");
		/* String country = request.getParameter("country"); */
		String country = "中国";
		String province = request.getParameter("province");
		String city = request.getParameter("city");
		String streeName = request.getParameter("streeName");
		String companyName = request.getParameter("companyName");
		String description = request.getParameter("description");
		String cellPhoneNum = request.getParameter("cellPhoneNum");
		String qqNum = request.getParameter("qqNum");

		Result result = new Result();
		User editUser = new User();
		editUser.setId(loginUser.getId());
		if (loginUser.getUserName().equalsIgnoreCase(userName)) {
			if (!loginUser.getEmail().equalsIgnoreCase(email)) {
				editUser.setEmail(email);
			}

			if (!loginUser.getCountry().equalsIgnoreCase(country)) {
				editUser.setCountry(country);
			}

			if (!loginUser.getProvince().equalsIgnoreCase(province)) {
				editUser.setProvince(province);
			}

			if (!loginUser.getCity().equalsIgnoreCase(city)) {
				editUser.setProvince(city);
			}

			if (!loginUser.getStreeName().equalsIgnoreCase(streeName)) {
				editUser.setStreeName(streeName);
			}

			if (!loginUser.getCompanyName().equalsIgnoreCase(companyName)) {
				editUser.setCompanyName(companyName);
			}

			if (!loginUser.getDescription().equalsIgnoreCase(description)) {
				editUser.setDescription(description);
			}

			if (!loginUser.getCellPhoneNum().equalsIgnoreCase(cellPhoneNum)) {
				editUser.setCellPhoneNum(cellPhoneNum);
			}

			if (!loginUser.getQqNum().equalsIgnoreCase(qqNum)) {
				editUser.setQqNum(qqNum);
			}

			if (editUser.getPassword() == null && editUser.getEmail() == null
					&& editUser.getCountry() == null
					&& editUser.getProvince() == null
					&& editUser.getCity() == null
					&& editUser.getStreeName() == null
					&& editUser.getCompanyName() == null
					&& editUser.getDescription() == null
					&& editUser.getCellPhoneNum() == null
					&& editUser.getQqNum() == null) {
				result.setSuccess(true);
			} else {
				editUser.setUpdatedTime(Calendar.getInstance(
						TimeZone.getDefault()).getTime());
				userService.updateByPrimaryKeySelective(editUser);
				result.setSuccess(true);
			}

		} else {
			result.setSuccess(false);
			result.setMsg("修改用户失败！用户名不能修改");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/acountInfo", method = RequestMethod.POST)
	public Result acountInfo(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		Result result = new Result();
		User user = userService.selectByMap(loginUser.getId());

		if (user != null) {
			result.setSuccess(true);
			result.setObj(user.getAcount());
		} else {
			result.setSuccess(false);
			result.setMsg("获取账户信息失败！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/modifySelfPassword", method = RequestMethod.POST)
	public Result modifySelfPassword(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String userName = request.getParameter("userName");
		String mpassword = request.getParameter("mpassword");
		String rpassword = request.getParameter("rpassword");

		Result result = new Result();
		try {
			User user = userService.selectByMap(loginUser.getId());
			if (user != null) {
				User editUser = new User();
				editUser.setId(user.getId());
				if (user.getUserName().equalsIgnoreCase(userName)) {
					if (mpassword != null && !mpassword.equalsIgnoreCase("")) {
						if (rpassword != null && rpassword.equals(mpassword)) {
							if (!user.getPassword().equals(mpassword)) {
								editUser.setPassword(mpassword);
								editUser.setUpdatedTime(Calendar.getInstance(
										TimeZone.getDefault()).getTime());
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
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("修改密码失败！内部错误");
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
		String strTradeWay = request.getParameter("tradeWay");
		String description = request.getParameter("description");
		String strTradeType = request.getParameter("tradeType");

		Result result = new Result();
		double totalPrice = -1;
		byte tradeWay = -1;
		byte tradeType = -1;
		try {
			User user = userService.selectByMap(loginUser.getId());
			if (user != null) {
				if (strTotalPrice != null && !"".equals(strTotalPrice)) {
					totalPrice = Double.parseDouble(strTotalPrice);
				}

				if (strTradeWay != null && !"".equals(strTradeWay)) {
					tradeWay = Byte.parseByte(strTradeWay);
				}

				if (strTradeType != null && !"".equals(strTradeType)) {
					tradeType = Byte.parseByte(strTradeType);
				}

				if (totalPrice >= 100) {

					if (tradeType == HorizonConfig.USERTRADE_TRADE_TYPE.RECHARGE
							.getIndex()) {
						if (description != null && !"".equals(description)) {
							if (tradeWay == HorizonConfig.USERTRADE_TRADE_WAY.BANK
									.getIndex()) {
								UserTrade userTrade = new UserTrade(
										user.getId(),
										totalPrice,
										(byte) HorizonConfig.USERTRADE_TRADE_STATUS.CHECKING
												.getIndex(), tradeType,
										description, tradeWay);
								userTradeService.insert(userTrade);
								result.setSuccess(true);
							} else if (tradeWay == HorizonConfig.USERTRADE_TRADE_WAY.ALIPAY
									.getIndex()) {

							} else {
								result.setSuccess(false);
								result.setMsg("充值失败！支付方式错误！");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("充值失败！交易描述不能为空！");
						}
					} else {
						result.setSuccess(false);
						result.setMsg("充值失败！交易类型错误！");
					}

				} else {
					result.setSuccess(false);
					result.setMsg("充值失败！充值金额必须大于等于100元！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("充值失败！用户登录过期或者没有此用户！");
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
	@RequestMapping(value = "/smsTrade", method = RequestMethod.POST)
	public Result smsTrade(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String nodeID = request.getParameter("ndName");
		String strSmsPackage = request.getParameter("smsPackage");

		Result result = new Result();
		
		byte smsPackage = -1;
		double totalPrice = -1;
		long applySms = -1;
		try {
			User user = userService.selectByMap(loginUser.getId());
			if (user != null) {
				if (nodeID != null && !"".equals(nodeID)) {
					Node node = nodeService.selectByPrimaryKey(Long.parseLong(nodeID));
					if (node != null) {
						if (strSmsPackage != null && !"".equals(strSmsPackage)) {
							smsPackage = Byte.parseByte(strSmsPackage);
							if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED.getApplySms();
							} else if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_HUNDRED.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_HUNDRED.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_HUNDRED.getApplySms();
							} else if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_THOUSAND.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_THOUSAND.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_THOUSAND.getApplySms();
							} else if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_THOUSAND.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_THOUSAND.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.FIVE_THOUSAND.getApplySms();
							} else if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.TEN_THOUSAND.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.TEN_THOUSAND.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.TEN_THOUSAND.getApplySms();
							} else if (smsPackage == HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED_THOUSAND.getIndex()) {
								totalPrice = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED_THOUSAND.getTotalPrice();
								applySms = HorizonConfig.SMSTRADE_SMS_PACKAGE.ONE_HUNDRED_THOUSAND.getApplySms();
							} else {
								result.setSuccess(false);
								result.setMsg("授权失败！没有此短信套餐！");
								return result;
							}
							
							if (totalPrice < user.getAcount()) {
								SmsTrade smsTrade = new SmsTrade(user.getId(), node.getId(), applySms, totalPrice, (byte)HorizonConfig.SMSTRADE_TRADE_STATUS.SUCCESS.getIndex());
								smsTradeService.insert(smsTrade);
								
								Node editNode = new Node();
								editNode.setId(node.getId());
								editNode.setRemainSms(node.getRemainSms() + applySms);
								nodeService.updateByPrimaryKeySelective(editNode);
								
								User editUser = new User();
								editUser.setId(user.getId());
								editUser.setAcount(user.getAcount() - totalPrice);
								userService.updateByPrimaryKeySelective(editUser);
								result.setSuccess(true);
							} else {
								result.setSuccess(false);
								result.setMsg("授权失败！余额不足！");
							}
						} else {
							result.setSuccess(false);
							result.setMsg("授权失败！短信套餐未选择！");
						}
						
					} else {
						result.setSuccess(false);
						result.setMsg("授权失败！热点不存在！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("授权失败！热点没有选择！");
				}

			} else {
				result.setSuccess(false);
				result.setMsg("授权失败！用户登录过期或者没有此用户！");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("授权失败！内部错误");
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
		String strTradeWay = request.getParameter("tradeWay");
		String description = request.getParameter("description");
		String strTradeType = request.getParameter("tradeType");

		Result result = new Result();
		double totalPrice = -1;
		byte tradeWay = -1;
		byte tradeType = -1;
		try {
			User user = userService.selectByMap(loginUser.getId());
			if (user != null) {
				if (strTotalPrice != null && !"".equals(strTotalPrice)) {
					totalPrice = Double.parseDouble(strTotalPrice);
				}

				if (strTradeWay != null && !"".equals(strTradeWay)) {
					tradeWay = Byte.parseByte(strTradeWay);
				}

				if (strTradeType != null && !"".equals(strTradeType)) {
					tradeType = Byte.parseByte(strTradeType);
				}

				if (totalPrice >= 100) {
					if (totalPrice <= user.getAcount()) {
						if (tradeType == HorizonConfig.USERTRADE_TRADE_TYPE.ENCHASHMENT
								.getIndex()) {
							if (description != null && !"".equals(description)) {
								if (tradeWay == HorizonConfig.USERTRADE_TRADE_WAY.BANK
										.getIndex()) {
									UserTrade userTrade = new UserTrade(
											user.getId(),
											totalPrice,
											(byte) HorizonConfig.USERTRADE_TRADE_STATUS.CHECKING
													.getIndex(), tradeType,
											description, tradeWay);
									userTradeService.insert(userTrade);
									result.setSuccess(true);
								} else if (tradeWay == HorizonConfig.USERTRADE_TRADE_WAY.ALIPAY
										.getIndex()) {

								} else {
									result.setSuccess(false);
									result.setMsg("取现失败！支付方式错误！");
								}
							} else {
								result.setSuccess(false);
								result.setMsg("取现失败！交易描述不能为空！");
							}
					} else {
						result.setSuccess(false);
						result.setMsg("取现失败！交易类型错误！");
					}
					
					} else {
						result.setSuccess(false);
						result.setMsg("取现失败！账户余额不足！");
					}

				} else {
					result.setSuccess(false);
					result.setMsg("取现失败！取现金额必须大于等于100元！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("取现失败！用户登录过期或者没有此用户！");
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
			CommonUser commonUser = commonUserService.selectByMap(userName,
					loginUser.getId());
			if (commonUser == null) {
				result.setSuccess(false);
				result.setMsg("搜索失败！用户\"" + userName + "\"不存在!");
			} else {
				userID = commonUser.getId();
			}
		}

		if (strNodeID != null && !"".equals(strNodeID)) {
			if (!strNodeID.equals("-2")) {
				Node node = nodeService.selectByPrimaryKey(Long
						.parseLong(strNodeID));
				if (node != null && node.getBusinessID() != loginUser.getId()) {
					String nodeName = node.getAliasName();
					if (nodeName == null || nodeName.equals("")) {
						nodeName = node.getNdName();
					}
					result.setSuccess(false);
					result.setMsg("搜索失败！节点\"" + nodeName + "\"不存在!");
				} else if (node == null) {
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
				.listAllConnections(nodeID, status, userID, startTime, endTime,
						loginUser.getId(), limit, offset);
		for (NodeConnection nodeConnection : listNodeConnections) {
			Node node = nodeService.selectByPrimaryKey(nodeConnection
					.getNodeID());
			if (node != null) {
				String nodeName = node.getAliasName();
				if (nodeName == null || nodeName.equals("")) {
					nodeName = node.getNdName();
				}
				nodeConnection.setNdName(nodeName);
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
				nodeID, status, userID, startTime, endTime, loginUser.getId()));
		mapNodeConnections.put("rows", listNodeConnections);
		return mapNodeConnections;
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
		String strNodeID = request.getParameter("nodeID");
		String strLastTime = request.getParameter("lastTime");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");
		String cellPhoneNum = request.getParameter("cellPhoneNum");

		long nodeID = -1;
		byte mobileType = -1;
		Date startTime = null;
		Date endTime = null;

		Result result = new Result();

		if (strNodeID != null && !"".equals(strNodeID)) {
			if (!strNodeID.equals("-2")) {
				Node node = nodeService.selectByPrimaryKey(Long
						.parseLong(strNodeID));
				if (node != null && node.getBusinessID() != loginUser.getId()) {
					String nodeName = node.getAliasName();
					if (nodeName == null || nodeName.equals("")) {
						nodeName = node.getNdName();
					}
					result.setSuccess(false);
					result.setMsg("搜索失败！节点\"" + nodeName + "\"不存在!");
				} else if (node == null) {
					result.setSuccess(false);
					result.setMsg("搜索失败！节点不存在!");
				} else {
					nodeID = Long.parseLong(strNodeID);
				}
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
				.listAllSmsSecurityCode(loginUser.getId(), nodeID, cellPhoneNum, null, mobileType, startTime, endTime, limit, offset);
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
				loginUser.getId(), nodeID, cellPhoneNum, null, mobileType, startTime, endTime));
		mapNodeConnections.put("rows", listSmsSecurityCodes);
		return mapNodeConnections;
	}
	
	@ResponseBody
	@RequestMapping(value = "/exportDetailExcel", method = RequestMethod.GET)
	public void exportDetailExcel(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		String strMobileType = request.getParameter("mobileType");
		String strNodeID = request.getParameter("nodeID");
		String strLastTime = request.getParameter("lastTime");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");
		String cellPhoneNum = request.getParameter("cellPhoneNum");

		long nodeID = -1;
		byte mobileType = -1;
		Date startTime = null;
		Date endTime = null;

		Result result = new Result();

		if (strNodeID != null && !"".equals(strNodeID)) {
			if (!strNodeID.equals("-2")) {
				Node node = nodeService.selectByPrimaryKey(Long
						.parseLong(strNodeID));
				if (node != null && node.getBusinessID() != loginUser.getId()) {
					String nodeName = node.getAliasName();
					if (nodeName == null || nodeName.equals("")) {
						nodeName = node.getNdName();
					}
					result.setSuccess(false);
					result.setMsg("搜索失败！节点\"" + nodeName + "\"不存在!");
				} else if (node == null) {
					result.setSuccess(false);
					result.setMsg("搜索失败！节点不存在!");
				} else {
					nodeID = Long.parseLong(strNodeID);
				}
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

		List<SmsSecurityCode> listSmsSecurityCodes = smsSecurityCodeService
				.listAllSmsSecurityCode(loginUser.getId(), nodeID, cellPhoneNum, null, mobileType, startTime, endTime, -1, -1);
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
		String title = "短信手机验证码统计";
		String[] headers = {"热点名", "手机号码", "运营商", "时间"};
		response.reset();
		response.setContentType("application/x-download;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
	    /*response.setHeader("Content-Disposition", "inline;filename = 短信手机验证码统计.xls");*/
		String fileName = "短信手机验证码统计";
		if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") >0){
			fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
	    }else{
	    	fileName = URLEncoder.encode(fileName, "UTF-8");
	    }
	    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
	    OutputStream out = response.getOutputStream();
		ExportExcel.exportExcel(title, headers, listSmsSecurityCodes, out);
		out.flush();
        out.close();
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

		byte tradeType = -1;
		Date startTime = null;
		Date endTime = null;
		if (strTradeType != null && !"".equals(strTradeType)) {
			tradeType = Byte.parseByte(strTradeType);
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

		List<UserTrade> listUserTrades = userTradeService
				.listAllUserTrade(loginUser.getId(), tradeType, startTime,
						endTime, limit, offset);
		mapUserTrades.put("total", userTradeService.getTotalCount(
				loginUser.getId(), tradeType, startTime, endTime));
		mapUserTrades.put("rows", listUserTrades);
		return mapUserTrades;
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
		String strNodeID = request.getParameter("ndName");
		String strStartTime = request.getParameter("startTime");
		String strEndTime = request.getParameter("endTime");

		long nodeID = -1;
		Date startTime = null;
		Date endTime = null;
		if (strNodeID != null && !"".equals(strNodeID)) {
			nodeID = Long.parseLong(strNodeID);
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

		List<SmsTrade> listSmsTrades = smsTradeService.listAllSmsTrade(loginUser.getId(), nodeID, startTime, endTime, limit, offset);
		
		for (SmsTrade smsTrade : listSmsTrades) {
			Node nodeTemp = nodeService.selectByPrimaryKey(smsTrade.getNodeID());
			if (nodeTemp != null) {
				if (nodeTemp.getAliasName() != null && !"".equals(nodeTemp.getAliasName())) {
					smsTrade.setNdName(nodeTemp.getAliasName());
				} else {
					smsTrade.setNdName(nodeTemp.getNdName());
				}
			}
			
		}
		mapSmsTrades.put("total", smsTradeService.getTotalCount(
				loginUser.getId(), nodeID, startTime, endTime));
		mapSmsTrades.put("rows", listSmsTrades);
		return mapSmsTrades;
	}

	@ResponseBody
	@RequestMapping(value = "/listNode", method = RequestMethod.POST)
	public List<BusinessNode> listNode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}

		List<Node> nodes = nodeService.listAllNode(-1L, loginUser.getId(),
				null, null, (byte) -1, -1L, -1L);
		List<BusinessNode> businessNodes = new ArrayList<BusinessNode>();
		for (Node node : nodes) {
			BusinessNode businessNode = new BusinessNode();
			businessNode.setId(node.getId());
			String nodeName = node.getAliasName();
			if (nodeName == null || nodeName.equals("")) {
				businessNode.setNdName(node.getNdName());
			} else {
				businessNode.setNdName(node.getAliasName());
			}
			businessNode.setMcode(node.getmCode());
			businessNode.setCreateTime(node.getCreateTime());
			businessNode.setNodeStatus(node.getNodeStatus());
			businessNode.setAliasName(node.getAliasName());
			businessNode.setEachLimitIncoming(node.getEachLimitIncoming());
			businessNode.setEachLimitOutgoing(node.getEachLimitOutgoing());
			businessNode.setEndTime(node.getEndTime());
			businessNode.setIndustry(node.getIndustry());
			businessNode.setLimitSpeed(node.getLimitSpeed());
			businessNode.setLoginType(node.getLoginType());
			businessNode.setMultiTerminalLogin(node.getMultiTerminalLogin());
			businessNode.setNodeStatus(node.getNodeStatus());
			businessNode.setPortalUrl(node.getPortalUrl());
			businessNode.setStartTime(node.getStartTime());
			businessNode.setTotalLimitIncoming(node.getTotalLimitIncoming());
			businessNode.setTotalLimitOutgoing(node.getTotalLimitOutgoing());
			businessNode.setTurnOffFreeTime(node.getTurnOffFreeTime());
			businessNode.setTurnOffTime(node.getTurnOffTime());
			businessNode.setUpdateTime(node.getUpdateTime());
			businessNode.setLimitOnlineUserNum(node.getLimitOnlineUserNum());
			businessNode.setRemainSms(node.getRemainSms());
			businessNode.setSmsCodeDayNum(node.getSmsCodeDayNum());
			businessNode.setSmsCodeLength(node.getSmsCodeLength());
			businessNode.setSmsCodeObtainInterval(node.getSmsCodeObtainInterval());
			businessNode.setSmsCodeValidTime(node.getSmsCodeValidTime());
			businessNode.setSmsCodeValidTimeType(node.getSmsCodeValidTimeType());
			businessNode.setSmsContentID(node.getSmsContentID());
			businessNode.setSmsType(node.getSmsType());
			businessNodes.add(businessNode);
		}
		return businessNodes;
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
		String ndName = request.getParameter("ndName");

		Result result = new Result();
		if (id != null && !"".equals(id) && ndName != null
				&& !"".equals(ndName)) {
			Node node = nodeService.selectByPrimaryKey(Long.parseLong(id));
			if (node != null) {
				if (node.getBusinessID() != -1) {
					if (ndName.equals(node.getNdName())) {
						long longTurnOffTime = HorizonConfig.NODE_DEFAULT_TURNOFFFREETIME;
						long longTurnOffFreeTime = HorizonConfig.NODE_DEFAULT_TURNOFFTIME;
						byte byteMultiTerminalLogin = (byte) Node.MULTITERMINALLOGIN.NO
								.ordinal();
						byte byteLimitSpeed = (byte) Node.LIMITSPEED.NO
								.ordinal();
						long longTotalLimitIncoming = HorizonConfig.NODE_DEFAULT_TOTALLIMITINCOMING;
						long longTotalLimitOutgoing = HorizonConfig.NODE_DEFAULT_TOTALLIMITOUTGOING;
						long longEachLimitIncoming = HorizonConfig.NODE_DEFAULT_EACHLIMITINCOMING;
						long longEachLimitOutgoing = HorizonConfig.NODE_DEFAULT_EACHLIMITOUTGOING;
						Time timeStartTime = Time.valueOf("00:00:00");
						Time timeEndTime = Time.valueOf("23:59:59");
						byte byteLoginType = (byte) HorizonConfig.NODE_LOGINTYPE.DIRECTORY
								.ordinal();
						byte byteNodeStatus = (byte) Node.NODESTATUS.NORMAL
								.ordinal();

						byte running = (byte) HorizonConfig.NODE_RUNNING.STOP
								.getIndex();
						int limitOnlineUserNum = HorizonConfig.NODE_DEFAULT_LIMITONLINEUSERNUM;
						long remainSms = HorizonConfig.NODE_DEFAULT_REMAINSMS;
						long smsCodeValidTime = HorizonConfig.NODE_DEFAULT_SMSCODEVALIDTIME;
						byte smsCodeValidTimeType = HorizonConfig.NODE_DEFAULT_SMSCODEVALIDTIMETYPE;
						byte smsType = (byte) HorizonConfig.NODE_SMSTYPE.ONCE
								.getIndex();
						int smsCodeLength = HorizonConfig.NODE_DEFAULT_SMSCODELENGTH;
						int smsCodeDayNum = HorizonConfig.NODE_DEFAULT_SMSCODEDAYNUM;
						long smsCodeObtainInterval = HorizonConfig.NODE_DEFAULT_SMSCODEOBTAININTERVAL;

						Node editNode = new Node(node.getNdName(), null,
								node.getmCode(), Byte.parseByte("-1"),
								longTurnOffTime, longTurnOffFreeTime,
								byteMultiTerminalLogin, byteLimitSpeed,
								longTotalLimitIncoming, longTotalLimitOutgoing,
								longEachLimitIncoming, longEachLimitOutgoing,
								timeStartTime, timeEndTime, null,
								byteLoginType, byteNodeStatus,
								node.getVenderID(), -1, limitOnlineUserNum,
								running, remainSms, smsCodeValidTime,smsCodeValidTimeType, smsType,
								smsCodeLength, smsCodeDayNum,
								smsCodeObtainInterval, -1);
						editNode.setUpdateTime(node.getCreateTime());
						editNode.setId(node.getId());
						nodeService.updateByPrimaryKey(editNode);
						result.setSuccess(true);
					} else {
						result.setSuccess(false);
						result.setMsg("删除路由失败！内部错误！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("删除路由失败！此路没有被绑定！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("删除路由失败！没有此路由！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("删除路由失败！必要字段可能为空！");
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
		String industry = request.getParameter("industry");
		String mcode = request.getParameter("mcode");
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
		String strlimitOnlineUserNum = request
				.getParameter("limitOnlineUserNum");

		String strSmsVerifyTemplateID = request.getParameter("smsVerifyTemplate");
		String strSmsCodeLength = request.getParameter("smsCodeLength");
		String strSmsCodeDayNum = request.getParameter("smsCodeDayNum");
		String strSmsCodeObtainInterval = request.getParameter("smsCodeObtainInterval");
		String strSmsType = request.getParameter("smsType");
		String strSmsCodeValidType = request.getParameter("smsCodeValidType");
		String strSmsCodeValidTime = request.getParameter("smsCodeValidTime");

		int num = loginUser.getVipLevel();
		int limitOnlineUserNum = User.LIMIT_ONLINE_MAX_NUM[num];

		Result result = new Result();
		if (id != null && !"".equals(id) && ndName != null
				&& !"".equals(ndName)) {
			Node node = nodeService.selectByPrimaryKey(Long.parseLong(id));

			if (node != null && node.getmCode().equals(mcode)) {
				if (node.getBusinessID() != -1) {
					node.setNdName(null);

					if (aliasName != null && "".equals(aliasName)) {
						aliasName = null;
					}

					if (portalUrl != null && "".equals(portalUrl)) {
						portalUrl = null;
					}
					node.setmCode(null);
					node.setVenderID(-1);
					node.setAliasName(aliasName);
					node.setPortalUrl(portalUrl);
					node.setBusinessID(loginUser.getId());

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

					if (eachLimitIncoming != null
							&& !"".equals(eachLimitIncoming)) {
						node.setEachLimitIncoming(Long
								.parseLong(eachLimitIncoming));
					}

					if (eachLimitOutgoing != null
							&& !"".equals(eachLimitOutgoing)) {
						node.setEachLimitOutgoing(Long
								.parseLong(eachLimitOutgoing));
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

					if (strSmsCodeValidTime != null
							&& !"".equals(strSmsCodeValidTime)) {
						node.setSmsCodeValidTime(Long
								.parseLong(strSmsCodeValidTime));
					}
					if (strSmsType != null && !"".equals(strSmsType)) {
						node.setSmsType(Byte.parseByte(strSmsType));
					}
					if (strSmsCodeLength != null
							&& !"".equals(strSmsCodeLength)) {
						node.setSmsCodeLength(Integer
								.parseInt(strSmsCodeLength));
					}
					if (strSmsCodeDayNum != null
							&& !"".equals(strSmsCodeDayNum)) {
						node.setSmsCodeDayNum(Integer
								.parseInt(strSmsCodeDayNum));
					}
					if (strSmsCodeObtainInterval != null
							&& !"".equals(strSmsCodeObtainInterval)) {
						node.setSmsCodeObtainInterval(Long
								.parseLong(strSmsCodeObtainInterval));
					}

					node.setLimitOnlineUserNum(limitOnlineUserNum);

					if (node.getStartTime().compareTo(node.getEndTime()) >= 0) {
						result.setSuccess(false);
						result.setMsg("修改路由失败！开放开始时间不能大于开放小于时间！");
					} else {
						if (strlimitOnlineUserNum != null
								&& !"".equals(strlimitOnlineUserNum)) {
							limitOnlineUserNum = Integer
									.parseInt(strlimitOnlineUserNum);
							if (limitOnlineUserNum <= User.LIMIT_ONLINE_MAX_NUM[num]) {
								node.setLimitOnlineUserNum(limitOnlineUserNum);
								
								if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE.getIndex()) {
									if (strSmsVerifyTemplateID != null && !"".equals(strSmsVerifyTemplateID)) {
										node.setSmsContentID(Long.parseLong(strSmsVerifyTemplateID));
									}
									
									if (strSmsCodeLength != null && !"".equals(strSmsCodeLength)) {
										node.setSmsCodeLength(Integer.parseInt(strSmsCodeLength));
									}
									
									if (strSmsCodeDayNum != null && !"".equals(strSmsCodeDayNum)) {
										node.setSmsCodeDayNum(Integer.parseInt(strSmsCodeDayNum));
									}
									
									if (strSmsCodeObtainInterval != null && !"".equals(strSmsCodeObtainInterval)) {
										node.setSmsCodeObtainInterval(Long.parseLong(strSmsCodeObtainInterval));
									}
									
									if (strSmsType != null && !"".equals(strSmsType)) {
										node.setSmsType(Byte.parseByte(strSmsType));
									}
									
									if (strSmsCodeValidType != null && !"".equals(strSmsCodeValidType)) {
										node.setSmsCodeValidTimeType(Byte.parseByte(strSmsCodeValidType));
									}
									
									if (strSmsCodeValidTime != null && !"".equals(strSmsCodeValidTime)) {
										node.setSmsCodeValidTime(Long.parseLong(strSmsCodeValidTime));
									}
									
								}
								
								node.setUpdateTime(Calendar.getInstance(
										TimeZone.getDefault()).getTime());
								nodeService.updateByPrimaryKeySelective(node);
								result.setSuccess(true);
							} else {
								result.setSuccess(false);
								result.setMsg("修改路由失败！同时在线人数设置太大，只能小于等于"
										+ User.LIMIT_ONLINE_MAX_NUM[num] + "!");
							}
						} else {
							node.setUpdateTime(Calendar.getInstance(
									TimeZone.getDefault()).getTime());
							nodeService.updateByPrimaryKeySelective(node);
							result.setSuccess(true);
						}
					}
				} else {
					result.setSuccess(false);
					result.setMsg("修改路由失败！此路没有被绑定，请确定路由标识！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("修改路由失败！没有此路由！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("修改路由失败！必要字段可能为空！");
		}
		return result;
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

		String ndName = request.getParameter("ndName");
		String aliasName = request.getParameter("aliasName");
		String mcode = request.getParameter("mcode");
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
		
		String strSmsVerifyTemplateID = request.getParameter("smsVerifyTemplate");
		String strSmsCodeLength = request.getParameter("smsCodeLength");
		String strSmsCodeDayNum = request.getParameter("smsCodeDayNum");
		String strSmsCodeObtainInterval = request.getParameter("smsCodeObtainInterval");
		String strSmsType = request.getParameter("smsType");
		String strSmsCodeValidType = request.getParameter("smsCodeValidType");
		String strSmsCodeValidTime = request.getParameter("smsCodeValidTime");
		
		String strlimitOnlineUserNum = request
				.getParameter("limitOnlineUserNum");

		int num = loginUser.getVipLevel();
		int limitOnlineUserNum = User.LIMIT_ONLINE_MAX_NUM[num];

		Result result = new Result();
		if (mcode != null && !"".equals(mcode)) {
			Node node = nodeService.selectByMCode(mcode);

			if (node != null) {
				if (ndName == null || "".equals(ndName)) {
					result.setSuccess(false);
					result.setMsg("绑定路由失败！热点名不能为空！");
					return result;
				}
				if (nodeService.getTotalCount(loginUser.getId(), ndName) == 0) {
					if (node.getBusinessID() == -1) {
						if (industry != null && !"".equals(industry)) {
							node.setIndustry(Byte.parseByte(industry));
							if (aliasName != null && "".equals(aliasName)) {
								aliasName = null;
							}

							if (portalUrl != null && "".equals(portalUrl)) {
								portalUrl = null;
							}
							node.setNdName(ndName);
							node.setmCode(null);
							node.setVenderID(-1);
							node.setAliasName(aliasName);
							node.setPortalUrl(portalUrl);
							node.setBusinessID(loginUser.getId());
							if (turnOffTime != null && !"".equals(turnOffTime)) {
								node.setTurnOffTime(Long.parseLong(turnOffTime));
							}

							if (turnOffFreeTime != null
									&& !"".equals(turnOffFreeTime)) {
								node.setTurnOffFreeTime(Long
										.parseLong(turnOffFreeTime));
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

							if (eachLimitIncoming != null
									&& !"".equals(eachLimitIncoming)) {
								node.setEachLimitIncoming(Long
										.parseLong(eachLimitIncoming));
							}

							if (eachLimitOutgoing != null
									&& !"".equals(eachLimitOutgoing)) {
								node.setEachLimitOutgoing(Long
										.parseLong(eachLimitOutgoing));
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

							node.setLimitOnlineUserNum(limitOnlineUserNum);
							if (node.getStartTime()
									.compareTo(node.getEndTime()) >= 0) {
								result.setSuccess(false);
								result.setMsg("绑定路由失败！开放开始时间不能大于开放小于时间！");
							} else {
								if (strlimitOnlineUserNum != null
										&& !"".equals(strlimitOnlineUserNum)) {
									limitOnlineUserNum = Integer
											.parseInt(strlimitOnlineUserNum);
									if (limitOnlineUserNum <= User.LIMIT_ONLINE_MAX_NUM[num]) {
										node.setLimitOnlineUserNum(limitOnlineUserNum);
										
										if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE.getIndex()) {
											if (strSmsVerifyTemplateID != null && !"".equals(strSmsVerifyTemplateID)) {
												node.setSmsContentID(Long.parseLong(strSmsVerifyTemplateID));
											}
											
											if (strSmsCodeLength != null && !"".equals(strSmsCodeLength)) {
												node.setSmsCodeLength(Integer.parseInt(strSmsCodeLength));
											}
											
											if (strSmsCodeDayNum != null && !"".equals(strSmsCodeDayNum)) {
												node.setSmsCodeDayNum(Integer.parseInt(strSmsCodeDayNum));
											}
											
											if (strSmsCodeObtainInterval != null && !"".equals(strSmsCodeObtainInterval)) {
												node.setSmsCodeObtainInterval(Long.parseLong(strSmsCodeObtainInterval));
											}
											
											if (strSmsType != null && !"".equals(strSmsType)) {
												node.setSmsType(Byte.parseByte(strSmsType));
											}
											
											if (strSmsCodeValidType != null && !"".equals(strSmsCodeValidType)) {
												node.setSmsCodeValidTimeType(Byte.parseByte(strSmsCodeValidType));
											}
											
											if (strSmsCodeValidTime != null && !"".equals(strSmsCodeValidTime)) {
												node.setSmsCodeValidTime(Long.parseLong(strSmsCodeValidTime));
											}
											
										}
										
										node.setUpdateTime(Calendar
												.getInstance(
														TimeZone.getDefault())
												.getTime());
										nodeService
												.updateByPrimaryKeySelective(node);
										result.setSuccess(true);
									} else {
										result.setSuccess(false);
										result.setMsg("修改路由失败！同时在线人数设置太大，只能小于等于"
												+ User.LIMIT_ONLINE_MAX_NUM[num]
												+ "!");
									}
								} else {
									node.setUpdateTime(Calendar.getInstance(
											TimeZone.getDefault()).getTime());
									nodeService
											.updateByPrimaryKeySelective(node);
									result.setSuccess(true);
								}
							}
						} else {
							result.setSuccess(false);
							result.setMsg("绑定路由失败！请选择行业！");
						}
					} else {
						result.setSuccess(false);
						result.setMsg("绑定路由失败！此路由已被绑定，请确定路由标识！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("绑定路由失败！热点名已使用！");
				}

			} else {
				result.setSuccess(false);
				result.setMsg("绑定路由失败！没有此路由！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("绑定路由失败！必要字段可能为空！");
		}
		return result;
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
		String strNodeID = request.getParameter("nodeID");
		String userType = request.getParameter("userType");
		String multiTerminalLogin = request.getParameter("multiTerminalLogin");
		String strValidTime = request.getParameter("validTime");
		String usableTime = request.getParameter("usableTime");
		String userStatus = request.getParameter("userStatus");
		String description = request.getParameter("description");

		Result result = new Result();
		if (userName != null && !"".equals(userName) && password != null
				&& !"".equals(password) && userType != null
				&& !"".equals(userType)) {
			SimpleDateFormat sdf = new SimpleDateFormat(
					HorizonConfig.DATA_FORMAT);
			Date validTime = null;
			try {
				if (strValidTime != null && !"".equals(strValidTime)) {
					validTime = sdf.parse(strValidTime);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				result.setSuccess(false);
				result.setMsg("增加用户失败！用户名或者密码为空！");
				return result;
			}
			/*
			 * Calendar calendar = new GregorianCalendar();
			 * calendar.setTime(validTime); calendar.add(calendar.DATE, 1);
			 * validTime = calendar.getTime();
			 */
			long nodeID = -2L;// 默认支持所有节点
			if (strNodeID != null && !strNodeID.equals("")) {
				nodeID = Long.parseLong(strNodeID);
			}
			CommonUser user = new CommonUser(userName, password, nodeID,
					loginUser.getId(), Byte.parseByte(userType),
					Byte.parseByte(multiTerminalLogin), validTime,
					Long.parseLong(usableTime) * 60 * 1000,
					Byte.parseByte(userStatus), description);
			commonUserService.insertSelective(user);
			result.setSuccess(true);
			result.setObj(user);
		} else {
			result.setSuccess(false);
			result.setMsg("增加用户失败！用户名或者密码为空！");
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
		String strNodeID = request.getParameter("nodeID");
		String userType = request.getParameter("userType");
		String multiTerminalLogin = request.getParameter("multiTerminalLogin");
		String strValidTime = request.getParameter("validTime");
		String usableTime = request.getParameter("usableTime");
		String userStatus = request.getParameter("userStatus");
		String description = request.getParameter("description");

		Result result = new Result();
		try {
			if (id != null && !"".equals(id)) {
				CommonUser user = commonUserService.selectByMap(Long
						.parseLong(id));
				if (user != null) {
					CommonUser editUser = new CommonUser();
					editUser.setId(user.getId());
					if (user.getUserName().equalsIgnoreCase(userName)) {

						if (strNodeID != null
								&& !strNodeID.equalsIgnoreCase("")
								&& user.getNodeID() != Long
										.parseLong(strNodeID)) {
							editUser.setNodeID(Long.parseLong(strNodeID));
						}

						if (userType != null
								&& !userType.equals("")
								&& user.getUserType() != Byte
										.parseByte(userType)) {
							editUser.setUserType(Byte.parseByte(userType));
						}

						if (multiTerminalLogin != null
								&& !multiTerminalLogin.equals("")
								&& user.getMultiTerminalLogin() != Byte
										.parseByte(multiTerminalLogin)) {
							editUser.setMultiTerminalLogin(Byte
									.parseByte(multiTerminalLogin));
						}

						Date validTime = null;
						try {
							SimpleDateFormat sdf = new SimpleDateFormat(
									HorizonConfig.DATA_FORMAT);
							if (strValidTime != null
									&& !"".equals(strValidTime)) {
								validTime = sdf.parse(strValidTime);
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							result.setSuccess(false);
							result.setMsg("修改用户信息失败！内部错误！");
							return result;
						}

						if (validTime != null) {
							if (user.getValidTime() != null) {
								if (validTime.compareTo(user.getValidTime()) != 0) {
									editUser.setValidTime(validTime);
								}
							} else {
								editUser.setValidTime(validTime);
							}
						}

						if (usableTime != null
								&& !usableTime.equals("")
								&& user.getUsableTime() != Long
										.parseLong(usableTime)) {
							editUser.setUsableTime(Long.parseLong(usableTime) * 1000 / 60);
						}

						if (userStatus != null
								&& !userStatus.equals("")
								&& user.getUserStatus() != Byte
										.parseByte(userStatus)) {
							editUser.setUserStatus(Byte.parseByte(userStatus));
						}

						if (description != null
								&& !user.getDescription().equals(description)) {
							editUser.setDescription(description);
						}

						editUser.setUpdateTime(Calendar.getInstance(
								TimeZone.getDefault()).getTime());
						commonUserService.updateByPrimaryKeySelective(editUser);
						result.setSuccess(true);

					} else {
						result.setSuccess(false);
						result.setMsg("修改用户信息失败！用户名不能修改");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("修改用户信息失败！没有此用户");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("修改用户信息失败！内部错误");
				// throw new
				// ServletException("Add user errer! Maybe user name or password is null!");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("修改用户信息失败！内部错误");
			e.printStackTrace();
			return result;
		}
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
				CommonUser user = commonUserService.selectByMap(Long
						.parseLong(id));
				if (user != null) {
					CommonUser editUser = new CommonUser();
					editUser.setId(user.getId());
					if (user.getUserName().equalsIgnoreCase(userName)) {

						if (mpassword != null
								&& !mpassword.equalsIgnoreCase("")) {
							if (rpassword != null
									&& rpassword.equals(mpassword)) {
								if (!user.getPassword().equals(mpassword)) {
									editUser.setPassword(mpassword);
									editUser.setUpdateTime(Calendar
											.getInstance(TimeZone.getDefault())
											.getTime());
									commonUserService
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

		Map<String, Object> listUser = new HashMap<String, Object>();
		long page = Long
				.parseLong((strPage == null || strPage.equals("0")) ? "1"
						: strPage);
		long limit = Long
				.parseLong((strRows == null || strRows.equals("0")) ? "1"
						: strRows);
		long offset = (page - 1) * limit;

		List<CommonUser> users = commonUserService.listAllUser(
				loginUser.getId(), limit, offset);
		List<CommonUser> commonUsers = new ArrayList<CommonUser>();
		for (CommonUser user : users) {
			long nodeID = user.getNodeID();
			if (nodeID != -1 && nodeID != -2) {
				Node node = nodeService.selectByPrimaryKey(nodeID);
				String nodeName = node.getAliasName();
				if (nodeName == null || nodeName.equals("")) {
					user.setNodeName(node.getNdName());
				} else {
					user.setNodeName(node.getAliasName());
				}
			}
			user.setPassword("");
			user.setUsableTime(user.getUsableTime() / 1000 / 60);
			commonUsers.add(user);
		}
		listUser.put("total",
				commonUserService.getTotalCount(loginUser.getId()));
		listUser.put("rows", commonUsers);
		return listUser;
	}
	
	@ResponseBody
	@RequestMapping(value = "/smsVerifyTemplate", method = RequestMethod.POST)
	public List<SmsContent> smsVerifyTemplate(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath() + "/");
		}


		List<SmsContent> smsContents = smsContentService.listAllSmsContent(loginUser.getId(), -1, -1);
		return smsContents;
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
		try {
			if (id != null && !"".equals(id)) {
				commonUserService.deleteByPrimaryKey(Long.parseLong(id));
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setMsg("删除用户失败！");
				// throw new
				// ServletException("Add user errer! Maybe user name or password is null!");
			}
			return result;
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg("增加用户失败！内部错误");
			e.printStackTrace();
			return result;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/validMCode", method = RequestMethod.POST)
	public Result validMCode(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String mcode = request.getParameter("mcode");

		Result result = new Result();
		if (mcode != null && !"".equals(mcode)) {
			Node node = nodeService.selectByMCode(mcode);
			if (node == null) {
				result.setSuccess(false);
				result.setMsg("路由不存在！");
			} else {
				if (node.getBusinessID() != -1) {
					result.setSuccess(false);
					result.setMsg("路由已绑定商家！");
				} else {
					result.setObj(node.getNdName());
					result.setSuccess(true);
				}
			}
		} else {
			result.setSuccess(false);
			result.setMsg("请输入机器码！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/validNdName", method = RequestMethod.POST)
	public Result validNdName(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		String ndName = request.getParameter("ndName");

		Result result = new Result();
		if (ndName != null && !"".equals(ndName)) {
			long nodeNum = nodeService.getTotalCount(loginUser.getId(), ndName);
			if (nodeNum == 0) {
				result.setSuccess(true);
			} else {
				result.setSuccess(false);
				result.setMsg("热点已存在！");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("请输入热点名！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/getLimitMaxOnlineNum", method = RequestMethod.POST)
	public int getLimitMaxOnlineNum(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		User loginUser = (User) session.getAttribute("user");

		if (loginUser == null) {
			response.sendRedirect(request.getContextPath());
		}

		int num = loginUser.getVipLevel();
		if (User.LIMIT_ONLINE_MAX_NUM.length >= num) {
			return User.LIMIT_ONLINE_MAX_NUM[num];
		} else {
			return 0;
		}
	}
}