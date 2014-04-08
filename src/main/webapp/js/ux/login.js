function ajaxSubmit(form, option) {
	form.ajaxSubmit(option);
}

function checkLogin(data) {// 检查是否登录超时
	if (data.logoutFlag) {
		closeProgress();
		alert('提示', "登录超时,点击确定重新登录.", 'error', login);
		return false;
	}
	return true;
}
function submitForm(form, callback, dataType) {
	var option = {
		type : 'POST',
		url : 'user/login',
		dataType : dataType || 'json',
		success : function(data) {
			if ($.isFunction(callback)) {
				callback(data);
			}
		},
		error : function(response, textStatus, errorThrown) {
			try {
				closeProgress();
				var data = $.parseJSON(response.responseText);
				// 检查登录
				if (!checkLogin(data)) {
					return false;
				} else {
					alert('提示', data.msg || "请求出现异常,请联系管理员", 'error');
				}
			} catch (e) {
				alert('提示', "请求出现异常,请联系管理员.", 'error');
			}
		},
		complete : function() {

		}
	}
	ajaxSubmit(form, option);
}

function progress(title, msg) {
	var win = $.messager.progress({
		title : title || 'Please waiting',
		msg : msg || 'Loading data...'
	});
}

function closeProgress() {
	$.messager.progress('close');
}

function login() {
	try {
		$('#errorTips').html('');
		if ($("#userLogin").form('validate')) {
			progress('Please waiting', 'Loading...');
			$("#userLogin").form('submit', {
				url : getBasePath() + '/user/login',
				success : function(data) {
					closeProgress();
					var result = $.parseJSON(data);
					if (result.success == true) {
						window.location = "innerIndex.html";
					} else {
//						alert('提示', result.msg, 'error');
						$('#errorTips').html('<font color="red">' + result.msg + '</font>');
					}
					loadVrifyCode();// 刷新验证码
				},
				error : function(response, textStatus, errorThrown) {
					try {
						closeProgress();
						var data = $.parseJSON(response.responseText);
						// 检查登录
						if (!checkLogin(data)) {
							return false;
						} else {
							$('#errorTips').html('<font color="red">' + (data.msg || "请求出现异常,请联系管理员")+ '</font>');
						}
					} catch (e) {
						$('#errorTips').html('<font color="red">请求出现异常,请联系管理员.</font>');
					}
				}
			});
		}
		
	} catch (e) {

	}
	return false;
}

function loadVrifyCode() {// 刷新验证码
	var _url = "utils/varifyimage?time=new Date().getTime()";
	$(".vc-pic").attr("src", "utils/varifyimage?time=" + new Date().getTime());
}
function init() {
	if (window.top != window.self) {
		window.top.location = window.self.location;
	}
	// 验证码图片绑定点击事件
	$(".vc-pic").click(loadVrifyCode);
/*	var form = $("#loginForm");
	form.submit(login);*/
}

$(function() {
	loadVrifyCode();// 刷新验证码
	init();
});