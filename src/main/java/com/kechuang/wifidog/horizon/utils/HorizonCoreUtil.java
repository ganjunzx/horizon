package com.kechuang.wifidog.horizon.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kechuang.wifidog.horizon.model.Node;

public class HorizonCoreUtil {
	public String generateCoreClient(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String message) {
		String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
		content += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
		content += "<head>\n";

		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n";
		content += "<title>登录页</title>\n";

		content += "<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/themes/bootstrap/easyui.css\" type=\"text/css\"/>n";
		content += "<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/themes/icon.css\" type=\"text/css\"/>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-2.0.0.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/extEasyUI.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/hms.js\" charset=\"utf-8\"></script>\n";

		content += "<style>\n * \n{padding: 0px;\nmargin: 0px;}\n";
		content += "body {\nfont-family: Arial, Helvetica, sans-serif;\nbackground: url(" + request.getContextPath() + "/images/grass.jpg);\nfont-size: 13px;\n}\n";
		content += "img {\nborder: 0;\n}\n";
		content += ".lg {\nwidth: 468px;\nheight: 468px;\nmargin: 100px auto;\nbackground: url(" + request.getContextPath() + "/images/login_bg.png) no-repeat;\n}\n";
		content += ".lg_top {\nheight: 200px;\nwidth: 468px;\n}\n";
		content += ".lg_main {\nwidth: 400px;\nheight: 180px;\nmargin: 0 25px;\n}\n";
		content += ".lg_m_1 {\nwidth: 290px;\nheight: 100px;\npadding: 60px 55px 20px 55px;\n}\n";
		content += ".ur {\nheight: 37px;\nborder: 0;\ncolor: #666;\nwidth: 236px;\nmargin: 4px 28px;\nbackground: url(" + request.getContextPath() + "/images/user.png) no-repeat;\npadding-left: 10px;\nfont-size: 16pt;\nfont-family: Arial, Helvetica, sans-serif;\n}\n";
		content += ".pw {\nheight: 37px;\nborder: 0;\ncolor: #666;\nwidth: 236px;\nmargin: 4px 28px;\nbackground: url(" + request.getContextPath() + "/images/password.png) no-repeat;\npadding-left: 10px;\nfont-size: 16pt;\nfont-family: Arial, Helvetica, sans-serif;\n}\n";
		content += ".bn {\nwidth: 330px;\nheight: 72px;\nbackground: url(" + request.getContextPath() + "/images/enter.png) no-repeat;\nborder: 0;\ndisplay: block;\nfont-size: 18px;\ncolor: #FFF;\nfont-family: Arial, Helvetica, sans-serif;\nfont-weight: bolder;\n}\n";
		content += ".lg_foot {\nheight: 80px;\nwidth: 330px;\npadding: 6px 68px 0 68px;\n}\n";
		content += "</style>\n";

		content += "<script type=\"text/javascript\">\n";
		content += "function checkLogin(data) {// 检查是否登录超时\nif (data.logoutFlag) {\n\tcloseProgress();\n\talert('提示', \"登录超时,点击确定重新登录.\", 'error', login);\n\treturn false;\n\t}\n\treturn true;\n}\n";
		content += "function progress(title, msg) {\n\tvar win = $.messager.progress({\n\ttitle : title || 'Please waiting',\n\tmsg : msg || 'Loading data...'\n\t});\n}\n\n\nfunction closeProgress() {\n\t$.messager.progress('close');\n\t}\n";

	/*	content += "function login() {\n\t";
		content += "try {\n\t";
		content += "$('#errorTips').html('');\n\t";
		content += "if ($(\"#userLogin\").form('validate')) {\n\t";
		content += "progress('Please waiting', 'Loading...');\n\t";
		content += "$(\"#userLogin\").form('submit', {\n\t";
		content += "url : getBasePath() + '/core/userlogin',\n\t";
		content += "success : function(data) {\n\t";
		content += "closeProgress();\n\t";
		content += "var result = $.parseJSON(data);\n\t";
		content += "if (result.success == true) {\n\t";
		content += "window.location = \"" + request.getContextPath() + "/innerIndex.html\";\n\t";
		content += "} else {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + result.msg + '</font>');\n\t";
		content += "}\n\t";
		content += "},\n\t";
		content += "error : function(response, textStatus, errorThrown) {\n\t";
		content += "try {\n\t";
		content += "closeProgress();\n\t";
		content += "var data = $.parseJSON(response.responseText);\n\t";
		content += "if (!checkLogin(data)) {\n\t";
		content += "return false;\n\t";
		content += "} else {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + (data.msg || \"请求出现异常,请联系管理员\")+ '</font>');\n\t";
		content += "}\n\t";
		content += "} catch (e) {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">请求出现异常,请联系管理员.</font>');\n\t";
		content += "}\n\t";
		content += "}\n\t";
		content += "});\n\t";
		content += "}\n\t";
		content += "} catch (e) {\n\t";
		content += "}\n\t";
		content += "return false;\n\t";
		content += "}\n\t";*/
/*		content += "$(function() {\n\t";
		content += "String msg = getUrlParam('msg');\n\t";
		content += "if (msg != null && msg != '') {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + msg + '</font>');\n\t";
		content += "}\n\t";
		content += "}\n\t";*/
		
		content += "</script>\n";
		content += "</head>\n";
		content += "<body class=\"b\">\n\t";
		content += "<div class=\"lg\">\n\t";
		
		content += "<form id=\"userLogin\" action=\"" + request.getContextPath() + "/core/userlogin" + "\" method=\"post\">\n\t";
		content += "<div class=\"lg_top\"></div>\n\t";
		content += "<div class=\"lg_main\">\n\t";
		content += "<div class=\"lg_m_1\">\n\t";
		
		String gateWayAddress = request.getParameter("gw_address");
		String gateWayPort = request.getParameter("gw_port");
		String gateWayID = request.getParameter("gw_id");
		String ip = request.getParameter("ip");
		String mac = request.getParameter("mac");
		String key = request.getParameter("key");
		String wwver = request.getParameter("wwver");
		String onlineUser = request.getParameter("onlineuser");
		String url = request.getParameter("url");

		
		content += "<input name=\"gw_address\" value=\"" + gateWayAddress + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"gw_port\" value=\"" + gateWayPort + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"gw_id\" value=\"" + gateWayID + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"ip\" value=\"" + ip + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"mac\" value=\"" + mac + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"key\" value=\"" + key + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"wwver\" value=\"" + wwver + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"onlineuser\" value=\"" + onlineUser + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"url\" value=\"" + url + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"loginType\" value=\"" + HorizonConfig.NODE_LOGINTYPE.WEB.getIndex() + "\" type=\"hidden\"/> \n\t";
		
		
		content += "<input name=\"userName\" value=\"\" class=\"ur easyui-validatebox\" data-options=\"required:true,missingMessage:'用户名不能为空.'\"/> \n\t";
		content += "<input name=\"password\" type=\"password\" class=\"pw easyui-validatebox\" data-options=\"required:true,missingMessage:'密码不能为空.'\"/>\n\t";
		content += "<span id=\"errorTips\" style=\"margin-left: 80px;height: 100px\">";
		if (message != null && !"".equals(message)) {
			content += "<font color=\"red\">" + message + "</font></span>\n\t";
		} else {
			content += "</span>\n\t";
		}
		content += "</div>\n\t";
		content += "</div>\n\t";
		content += "<div class=\"lg_foot\">\n\t";
		content += "<input type=\"submit\" value=\"Login In\" class=\"bn\"/>\n\t";
		content += "</div>\n\t";
		content += "</form>\n\t";
		content += "</div>\n\t";
		content += "</body>\n\t";
		content += "</html>\n\t";
		
		return content;
	}
	
	public String generateCelphoneCoreClient(HttpServletRequest request,
			HttpServletResponse response, HttpSession session, String message) {
		String content = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
		content += "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n";
		content += "<head>\n";

		content += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n";
		content += "<title>登录页</title>\n";

		content += "<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/themes/bootstrap/easyui.css\" type=\"text/css\"/>n";
		content += "<link id=\"easyuiTheme\" rel=\"stylesheet\" href=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/themes/icon.css\" type=\"text/css\"/>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-2.0.0.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/jquery.easyui.min.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/extEasyUI.js\" charset=\"utf-8\"></script>\n";
		content += "<script type=\"text/javascript\" src=\""
				+ request.getContextPath()
				+ "/jslib/hms.js\" charset=\"utf-8\"></script>\n";

		content += "<style>\n * \n{padding: 0px;\nmargin: 0px;}\n";
		content += "body {\nfont-family: Arial, Helvetica, sans-serif;\nbackground: url(" + request.getContextPath() + "/images/grass.jpg);\nfont-size: 13px;\n}\n";
		content += "img {\nborder: 0;\n}\n";
		content += ".lg {\nwidth: 468px;\nheight: 468px;\nmargin: 100px auto;\nbackground: url(" + request.getContextPath() + "/images/login_bg.png) no-repeat;\n}\n";
		content += ".lg_top {\nheight: 200px;\nwidth: 468px;\n}\n";
		content += ".lg_main {\nwidth: 400px;\nheight: 180px;\nmargin: 0 25px;\n}\n";
		content += ".lg_m_1 {\nwidth: 290px;\nheight: 100px;\npadding: 60px 55px 20px 55px;\n}\n";
		content += ".ur {\nheight: 37px;\nborder: 0;\ncolor: #666;\nwidth: 236px;\nmargin: 4px 28px;\nbackground: url(" + request.getContextPath() + "/images/user.png) no-repeat;\npadding-left: 10px;\nfont-size: 16pt;\nfont-family: Arial, Helvetica, sans-serif;\n}\n";
		content += ".pw {\nheight: 37px;\nborder: 0;\ncolor: #666;\nwidth: 236px;\nmargin: 4px 28px;\nbackground: url(" + request.getContextPath() + "/images/password.png) no-repeat;\npadding-left: 10px;\nfont-size: 16pt;\nfont-family: Arial, Helvetica, sans-serif;\n}\n";
		content += ".bn {\nwidth: 330px;\nheight: 72px;\nbackground: url(" + request.getContextPath() + "/images/enter.png) no-repeat;\nborder: 0;\ndisplay: block;\nfont-size: 18px;\ncolor: #FFF;\nfont-family: Arial, Helvetica, sans-serif;\nfont-weight: bolder;\n}\n";
		content += ".lg_foot {\nheight: 80px;\nwidth: 330px;\npadding: 6px 68px 0 68px;\n}\n";
		content += "</style>\n";

		content += "<script type=\"text/javascript\">\n";
		content += "function checkLogin(data) {// 检查是否登录超时\nif (data.logoutFlag) {\n\tcloseProgress();\n\talert('提示', \"登录超时,点击确定重新登录.\", 'error', login);\n\treturn false;\n\t}\n\treturn true;\n}\n";
		content += "function progress(title, msg) {\n\tvar win = $.messager.progress({\n\ttitle : title || 'Please waiting',\n\tmsg : msg || 'Loading data...'\n\t});\n}\n\n\nfunction closeProgress() {\n\t$.messager.progress('close');\n\t}\n";

	/*	content += "function login() {\n\t";
		content += "try {\n\t";
		content += "$('#errorTips').html('');\n\t";
		content += "if ($(\"#userLogin\").form('validate')) {\n\t";
		content += "progress('Please waiting', 'Loading...');\n\t";
		content += "$(\"#userLogin\").form('submit', {\n\t";
		content += "url : getBasePath() + '/core/userlogin',\n\t";
		content += "success : function(data) {\n\t";
		content += "closeProgress();\n\t";
		content += "var result = $.parseJSON(data);\n\t";
		content += "if (result.success == true) {\n\t";
		content += "window.location = \"" + request.getContextPath() + "/innerIndex.html\";\n\t";
		content += "} else {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + result.msg + '</font>');\n\t";
		content += "}\n\t";
		content += "},\n\t";
		content += "error : function(response, textStatus, errorThrown) {\n\t";
		content += "try {\n\t";
		content += "closeProgress();\n\t";
		content += "var data = $.parseJSON(response.responseText);\n\t";
		content += "if (!checkLogin(data)) {\n\t";
		content += "return false;\n\t";
		content += "} else {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + (data.msg || \"请求出现异常,请联系管理员\")+ '</font>');\n\t";
		content += "}\n\t";
		content += "} catch (e) {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">请求出现异常,请联系管理员.</font>');\n\t";
		content += "}\n\t";
		content += "}\n\t";
		content += "});\n\t";
		content += "}\n\t";
		content += "} catch (e) {\n\t";
		content += "}\n\t";
		content += "return false;\n\t";
		content += "}\n\t";*/
/*		content += "$(function() {\n\t";
		content += "String msg = getUrlParam('msg');\n\t";
		content += "if (msg != null && msg != '') {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + msg + '</font>');\n\t";
		content += "}\n\t";
		content += "}\n\t";*/
		
		content += "function sendVerifySms() {\n\t";
		content += "$('#errorTips').html('');\n\t";
		content += "var gateWayID = $('#gateWayID').val();\n\t";
		content += "var cellPhoneNum = $('#cellPhoneNum').val();\n\t";
		content += "if (gateWayID == null || gateWayID == '') {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">系统错误</font>');\n\t";
		content += "return;\n\t";
		content += "}\n\t";
		content += "if (cellPhoneNum == null || cellPhoneNum == '') {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">手机号不能为空</font>');\n\t";
		content += "return;\n\t";
		content += "}\n\t";
		content += "\n\t";
		content += "\n\t";
		content += "$.ajax({\n\t";
		content += "type : \"POST\",\n\t";
		content += "url : getBasePath() + \"/core/sendVerifySms?gateWayID=\" + gateWayID + \"&cellPhoneNum=\" + cellPhoneNum,\n\t";
		content += "success : function(data) {\n\t";
		content += "var result = $.parseJSON(data);\n\t";
		content += "if (result.success == false) {\n\t";
		content += "$('#errorTips').html('<font color=\"red\">' + result.msg + '</font>');\n\t";
		content += "} else {\n\t";
		content += "alert('验证码已发送!');\n\t";
		content += "}\n\t";
		content += "}\n\t";
		content += "});\n\t";
		content += "}\n\t";
		content += "\n\t";
		content += "\n\t";
		
		content += "</script>\n";
		content += "</head>\n";
		content += "<body class=\"b\">\n\t";
		content += "<div class=\"lg\">\n\t";
		
		content += "<form id=\"userLogin\" action=\"" + request.getContextPath() + "/core/celphoneLogin" + "\" method=\"post\">\n\t";
		content += "<div class=\"lg_top\"></div>\n\t";
		content += "<div class=\"lg_main\">\n\t";
		content += "<div class=\"lg_m_1\">\n\t";
		
		String gateWayAddress = request.getParameter("gw_address");
		String gateWayPort = request.getParameter("gw_port");
		String gateWayID = request.getParameter("gw_id");
		String ip = request.getParameter("ip");
		String mac = request.getParameter("mac");
		String key = request.getParameter("key");
		String wwver = request.getParameter("wwver");
		String onlineUser = request.getParameter("onlineuser");
		String url = request.getParameter("url");

		
		content += "<input name=\"gw_address\" value=\"" + gateWayAddress + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"gw_port\" value=\"" + gateWayPort + "\" type=\"hidden\"/> \n\t";
		content += "<input id=\"gateWayID\" name=\"gw_id\" value=\"" + gateWayID + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"ip\" value=\"" + ip + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"mac\" value=\"" + mac + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"key\" value=\"" + key + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"wwver\" value=\"" + wwver + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"onlineuser\" value=\"" + onlineUser + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"url\" value=\"" + url + "\" type=\"hidden\"/> \n\t";
		content += "<input name=\"loginType\" value=\"" + HorizonConfig.NODE_LOGINTYPE.CELLPHONE.getIndex() + "\" type=\"hidden\"/> \n\t";
		
		content += "<div style=\"margin-left:-10px\">\n\t";
		content += "<label>手机号:&nbsp;&nbsp;</label><input id=\"cellPhoneNum\" name=\"cellPhoneNum\" value=\"\" class=\"easyui-validatebox\" data-options=\"required:true,missingMessage:'手机号不能为空.'\"/> <button type=\"button\" class=\"btn btn-default\" style=\"text-align:center; margin-left:240px; margin-top:-25px;\" onClick=\"sendVerifySms()\">获取验证码</button><br/> \n\t";
		content += "</div>\n\t";
		content += "<div style=\"margin-left:-10px\">\n\t";
		content += "<label >验证码:&nbsp;&nbsp;</label><input name=\"verifyCode\" value=\"\" class=\"easyui-validatebox\" data-options=\"required:true,missingMessage:'验证码不能为空.'\"/>\n\t";
		content += "<span id=\"errorTips\" style=\"margin-left: 80px;height: 100px\">";
		if (message != null && !"".equals(message)) {
			content += "<font color=\"red\">" + message + "</font></span>\n\t";
		} else {
			content += "</span>\n\t";
		}
		content += "</div>\n\t";
		content += "</div>\n\t";
		content += "</div>\n\t";
		content += "<div class=\"lg_foot\">\n\t";
		content += "<input type=\"submit\" value=\"Login In\" class=\"bn\"/>\n\t";
		content += "</div>\n\t";
		content += "</form>\n\t";
		content += "</div>\n\t";
		content += "</body>\n\t";
		content += "</html>\n\t";
		
		return content;
	}
}
