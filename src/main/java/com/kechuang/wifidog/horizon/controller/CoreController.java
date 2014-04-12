package com.kechuang.wifidog.horizon.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;

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
import com.kechuang.wifidog.horizon.model.Lever;
import com.kechuang.wifidog.horizon.model.MobileLocation;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.model.NodeLever;
import com.kechuang.wifidog.horizon.model.Result;
import com.kechuang.wifidog.horizon.model.RouteRecord;
import com.kechuang.wifidog.horizon.model.RouteStatus;
import com.kechuang.wifidog.horizon.model.SmsSecurityCode;
import com.kechuang.wifidog.horizon.model.Tokens;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.CoreUtilService;
import com.kechuang.wifidog.horizon.service.LeverService;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;
import com.kechuang.wifidog.horizon.service.NodeLeverService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.service.RouteRecordService;
import com.kechuang.wifidog.horizon.service.RouteStatusService;
import com.kechuang.wifidog.horizon.service.SmsContentService;
import com.kechuang.wifidog.horizon.service.SmsSecurityCodeService;
import com.kechuang.wifidog.horizon.service.TokensService;
import com.kechuang.wifidog.horizon.service.UserService;
import com.kechuang.wifidog.horizon.utils.ChineseSmsUtils;
import com.kechuang.wifidog.horizon.utils.CommonUtils;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;
import com.kechuang.wifidog.horizon.utils.HorizonCoreUtil;
import com.kechuang.wifidog.horizon.utils.MobileLocationUtil;
import com.kechuang.wifidog.horizon.utils.PropertiesTool;
import com.kechuang.wifidog.horizon.utils.TokensUtil;

@Controller
@RequestMapping("/core")
public class CoreController {
	private static final Logger LOG = Logger.getLogger(CoreController.class);

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.UserService")
	private UserService userService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.TokensService")
	private TokensService tokensService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CoreUtilService")
	private CoreUtilService coreUtilService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.RouteRecordService")
	private RouteRecordService routeRecordService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.RouteStatusService")
	private RouteStatusService routeStatusService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsSecurityCodeService")
	private SmsSecurityCodeService smsSecurityCodeService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeLeverService")
	private NodeLeverService nodeLeverService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.LeverService")
	private LeverService leverService;
	
	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.SmsContentService")
	private SmsContentService smsContentService;

	@ResponseBody
	@RequestMapping(value = "/auth/", method = RequestMethod.GET)
	public void auth(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {
		Enumeration keys = request.getParameterNames();
		System.out.println("auth get parameter scan start!");
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + "\t:\t" + request.getParameter(key));
		}
		System.out.println("parameter scan end!");

		String stage = request.getParameter("stage");
		String ip = request.getParameter("ip");
		String mac = request.getParameter("mac");
		String token = request.getParameter("token");
		String incoming = request.getParameter("incoming");
		String outcoming = request.getParameter("outgoing");
		String nodeName = request.getParameter("gw_id");
		String mcode = request.getParameter("mcode");
		String wwver = request.getParameter("wwver");

		if (stage != null && !"".equals(stage) && mcode != null
				&& !"".equals(mcode) && incoming != null
				&& !"".equals(incoming) && outcoming != null
				&& !"".equals(outcoming)) {
			if (stage.equalsIgnoreCase(HorizonConfig.STAGE_STATUS.STAGE_LOGIN
					.getName())) {
				if (token != null && !"".equals(token)) {
					Tokens tokens = tokensService.selectByMap(token);
					if (tokens != null) {
						Node node = nodeService.selectByMCode(mcode);
						if (node != null && node.getNdName().equals(nodeName)) {
							NodeConnection nodeConnection = nodeConnectionService
									.selectByMap(tokens.getId(), node.getId(),
											node.getBusinessID());

							if (nodeConnection != null) {
								if (nodeConnection.getStatus() == NodeConnection.STATUS.INIT
										.ordinal()) {
									Date updateTime = Calendar.getInstance(
											TimeZone.getDefault()).getTime();
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setConnectEnd(updateTime);
									if (nodeConnection.getIp()
											.equalsIgnoreCase(ip)
											&& nodeConnection.getMac()
													.equalsIgnoreCase(mac)) {
										long longIncoming = Long
												.parseLong(incoming);
										long longOutcoming = Long
												.parseLong(outcoming);
										editNodeConnection
												.setIncoming(longIncoming);
										editNodeConnection
												.setOutgoing(longOutcoming);
										editNodeConnection
												.setTotalIncoming(nodeConnection
														.getTotalIncoming()
														+ longIncoming);
										editNodeConnection
												.setTotalOutgoing(nodeConnection
														.getTotalOutgoing()
														+ longOutcoming);
										if (coreUtilService.checkConnectOne(
												nodeConnection,
												editNodeConnection, node)) {
											if (coreUtilService
													.checkConnectTwo(
															nodeConnection,
															editNodeConnection,
															node)) {
												if (editNodeConnection
														.getIncoming() == nodeConnection
														.getIncoming()
														&& editNodeConnection
																.getOutgoing() == nodeConnection
																.getOutgoing()) {
													editNodeConnection
															.setFreeTime(editNodeConnection
																	.getFreeTime()
																	+ (updateTime
																			.getTime() - nodeConnection
																			.getConnectEnd()
																			.getTime()));
												} else {
													editNodeConnection
															.setFreeTime(0);
												}

											} else {
												editNodeConnection
														.setStatus((byte) NodeConnection.STATUS.OFFLINE
																.ordinal());
												nodeConnectionService
														.updateByPrimaryKeySelective(editNodeConnection);
												tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
														.getIndex());
												tokensService
														.updateByPrimaryKeySelective(tokens);
												response.getWriter().write(
														"Auth: 0");
												response.flushBuffer();
												return;
											}

										} else {
											editNodeConnection
													.setStatus((byte) NodeConnection.STATUS.OFFLINE
															.ordinal());
											nodeConnectionService
													.updateByPrimaryKeySelective(editNodeConnection);
											tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
													.getIndex());
											tokensService
													.updateByPrimaryKeySelective(tokens);
											response.getWriter().write(
													"Auth: 0");
											response.flushBuffer();
											return;
										}

										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.ONLINE
														.ordinal());
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_INUSE
												.getIndex());
										tokensService
												.updateByPrimaryKeySelective(tokens);
										response.getWriter().write("Auth: 1");
										response.flushBuffer();
										return;
									} else {
										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.OFFLINE
														.ordinal());
										editNodeConnection
												.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
														.ordinal());
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
												.getIndex());
										tokensService
												.updateByPrimaryKeySelective(tokens);
										response.getWriter().write("Auth: 0");
										response.flushBuffer();
										return;
									}
								} else if (nodeConnection.getStatus() == NodeConnection.STATUS.ONLINE
										.ordinal()) {
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setStatus((byte) NodeConnection.STATUS.OFFLINE
													.ordinal());
									editNodeConnection
											.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
													.ordinal());
									nodeConnectionService
											.updateByPrimaryKeySelective(editNodeConnection);
									tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
											.getIndex());
									tokensService
											.updateByPrimaryKeySelective(tokens);
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								} else {
									LOG.error("node connection status is errer!node connection is " + nodeConnection.getStatus());
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								}

							} else {
								LOG.error("node connection not found!tokenID=" + tokens.getId() + ";nodeID=" + node.getId() + ";businessID=" + node.getBusinessID());
								response.getWriter().write("Auth: 6");
								response.flushBuffer();
								return;
							}
						} else {
							LOG.error("node name is errer!nodeName:\t" + nodeName);
							response.getWriter().write("Auth: 0");
							response.flushBuffer();
							return;
						}
					} else {
						LOG.error("token is not found!token:\t" + token);
						response.getWriter().write("Auth: 6");
						response.flushBuffer();
						return;
					}
				} else {
					LOG.error("token is not found!");
					response.getWriter().write("Auth: 6");
					response.flushBuffer();
					return;
				}
			} else if (stage
					.equalsIgnoreCase(HorizonConfig.STAGE_STATUS.STAGE_COUNTERS
							.getName())) {
				if (token != null && !"".equals(token)) {
					Tokens tokens = tokensService.selectByMap(token);
					if (tokens != null) {
						Node node = nodeService.selectByMCode(mcode);
						if (node != null && node.getNdName().equals(nodeName)) {
							NodeConnection nodeConnection = nodeConnectionService
									.selectByMap(tokens.getId(), node.getId(),
											node.getBusinessID());

							if (nodeConnection != null) {
								if (nodeConnection.getStatus() == NodeConnection.STATUS.ONLINE
										.ordinal()) {
									Date updateTime = Calendar.getInstance(
											TimeZone.getDefault()).getTime();
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setConnectEnd(updateTime);
									if (nodeConnection.getIp()
											.equalsIgnoreCase(ip)
											&& nodeConnection.getMac()
													.equalsIgnoreCase(mac)) {
										long longIncoming = Long
												.parseLong(incoming);
										long longOutcoming = Long
												.parseLong(outcoming);
										editNodeConnection
												.setIncoming(longIncoming);
										editNodeConnection
												.setOutgoing(longOutcoming);
										editNodeConnection
												.setTotalIncoming(nodeConnection
														.getTotalIncoming()
														+ longIncoming);
										editNodeConnection
												.setTotalOutgoing(nodeConnection
														.getTotalOutgoing()
														+ longOutcoming);
										if (coreUtilService.checkConnectTwo(
												nodeConnection,
												editNodeConnection, node)) {
											if (editNodeConnection
													.getIncoming() == nodeConnection
													.getIncoming()
													&& editNodeConnection
															.getOutgoing() == nodeConnection
															.getOutgoing()) {
												editNodeConnection
														.setFreeTime(editNodeConnection
																.getFreeTime()
																+ (updateTime
																		.getTime() - nodeConnection
																		.getConnectEnd()
																		.getTime()));
											} else {
												editNodeConnection
														.setFreeTime(0);
											}
										} else {
											editNodeConnection
													.setStatus((byte) NodeConnection.STATUS.OFFLINE
															.ordinal());
											nodeConnectionService
													.updateByPrimaryKeySelective(editNodeConnection);
											tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
													.getIndex());
											tokensService
													.updateByPrimaryKeySelective(tokens);
											response.getWriter().write(
													"Auth: 0");
											response.flushBuffer();
											return;
										}

										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.ONLINE
														.ordinal());
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										response.getWriter().write("Auth: 1");
										response.flushBuffer();
										return;
									} else {
										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.OFFLINE
														.ordinal());
										editNodeConnection
												.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
														.ordinal());
										tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
												.getIndex());
										tokensService
												.updateByPrimaryKeySelective(tokens);
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										response.getWriter().write("Auth: 0");
										response.flushBuffer();
										return;
									}
								} else if (nodeConnection.getStatus() == NodeConnection.STATUS.INIT
										.ordinal()) {
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setStatus((byte) NodeConnection.STATUS.OFFLINE
													.ordinal());
									editNodeConnection
											.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
													.ordinal());
									nodeConnectionService
											.updateByPrimaryKeySelective(editNodeConnection);
									tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
											.getIndex());
									tokensService
											.updateByPrimaryKeySelective(tokens);
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								} else {
									LOG.error("node connection status is errer!node connection is " + nodeConnection.getStatus());
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								}

							} else {
								LOG.error("node connection not found!tokenID=" + tokens.getId() + ";nodeID=" + node.getId() + ";businessID=" + node.getBusinessID());
								response.getWriter().write("Auth: 6");
								response.flushBuffer();
								return;
							}
						} else {
							LOG.error("node name is errer!nodeName:\t" + nodeName);
							response.getWriter().write("Auth: 0");
							response.flushBuffer();
							return;
						}
					} else {
						LOG.error("token is not found!token:\t" + token);
						response.getWriter().write("Auth: 6");
						response.flushBuffer();
						return;
					}
				} else {
					LOG.error("token is not found!");
					response.getWriter().write("Auth: 6");
					response.flushBuffer();
					return;
				}
			} else if (stage
					.equalsIgnoreCase(HorizonConfig.STAGE_STATUS.STAGE_LOGOUT
							.getName())) {
				if (token != null && !"".equals(token)) {
					Tokens tokens = tokensService.selectByMap(token);
					if (tokens != null) {
						Node node = nodeService.selectByMCode(mcode);
						if (node != null && node.getNdName().equals(nodeName)) {
							NodeConnection nodeConnection = nodeConnectionService
									.selectByMap(tokens.getId(), node.getId(),
											node.getBusinessID());

							if (nodeConnection != null) {
								if (nodeConnection.getStatus() == NodeConnection.STATUS.ONLINE
										.ordinal()) {
									Date updateTime = Calendar.getInstance(
											TimeZone.getDefault()).getTime();
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setConnectEnd(updateTime);
									if (nodeConnection.getIp()
											.equalsIgnoreCase(ip)
											&& nodeConnection.getMac()
													.equalsIgnoreCase(mac)) {
										long longIncoming = Long
												.parseLong(incoming);
										long longOutcoming = Long
												.parseLong(outcoming);
										editNodeConnection
												.setIncoming(longIncoming);
										editNodeConnection
												.setOutgoing(longOutcoming);
										editNodeConnection
												.setTotalIncoming(nodeConnection
														.getTotalIncoming()
														+ longIncoming);
										editNodeConnection
												.setTotalOutgoing(nodeConnection
														.getTotalOutgoing()
														+ longOutcoming);
										if (coreUtilService.checkConnectTwo(
												nodeConnection,
												editNodeConnection, node)) {
											if (editNodeConnection
													.getIncoming() == nodeConnection
													.getIncoming()
													&& editNodeConnection
															.getOutgoing() == nodeConnection
															.getOutgoing()) {
												editNodeConnection
														.setFreeTime(editNodeConnection
																.getFreeTime()
																+ (updateTime
																		.getTime() - nodeConnection
																		.getConnectEnd()
																		.getTime()));
											} else {
												editNodeConnection
														.setFreeTime(0);
											}
										} else {
											editNodeConnection
													.setStatus((byte) NodeConnection.STATUS.OFFLINE
															.ordinal());
											nodeConnectionService
													.updateByPrimaryKeySelective(editNodeConnection);
											tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
													.getIndex());
											tokensService
													.updateByPrimaryKeySelective(tokens);
											response.getWriter().write(
													"Auth: 0");
											response.flushBuffer();
											return;
										}

										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.OFFLINE
														.ordinal());
										editNodeConnection
												.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER12
														.ordinal());
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
												.getIndex());
										tokensService
												.updateByPrimaryKeySelective(tokens);
										response.getWriter().write("Auth: 0");
										response.flushBuffer();
										return;
									} else {
										editNodeConnection
												.setStatus((byte) NodeConnection.STATUS.OFFLINE
														.ordinal());
										editNodeConnection
												.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
														.ordinal());
										nodeConnectionService
												.updateByPrimaryKeySelective(editNodeConnection);
										tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
												.getIndex());
										tokensService
												.updateByPrimaryKeySelective(tokens);
										response.getWriter().write("Auth: 0");
										response.flushBuffer();
										return;
									}
								} else if (nodeConnection.getStatus() == NodeConnection.STATUS.INIT
										.ordinal()) {
									NodeConnection editNodeConnection = new NodeConnection();
									editNodeConnection.setId(nodeConnection
											.getId());
									editNodeConnection
											.setStatus((byte) NodeConnection.STATUS.OFFLINE
													.ordinal());
									editNodeConnection
											.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER1
													.ordinal());
									nodeConnectionService
											.updateByPrimaryKeySelective(editNodeConnection);
									tokens.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
											.getIndex());
									tokensService
											.updateByPrimaryKeySelective(tokens);
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								} else {
									LOG.error("node connection status is errer!node connection is " + nodeConnection.getStatus());
									response.getWriter().write("Auth: 0");
									response.flushBuffer();
									return;
								}

							} else {
								LOG.error("node connection not found!tokenID=" + tokens.getId() + ";nodeID=" + node.getId() + ";businessID=" + node.getBusinessID());
								response.getWriter().write("Auth: 6");
								response.flushBuffer();
								return;
							}
						} else {
							LOG.error("node name is errer!nodeName:\t" + nodeName);
							response.getWriter().write("Auth: 0");
							response.flushBuffer();
							return;
						}

					} else {
						LOG.error("token is not found!token:\t" + token);
						response.getWriter().write("Auth: 6");
						response.flushBuffer();
						return;
					}
				} else {
					LOG.error("token is not found!");
					response.getWriter().write("Auth: 6");
					response.flushBuffer();
					return;
				}
			}
		} else {
			LOG.error("parameter stage or incoming or mcode or outcoming is null!");
			response.getWriter().write("Auth: 0");
			response.flushBuffer();
			return;
		}
	}

	@ResponseBody
	@RequestMapping(value = "/login/", method = RequestMethod.GET)
	public void login(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {
		Enumeration keys = request.getParameterNames();
		System.out.println("login get parameter scan start!");
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + "\t:\t" + request.getParameter(key));
		}
		System.out.println("parameter scan end!");
		String gateWayAddress = request.getParameter("gw_address");
		String gateWayPort = request.getParameter("gw_port");
		String gateWayID = request.getParameter("gw_id");
		String ip = request.getParameter("ip");
		String mac = request.getParameter("mac");
		String key = request.getParameter("key");
		String wwver = request.getParameter("wwver");
		String onlineUser = request.getParameter("onlineuser");
		String url = request.getParameter("url");
		String loginType = (String) request.getParameter("loginType");
		String userName = (String) request.getParameter("userName");
		String cellPhoneNum = (String) request.getParameter("cellPhoneNum");

		if (gateWayID != null && !"".equals(gateWayID)
				&& gateWayAddress != null && !"".equals(gateWayAddress)
				&& gateWayPort != null && !"".equals(gateWayPort)) {
			Node node = nodeService.selectByNodeName(gateWayID);
			if (node.getRunning() == HorizonConfig.NODE_RUNNING.RUNNING
					.getIndex()) {// 判断路由是否正在运行
				if (node != null && node.getNdName().equals(gateWayID)) {// 判断热点名是否正确
					if (node.getBusinessID() != -1) {// 判断路由是否激活
						if (node.getNodeStatus() == Node.NODESTATUS.NORMAL
								.ordinal()) {// 判断路由的状态
							NodeLever nodeLever = nodeLeverService.selectByNodeID(node.getId());
							Lever lever = leverService.selectByMap(nodeLever.getVipID());
							if (Integer.parseInt(onlineUser) < lever.getMaxOnlineNum()) {// 判断路由在线人数是否达到饱和
								session.setAttribute("originURL", url);
								if (loginType != null) {// 判断是否登录成功
									String token = TokensUtil.generateTokens();
									tokensService
											.deleteByStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
													.getIndex());
									Tokens tokensInsert = new Tokens();
									tokensInsert.setToken(token);
									tokensInsert
											.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_UNUSED
													.getIndex());
									tokensInsert.setNodeID(node.getId());
									tokensService.insert(tokensInsert);
									// /////////////////////////////////////////////
									Tokens tokens = tokensService
											.selectByMap(token);

									// ////////////////////////////////////////////
									NodeConnection nodeConnection = new NodeConnection();
									nodeConnection.setBusinessID(node
											.getBusinessID());
									nodeConnection.setNodeID(node.getId());
									nodeConnection.setTokensID(tokens.getId());
									// 设置外网IP
									nodeConnection.setWebIp(request
											.getRemoteAddr());
									// 设置设备连接类型
									String userAgent = request
											.getHeader("user-agent");
									for (String mobile : HorizonConfig.MOBILE_SPECIFIC_SUBSTRING) {
										if (userAgent.contains(mobile)
												|| userAgent.contains(mobile
														.toUpperCase())
												|| userAgent.contains(mobile
														.toLowerCase())) {
											nodeConnection.setDevice(mobile);
										}
									}

									if (Byte.parseByte(loginType) == HorizonConfig.NODE_LOGINTYPE.WEB
											.getIndex()) {
										nodeConnection
												.setConnectType((byte) HorizonConfig.NODE_LOGINTYPE.WEB
														.getIndex());
										CommonUser commonUser = commonUserService
												.selectByMap(userName,
														node.getBusinessID());
										if (commonUser == null) {
											return;
										}
										nodeConnection.setUserID(commonUser
												.getId());
									} else if (Byte.parseByte(loginType) == HorizonConfig.NODE_LOGINTYPE.CELLPHONE
											.getIndex()) {
										nodeConnection
												.setConnectType((byte) HorizonConfig.NODE_LOGINTYPE.CELLPHONE
														.getIndex());
										if (cellPhoneNum == null
												|| "".equals(cellPhoneNum)) {
											return;
										}
										nodeConnection
												.setCellPhoneNum(cellPhoneNum);
									} else {
										return;
									}
									nodeConnection
											.setStatus((byte) NodeConnection.STATUS.INIT
													.ordinal());
									Date startTime = Calendar.getInstance(
											TimeZone.getDefault()).getTime();
									nodeConnection.setConnectStart(startTime);
									nodeConnection.setConnectEnd(startTime);
									nodeConnection.setDevice(null);
									nodeConnection.setIp(ip);
									nodeConnection.setMac(mac);
									nodeConnection.setIncoming(0);
									nodeConnection.setOutgoing(0);
									nodeConnection.setTotalIncoming(0);
									nodeConnection.setTotalOutgoing(0);
									nodeConnection.setWebIp(null);
									nodeConnection.setFreeTime(0);
									nodeConnectionService
											.insert(nodeConnection);

									response.sendRedirect("http://"
											+ gateWayAddress + ":"
											+ gateWayPort
											+ "/wifidog/auth?token=" + token);
									return;
								} else if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.DIRECTORY
											.getIndex()) {
										String token = TokensUtil
												.generateTokens();
										tokensService
												.deleteByStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_USED
														.getIndex());
										Tokens tokensInsert = new Tokens();
										tokensInsert.setToken(token);
										tokensInsert
												.setStatus((byte) HorizonConfig.TOKENS_STATUS.TOKEN_UNUSED
														.getIndex());
										tokensInsert.setNodeID(node.getId());
										tokensService.insert(tokensInsert);
										// /////////////////////////////////////////////
										Tokens tokens = tokensService
												.selectByMap(token);

										// ////////////////////////////////////////////
										NodeConnection nodeConnection = new NodeConnection();
										nodeConnection.setBusinessID(node
												.getBusinessID());
										nodeConnection.setNodeID(node.getId());
										nodeConnection.setTokensID(tokens
												.getId());
										// 设置外网IP
										nodeConnection.setWebIp(request
												.getRemoteAddr());
										// 设置设备连接类型
										String userAgent = request
												.getHeader("user-agent");
										for (String mobile : HorizonConfig.MOBILE_SPECIFIC_SUBSTRING) {
											if (userAgent.contains(mobile)
													|| userAgent
															.contains(mobile
																	.toUpperCase())
													|| userAgent
															.contains(mobile
																	.toLowerCase())) {
												nodeConnection
														.setDevice(mobile);
											}
										}

										nodeConnection
												.setConnectType((byte) HorizonConfig.NODE_LOGINTYPE.DIRECTORY
														.getIndex());
										nodeConnection
												.setStatus((byte) NodeConnection.STATUS.INIT
														.ordinal());
										Date startTime = Calendar.getInstance(
												TimeZone.getDefault())
												.getTime();
										nodeConnection
												.setConnectStart(startTime);
										nodeConnection.setConnectEnd(startTime);
										nodeConnection.setDevice(null);
										nodeConnection.setIp(ip);
										nodeConnection.setMac(mac);
										nodeConnection.setIncoming(0);
										nodeConnection.setOutgoing(0);
										nodeConnection.setTotalIncoming(0);
										nodeConnection.setTotalOutgoing(0);
										nodeConnection.setWebIp(null);
										nodeConnection.setFreeTime(0);
										nodeConnectionService
												.insert(nodeConnection);

										response.sendRedirect("http://"
												+ gateWayAddress + ":"
												+ gateWayPort
												+ "/wifidog/auth?token="
												+ token);
										return;
										/*
										 * request.setAttribute("loginType",
										 * HorizonConfig
										 * .NODE_LOGINTYPE.DIRECTORY
										 * .getIndex());
										 * request.getRequestDispatcher
										 * ("/core/login/").forward(request,
										 * response);
										 */
									} else if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.WEB
											.getIndex()) {
										/*
										 * request.setAttribute("loginType",
										 * Node.LOGINTYPE.WEB.name());
										 * request.setAttribute("nodeName",
										 * nodeName);
										 */
										/*
										 * response.sendRedirect(request.
										 * getContextPath() +
										 * "/core/login.html"); return;
										 */
										/*
										 * session.setAttribute("loginType",
										 * Node.LOGINTYPE.WEB.name());
										 * session.setAttribute("nodeName",
										 * gateWayID);
										 * request.getRequestDispatcher
										 * ("/core/login.html").forward(request,
										 * response);
										 */
										HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
										response.setContentType("text/html");
										PrintWriter out = response.getWriter();
										out.println(horizonCoreUtil
												.generateCoreClient(request,
														response, session, null));
										out.flush();
										return;
									} else if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE.getIndex()){
										HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
										response.setContentType("text/html");
										PrintWriter out = response.getWriter();
										out.println(horizonCoreUtil
												.generateCelphoneCoreClient(request,
														response, session, null));
										out.flush();
										return;
									}
							}
						}
					}
				}

			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/userlogin", method = RequestMethod.POST)
	public void userLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String gateWayID = (String) request.getParameter("gw_id");
		String loginType = (String) request.getParameter("loginType");
		if (gateWayID == null
				|| "".equals(gateWayID)
				|| loginType == null
				|| !(Byte.parseByte(loginType) == HorizonConfig.NODE_LOGINTYPE.WEB
						.getIndex())) {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCoreClient(request, response,
					session, "错误登录！"));
			out.flush();
		}
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (userName == null || "".equals(userName) || password == null
				|| "".equals(password)) {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCoreClient(request, response,
					session, "用户名或者密码不能为空！"));
			out.flush();
		}
		Node node = nodeService.selectByNodeName(gateWayID);
		if (node != null) {
			CommonUser commonUser = commonUserService.selectByMap(userName,
					node.getBusinessID());

			if (commonUser == null) {
				HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(horizonCoreUtil.generateCoreClient(request,
						response, session, "用户不存在！"));
				out.flush();
			} else {
				if (commonUser.getPassword().equals(password)) {
					if (commonUser.getNodeID() == -2) {
						login(request, response, session);
					} else {
						if (commonUser.getNodeID() != node.getId()) {
							HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
							response.setContentType("text/html");
							PrintWriter out = response.getWriter();
							out.println(horizonCoreUtil.generateCoreClient(
									request, response, session, "错误登录！"));
							out.flush();
							return;
						}
						login(request, response, session);
						/*
						 * response.sendRedirect(request.getContextPath() +
						 * "/core/login/");
						 */
					}

				} else {
					HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
					response.setContentType("text/html");
					PrintWriter out = response.getWriter();
					out.println(horizonCoreUtil.generateCoreClient(request,
							response, session, "密码错误！"));
					out.flush();
				}
			}
		} else {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCoreClient(request, response,
					session, "错误登录！"));
			out.flush();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/celphoneLogin", method = RequestMethod.POST)
	public void celphoneLogin(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String gateWayID = (String) request.getParameter("gw_id");
		String loginType = (String) request.getParameter("loginType");
		if (gateWayID == null
				|| "".equals(gateWayID)
				|| loginType == null
				|| !(Byte.parseByte(loginType) == HorizonConfig.NODE_LOGINTYPE.CELLPHONE
						.getIndex())) {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
					session, "错误登录！"));
			out.flush();
		}
		String cellPhoneNum = request.getParameter("cellPhoneNum");
		String verifyCode = request.getParameter("verifyCode");

		if (cellPhoneNum == null || "".equals(cellPhoneNum) || verifyCode == null
				|| "".equals(verifyCode)) {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
					session, "手机号或者验证码不能为空！"));
			out.flush();
		}
		Node node = nodeService.selectByNodeName(gateWayID);
		if (node != null) {
			List<SmsSecurityCode> listSmsSecurityCode = smsSecurityCodeService.listAllSmsSecurityCode(node.getBusinessID(), node.getId(), cellPhoneNum, verifyCode, (byte)-1, null, null, -1, -1);
			if (listSmsSecurityCode.size() != 0) {
				SmsSecurityCode smsSecurityCode = listSmsSecurityCode.get(0);
				if (node.getSmsType() == HorizonConfig.NODE_SMSTYPE.ONCE.getIndex()) {
					if (smsSecurityCode.getStatus() == HorizonConfig.SMSSECURITYCODE_STATUS.LOGINED.getIndex()) {
						HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
								session, "验证码已经使用过！请重新申请!"));
						out.flush();
						return;
					} else {
						long smsCodeValidTime = 0;
						byte validType = node.getSmsCodeValidTimeType();
						long validTime = node.getSmsCodeValidTime();
						if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.MINUTE.getIndex()) {
							smsCodeValidTime = validTime * 60 * 1000;
						} else if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.HOUR.getIndex()){
							smsCodeValidTime = validTime * 60 * 60 * 1000;
						} else if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.DAY.getIndex()){
							smsCodeValidTime = validTime * 24 * 60 * 60 * 1000;
						}
						long currentTime = Calendar.getInstance(TimeZone.getDefault()).getTime().getTime();
						if ((smsCodeValidTime + smsSecurityCode.getCreateTime().getTime()) >= currentTime) {
							SmsSecurityCode editSmsSecurityCode = new SmsSecurityCode();
							editSmsSecurityCode.setId(smsSecurityCode.getId());
							editSmsSecurityCode.setStatus((byte)HorizonConfig.SMSSECURITYCODE_STATUS.LOGINED.getIndex());
							smsSecurityCodeService.updateByPrimaryKeySelective(editSmsSecurityCode);
							login(request, response, session);
						} else {
							HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
							response.setContentType("text/html");
							PrintWriter out = response.getWriter();
							out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
									session, "验证码已经过期!"));
							out.flush();
						}
					}
				} else {
					long smsCodeValidTime = 0;
					byte validType = node.getSmsCodeValidTimeType();
					long validTime = node.getSmsCodeValidTime();
					if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.MINUTE.getIndex()) {
						smsCodeValidTime = validTime * 60 * 1000;
					} else if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.HOUR.getIndex()){
						smsCodeValidTime = validTime * 60 * 60 * 1000;
					} else if (validType == HorizonConfig.NODE_SMSCODEVALIDTIMETYPE.DAY.getIndex()){
						smsCodeValidTime = validTime * 24 * 60 * 60 * 1000;
					}
					long currentTime = Calendar.getInstance(TimeZone.getDefault()).getTime().getTime();
					if ((smsCodeValidTime + smsSecurityCode.getCreateTime().getTime()) >= currentTime) {
						SmsSecurityCode editSmsSecurityCode = new SmsSecurityCode();
						editSmsSecurityCode.setId(smsSecurityCode.getId());
						editSmsSecurityCode.setStatus((byte)HorizonConfig.SMSSECURITYCODE_STATUS.LOGINED.getIndex());
						smsSecurityCodeService.updateByPrimaryKeySelective(editSmsSecurityCode);
						login(request, response, session);
					} else {
						HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
						response.setContentType("text/html");
						PrintWriter out = response.getWriter();
						out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
								session, "验证码已经过期!"));
						out.flush();
					}
				}
			} else {
				HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
						session, "手机号或者验证码错误！"));
				out.flush();
			}
		} else {
			HorizonCoreUtil horizonCoreUtil = new HorizonCoreUtil();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println(horizonCoreUtil.generateCelphoneCoreClient(request, response,
					session, "错误登录！"));
			out.flush();
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/sendVerifySms", method = RequestMethod.POST)
	public Result sendVerifySms(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String gateWayID = (String) request.getParameter("gateWayID");
		String celPhone = (String) request.getParameter("cellPhoneNum");
		Result result = new Result();
		if (gateWayID == null
				|| "".equals(gateWayID)
				|| celPhone == null
				|| !("".equals(celPhone))) {
			result.setSuccess(false);
			result.setMsg("系统错误！");
		}
		Node node = nodeService.selectByNodeName(gateWayID);
		if (node != null) {
			if (node.getLoginType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE.getIndex()) {
				if (node.getBusinessID() != -1) {
					if (node.getRemainSms() > 0) {
						SimpleDateFormat sdf = new SimpleDateFormat(HorizonConfig.DATA_FORMAT);
						String date = sdf.format(new Date());
						Date currentDate = null;
						try {
							currentDate = sdf.parse(date);
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							LOG.error(e);
						}
						byte mobileType = 4; 
						try {
							MobileLocation mobileLocation = MobileLocationUtil.getMobileLocation2(celPhone);
							if (mobileLocation.isSuccess()) {
								if (mobileLocation.getSupplier().equalsIgnoreCase(HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_MOBILE.getName())) {
									mobileType = (byte)HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_MOBILE.getIndex();
								} else if (mobileLocation.getSupplier().equalsIgnoreCase(HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_UNICOM.getName())) {
									mobileType = (byte)HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_UNICOM.getIndex();
								} else if (mobileLocation.getSupplier().equalsIgnoreCase(HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_NET.getName())) {
									mobileType = (byte)HorizonConfig.SMSSECURITYCODE_MOBILE_TYPE.CHINA_NET.getIndex();
								} 
							} else {
								if (mobileLocation.getErrer().equalsIgnoreCase(celPhone+ "：手机号码格式错误！")) {
									result.setSuccess(false);
									result.setMsg("手机号码格式错误！");
									return result;
								}
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							LOG.error(e);
						}
						List<SmsSecurityCode> listSmsSecurityCode = smsSecurityCodeService.listAllSmsSecurityCode(node.getBusinessID(), node.getId(), celPhone, null, (byte)-1, currentDate, currentDate, -1, -1);
						if (listSmsSecurityCode.size() >= node.getSmsCodeDayNum()) {
							result.setSuccess(false);
							result.setMsg("验证码申请次数过多！每天只能申请" + node.getSmsCodeDayNum() + "次");
						} else {
							if (listSmsSecurityCode.size() != 0) {
								long lastSendSmsTime = listSmsSecurityCode.get(0).getCreateTime().getTime();
								long currentTime = Calendar.getInstance(TimeZone.getDefault()).getTime().getTime();
								
								if ((currentTime - lastSendSmsTime) > (node.getSmsCodeObtainInterval() * 60 * 1000)) {
									String smsVerifyCode = CommonUtils.generateVerifyCode(node.getSmsCodeLength());
									//插入短信验证信息
									PropertiesTool propertiesTool = new PropertiesTool();
									propertiesTool.loadFile("horizon.properties", "UTF-8");
									String uid = propertiesTool.getString("horizon.chinese.sms.uid");
									String key = propertiesTool.getString("horizon.chinese.sms.key");
									String content = smsContentService.selectByMap(node.getSmsContentID()).getSmsContent();
									content += " 验证码[" + smsVerifyCode + "]";
									ChineseSmsUtils chineseSysUtils = new ChineseSmsUtils();
									long sendResult = chineseSysUtils.sendVerifyCodeSms(uid, key, celPhone, content);
									if (sendResult > 0) {
										SmsSecurityCode smsSecurityCode = new SmsSecurityCode(node.getBusinessID(), node.getId(), celPhone, smsVerifyCode,(byte)HorizonConfig.SMSSECURITYCODE_STATUS.NOLOGIN.getIndex(), mobileType);
										smsSecurityCodeService.insert(smsSecurityCode);
										Node editNode = new Node();
										editNode.setId(node.getId());
										editNode.setRemainSms(node.getRemainSms() - 1);
										nodeService.updateByPrimaryKeySelective(editNode);
										result.setSuccess(true);
									} else if (sendResult == -3) {
										result.setSuccess(false);
										result.setMsg("系统短信不足，请联系管理员");
									} else if (sendResult == -4) {
										result.setSuccess(false);
										result.setMsg("手机号格式错误");
									}
									
								} else {
									result.setSuccess(false);
									result.setMsg("验证码申请间隔时间" + node.getSmsCodeObtainInterval() + "分钟");
								}
							} else {
								String smsVerifyCode = CommonUtils.generateVerifyCode(node.getSmsCodeLength());
								PropertiesTool propertiesTool = new PropertiesTool();
								propertiesTool.loadFile("horizon.properties", "UTF-8");
								String uid = propertiesTool.getString("horizon.chinese.sms.uid");
								String key = propertiesTool.getString("horizon.chinese.sms.key");
								String content = smsContentService.selectByMap(node.getSmsContentID()).getSmsContent();
								content += " 验证码[" + smsVerifyCode + "]";
								ChineseSmsUtils chineseSysUtils = new ChineseSmsUtils();
								long sendResult = chineseSysUtils.sendVerifyCodeSms(uid, key, celPhone, content);
								if (sendResult > 0) {
									SmsSecurityCode smsSecurityCode = new SmsSecurityCode(node.getBusinessID(), node.getId(), celPhone, smsVerifyCode,(byte)HorizonConfig.SMSSECURITYCODE_STATUS.NOLOGIN.getIndex(), mobileType);
									smsSecurityCodeService.insert(smsSecurityCode);
									Node editNode = new Node();
									editNode.setId(node.getId());
									editNode.setRemainSms(node.getRemainSms() - 1);
									nodeService.updateByPrimaryKeySelective(editNode);
									result.setSuccess(true);
								} else if (sendResult == -3) {
									result.setSuccess(false);
									result.setMsg("系统短信不足，请联系管理员");
								} else if (sendResult == -4) {
									result.setSuccess(false);
									result.setMsg("手机号格式错误");
								}
							}
						}
						
					} else {
						result.setSuccess(false);
						result.setMsg("系统短信不足！");
					}
				} else {
					result.setSuccess(false);
					result.setMsg("路由还未绑定！");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("系统登录方式不是手机验证码登录！");
			}

		} else {
			result.setSuccess(false);
			result.setMsg("系统错误！");
		}
		return result;
	}

	@ResponseBody
	@RequestMapping(value = "/portal/", method = RequestMethod.GET)
	public void portal(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Enumeration keys = request.getParameterNames();
		System.out.println("portal get parameter scan start!");
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + "\t:\t" + request.getParameter(key));
		}
		System.out.println("parameter scan end!");

		String nodeName = request.getParameter("gw_id");
		String token = request.getParameter("token");

		if (token != null && !"".equals(token) && nodeName != null
				&& !"".equals(nodeName)) {
			Tokens tokens = tokensService.selectByMap(token);
			if (tokens != null) {
				NodeConnection nodeConnection = nodeConnectionService
						.selectByMap(tokens.getId(),
								(byte) NodeConnection.STATUS.ONLINE.ordinal());
				if (nodeConnection != null) {
					Node node = nodeService.selectByPrimaryKey(nodeConnection
							.getNodeID());
					if (node != null && node.getNdName().equals(nodeName)) {
						if (node.getPortalUrl() != null) {
							response.sendRedirect(node.getPortalUrl());
						} else {
							response.sendRedirect((String) session
									.getAttribute("originURL"));
						}
					}

				}

			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/ping/", method = RequestMethod.GET)
	public void ping(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws ServletException, IOException {
		Enumeration keys = request.getParameterNames();
		System.out.println("ping get parameter scan start!");
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + "\t:\t" + request.getParameter(key));
		}
		System.out.println("parameter scan end!");

		String nodeName = request.getParameter("gw_id");
		String sysUptime = request.getParameter("sys_uptime");
		String sysMemfree = request.getParameter("sys_memfree");
		String sysLoad = request.getParameter("sys_load");
		String wifidogUptime = request.getParameter("wifidog_uptime");
		String mcode = request.getParameter("mcode");
		String wifidog = request.getParameter("wifiap");

		if (mcode != null && !"".endsWith(mcode)) {
			Node node = nodeService.selectByMCode(mcode);
			/*
			 * if (node != null && node.getNdName().equals(nodeName)) {
			 * session.setAttribute("mcode", mcode); }
			 */
			if (node != null && node.getNdName().equals(nodeName)) {
				if (node.getNodeStatus() == Node.NODESTATUS.NORMAL.ordinal()) {
					Node editNode = new Node();
					editNode.setId(node.getId());
					editNode.setRunning((byte) HorizonConfig.NODE_RUNNING.RUNNING
							.getIndex());
					nodeService.updateByPrimaryKeySelective(editNode);
					response.getWriter().write("Pong");
					response.flushBuffer();

					if (sysUptime != null && sysMemfree != null
							&& sysLoad != null && wifidogUptime != null) {
						RouteRecord routeRecord = new RouteRecord(node.getId(),
								Long.parseLong(sysUptime),
								Long.parseLong(sysMemfree),
								Float.parseFloat(sysLoad),
								Long.parseLong(wifidogUptime));
						routeRecordService.insert(routeRecord);
					}

					List<RouteStatus> listRouteStatus = routeStatusService
							.listAllRouteStatus(node.getId(), -1, -1);
					PropertiesTool propertiesTool = new PropertiesTool();
					propertiesTool.loadFile("horizon.properties", "UTF-8");
					int initRouteStatusRecordNum = propertiesTool
							.getInteger("horizon.route.status.record.num");
					String webIp = request.getRemoteAddr();
					if (listRouteStatus.size() < initRouteStatusRecordNum) {
						if (listRouteStatus.size() == 0) {
							RouteStatus routeStatus = new RouteStatus(
									node.getId(), webIp);
							routeStatusService.insert(routeStatus);
						} else {
							if (!listRouteStatus.get(0).getWebIp()
									.equalsIgnoreCase(webIp)) {
								RouteStatus routeStatus = new RouteStatus(
										node.getId(), webIp);
								routeStatusService.insert(routeStatus);
							}
							/*
							 * else { RouteStatus routeStatus = new
							 * RouteStatus();
							 * routeStatus.setId(listRouteStatus.get
							 * (0).getId());
							 * routeStatus.setCreateTime(Calendar.getInstance
							 * (TimeZone.getDefault()).getTime());
							 * routeStatusService
							 * .updateByPrimaryKeySelective(routeStatus); }
							 */
						}

					} else {
						if (listRouteStatus.size() != initRouteStatusRecordNum) {
							LOG.error("update route status record error!");
						} else {
							long sumUpdateTime = 0L;
							for (int i = 0; i < (listRouteStatus.size() - 1); ++i) {
								sumUpdateTime += (listRouteStatus.get(i)
										.getCreateTime().getTime() - listRouteStatus
										.get(i + 1).getCreateTime().getTime());
							}

							long avgUpdateTime = sumUpdateTime
									/ (listRouteStatus.size() - 1);
							long limitUpdateTime = propertiesTool
									.getLong("horizon.route.status.record.limit.update.time");
							if (avgUpdateTime < (limitUpdateTime * 60 * 1000)) {
								Node updateNode = new Node();
								updateNode.setId(node.getId());
								updateNode
										.setNodeStatus((byte) Node.NODESTATUS.LIMIT
												.ordinal());
								nodeService
										.updateByPrimaryKeySelective(updateNode);
							}
							if (!listRouteStatus.get(0).getWebIp()
									.equalsIgnoreCase(webIp)) {
								routeStatusService
										.deleteByPrimaryKey(listRouteStatus
												.get(listRouteStatus.size() - 1)
												.getId());
								RouteStatus routeStatus = new RouteStatus(
										node.getId(), webIp);
								routeStatusService.insert(routeStatus);
							}
						}
					}
				} else {
					LOG.error("Route connect error!\tmcode:'" + mcode
							+ "'\tgate way id:'" + nodeName
							+ "' is limit on status!");
				}
			} else {
				LOG.error("Route connect error!\tmcode:'" + mcode
						+ "'\tgate way id:'" + nodeName + "' is fault!");
			}
		}
	}

	@ResponseBody
	@RequestMapping(value = "/logout/", method = RequestMethod.GET)
	public void logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		Enumeration keys = request.getParameterNames();
		System.out.println("logout get parameter scan start!");
		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			System.out.println(key + "\t:\t" + request.getParameter(key));
		}
		String nodeConnectionID = request.getParameter("nodeConnectionID");
		if (nodeConnectionID != null && !"".equals(nodeConnectionID)) {
			NodeConnection nodeConnection = nodeConnectionService
					.selectByMap(Long.parseLong(nodeConnectionID));
			NodeConnection editNodeConnection = new NodeConnection();
			editNodeConnection.setId(nodeConnection.getId());
			editNodeConnection.setStatus((byte) NodeConnection.STATUS.OFFLINE
					.ordinal());
			editNodeConnection
					.setInterrupReason((byte) HorizonConfig.HORIZON_ERROR.ERRER13
							.ordinal());
			nodeConnectionService
					.updateByPrimaryKeySelective(editNodeConnection);
		}

		System.out.println("parameter scan end!");
	}
}
