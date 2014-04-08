package com.kechuang.wifidog.horizon.service.impl;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kechuang.wifidog.horizon.model.CommonUser;
import com.kechuang.wifidog.horizon.model.Node;
import com.kechuang.wifidog.horizon.model.NodeConnection;
import com.kechuang.wifidog.horizon.service.CommonUserService;
import com.kechuang.wifidog.horizon.service.CoreUtilService;
import com.kechuang.wifidog.horizon.service.NodeConnectionService;
import com.kechuang.wifidog.horizon.service.NodeService;
import com.kechuang.wifidog.horizon.utils.HorizonConfig;

@Service("com.kechuang.wifidog.horizon.service.impl.CoreUtilService")
public class CoreUtilServiceImpl implements CoreUtilService {

	private static final Logger LOG = Logger
			.getLogger(CoreUtilServiceImpl.class);

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.CommonUserService")
	private CommonUserService commonUserService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeConnectionService")
	private NodeConnectionService nodeConnectionService;

	@Autowired
	@Qualifier("com.kechuang.wifidog.horizon.service.impl.NodeService")
	private NodeService nodeService;

	/**
	 * 这里判断此连接是否未可靠连接的条件 a.用户名/密码方式登录 1.是否是一次性用户 2.是否是多终端登录
	 * 1)首先判断是否是普通用户类型的多终端类型 2)如果前面一个条件不成立则判断热点是否是多终端类型 b.手机验证码登录
	 * 1.判断热点是否是多终端类型，如果否，则进行判断
	 */
	public boolean checkConnectOne(NodeConnection nodeConnection, NodeConnection editNodeConnection, Node node) {
		// TODO Auto-generated method stub

		if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.WEB.getIndex()) {
			CommonUser commonUser = commonUserService
					.selectByMap(nodeConnection.getUserID());
			CommonUser editCommonUser = new CommonUser();
			if (commonUser.getUserType() == HorizonConfig.COMMONUSER_USERTYPE.ONCEUSER
					.getIndex()) {
				if (commonUser.getIsLogined() == HorizonConfig.COMMONUSER_ISLOGINED.LOGINED
						.getIndex()) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER2.getIndex());
					return false;
				}
			}

			if (commonUser.getIsLogined() == HorizonConfig.COMMONUSER_ISLOGINED.UNLOGINED
					.getIndex()) {
				editCommonUser
						.setIsLogined((byte) HorizonConfig.COMMONUSER_ISLOGINED.LOGINED
								.getIndex());
			}

			if (commonUser.getMultiTerminalLogin() == HorizonConfig.COMMONUSER_MULTITERMINALLOGIN.FORBID
					.getIndex()) {
				List<NodeConnection> listNodeConnection = nodeConnectionService
						.listAllConnections(nodeConnection.getNodeID(),
								(byte) NodeConnection.STATUS.ONLINE.ordinal(),
								nodeConnection.getUserID(), null, null,
								nodeConnection.getBusinessID(), 1, 0);
				if (listNodeConnection.size() != 0) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER3.getIndex());
					return false;
				}
			} else if (commonUser.getMultiTerminalLogin() == HorizonConfig.COMMONUSER_MULTITERMINALLOGIN.NOSET
					.getIndex()) {
				if (node.getMultiTerminalLogin() == Node.MULTITERMINALLOGIN.NO
						.ordinal()) {
					List<NodeConnection> listNodeConnection = nodeConnectionService
							.listAllConnections(nodeConnection.getNodeID(),
									(byte) NodeConnection.STATUS.ONLINE
											.ordinal(), nodeConnection
											.getUserID(), null, null,
									nodeConnection.getBusinessID(), 1, 0);
					if (listNodeConnection.size() != 0) {
						editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER4.getIndex());
						return false;
					}
				}
			}
		} else if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.CELLPHONE
				.getIndex()) {
			if (node.getMultiTerminalLogin() == Node.MULTITERMINALLOGIN.NO
					.ordinal()) {
				List<NodeConnection> listNodeConnection = nodeConnectionService
						.listAllConnections(nodeConnection.getNodeID(),
								(byte) NodeConnection.STATUS.ONLINE.ordinal(),
								nodeConnection.getCellPhoneNum(),
								nodeConnection.getBusinessID());
				if (listNodeConnection.size() != 0) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER4.getIndex());
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断此连接是否是可靠的连接
	 * 1.判断是否在开放时间之内
	 * 2.判断是否在热点强制断开时间之外
	 * 3.判断是否在热点强制断开空闲时间之外
	 * 4.判断是否在热点总的下载和上传流量之外
	 * 5.判断是否在热点针对单个连接设置的下载和上传之外
	 * 6.判断热点的状态是否正常
	 * 7.判断热点是否正在运行
	 * 8.判断普通用户状态
	 * 9.判断普通用户有效时间
	 * 10.判断普通用户可用时间
	 */
	public boolean checkConnectTwo(NodeConnection nodeConnection, NodeConnection editNodeConnection, Node node) {
		// TODO Auto-generated method stub
		Time startTime = node.getStartTime();
		Time endTime = node.getEndTime();

		long systemCurrentMillis = System.currentTimeMillis();
		Time currentTime = new Time(systemCurrentMillis);
		
		if (node.getNodeStatus() == Node.NODESTATUS.LIMIT.ordinal()) {
			editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER14.getIndex());
			return false;
		}
		
		if (node.getRunning() == HorizonConfig.NODE_RUNNING.STOP.getIndex()) {
			editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER15.getIndex());
			return false;
		}
		
		if (nodeConnection.getConnectType() == HorizonConfig.NODE_LOGINTYPE.WEB.getIndex()) {
			CommonUser commonUser = commonUserService.selectByMap(nodeConnection.getUserID());
			if (commonUser == null) {
				editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER1.getIndex());
				return false;
			} else {
				if (commonUser.getUserStatus() == HorizonConfig.COMMONUSER_STATUS.LIMIT.getIndex()) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER16.getIndex());
					return false;
				}
				
				Date currentDate= new Date(systemCurrentMillis);
				if (commonUser.getValidTime().before(currentDate)) {
					List<NodeConnection> listNodeConnection = nodeConnectionService.listAllConnections(-1, (byte)-1, commonUser.getId(), null, new Date(systemCurrentMillis), commonUser.getUserID(), -1, -1);
					if (listNodeConnection != null && listNodeConnection.size() != 0) {
						long sumUsableTime = 0;
						for (NodeConnection ndConnection : listNodeConnection) {
							if (ndConnection.getConnectEnd().after(currentDate)) {
								sumUsableTime += ndConnection.getConnectEnd().getTime() - systemCurrentMillis;
							}
						}
						
						if (sumUsableTime >= commonUser.getUsableTime()) {
							editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER17.getIndex());
							return false;
						}
					}
				}
			}
		}
		
		if (startTime.toString().compareTo(currentTime.toString()) < 0
				&& endTime.toString().compareTo(currentTime.toString()) > 0) {
			Date connectStart = nodeConnection.getConnectStart();
			Date connectEnd = nodeConnection.getConnectEnd();
			if (node.getTurnOffTime() != HorizonConfig.NODE_DEFAULT_TURNOFFTIME) {
				if ((connectEnd.getTime() - connectStart.getTime()) > node.getTurnOffTime()) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER5.getIndex());
					return false;
				}
			}

			if (node.getTurnOffTime() != HorizonConfig.NODE_DEFAULT_TURNOFFFREETIME) {
				if (nodeConnection.getFreeTime() > node.getTurnOffFreeTime()) {
					editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER6.getIndex());
					return false;
				}
			}
			
			if (node.getLimitSpeed() == Node.LIMITSPEED.YES.ordinal()) {
				if (node.getTotalLimitIncoming() != HorizonConfig.NODE_DEFAULT_TOTALLIMITINCOMING || node.getTotalLimitOutgoing() != HorizonConfig.NODE_DEFAULT_TOTALLIMITOUTGOING) {
					List<Long> totalInOut = nodeConnectionService.getTotalSum(nodeConnection.getNodeID(), (byte)-1, -1, node.getUpdateTime(), new Date(systemCurrentMillis), nodeConnection.getBusinessID());
					if (node.getTotalLimitIncoming() != HorizonConfig.NODE_DEFAULT_TOTALLIMITINCOMING) {
						if (totalInOut.size() != 0) {
							if (totalInOut.get(0) > node.getTotalLimitIncoming()) {
								editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER7.getIndex());
								return false;
							}
							
						}
					}
					
					if (node.getTotalLimitOutgoing() != HorizonConfig.NODE_DEFAULT_TOTALLIMITOUTGOING) {
						if (totalInOut.size() != 0) {
							if (totalInOut.get(1) > node.getTotalLimitOutgoing()) {
								editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER8.getIndex());
								return false;
							}
							
						}
					}
					
					if (node.getEachLimitIncoming() != HorizonConfig.NODE_DEFAULT_EACHLIMITINCOMING) {
						if (nodeConnection.getTotalIncoming() > node.getEachLimitIncoming()) {
							editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER9.getIndex());
							return false;
						}
					}
					
					if (node.getEachLimitOutgoing() != HorizonConfig.NODE_DEFAULT_EACHLIMITOUTGOING) {
						if (nodeConnection.getTotalOutgoing() > node.getEachLimitOutgoing()) {
							editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER10.getIndex());
							return false;
						}
					}
				}
				
			}
			return true;
		} else {
			editNodeConnection.setInterrupReason((byte)HorizonConfig.HORIZON_ERROR.ERRER11.getIndex());
			return false;
		}
	}

}
