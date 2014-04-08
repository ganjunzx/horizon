$.extend($.fn.validatebox.defaults.rules, {
		  idcard : {// 验证身份证 
		        validator : function(value) { 
		            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value); 
		        }, 
		        message : '身份证号码格式不正确' 
		    },
		      minLength: {
		        validator: function(value, param){
		            return value.length >= param[0];
		        },
		        message: '请输入至少（2）个字符.'
		    },
		    length:{validator:function(value,param){ 
		        var len=$.trim(value).length; 
		            return len>=param[0]&&len<=param[1]; 
		        }, 
		            message:"输入内容长度必须介于{0}和{1}之间." 
		        }, 
		    phone : {// 验证电话号码 
		        validator : function(value) { 
		            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
		        }, 
		        message : '格式不正确,请使用下面格式:020-88888888' 
		    }, 
		    mobile : {// 验证手机号码 
		        validator : function(value) { 
		            return /^(13|15|18)\d{9}$/i.test(value); 
		        }, 
		        message : '手机号码格式不正确' 
		    }, 
		    intOrFloat : {// 验证整数或小数 
		        validator : function(value) { 
		            return /^\d+(\.\d+)?$/i.test(value); 
		        }, 
		        message : '请输入数字，并确保格式正确' 
		    }, 
		    currency : {// 验证货币 
		        validator : function(value) { 
		            return /^\d+(\.\d+)?$/i.test(value); 
		        }, 
		        message : '货币格式不正确' 
		    }, 
		    qq : {// 验证QQ,从10000开始 
		        validator : function(value) { 
		            return /^[1-9]\d{4,11}$/i.test(value); 
		        }, 
		        message : 'QQ号码格式不正确' 
		    }, 
		    integer : {// 验证整数 
		        validator : function(value) { 
		            return /^[+]?[1-9]+\d*$/i.test(value); 
		        }, 
		        message : '请输入整数' 
		    }, 
		    age : {// 验证年龄
		        validator : function(value) { 
		            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value); 
		        }, 
		        message : '年龄必须是0到120之间的整数' 
		    }, 
		    
		    chinese : {// 验证中文 
		        validator : function(value) { 
		            return /^[\Α-\￥]+$/i.test(value); 
		        }, 
		        message : '请输入中文' 
		    }, 
		    english : {// 验证英语 
		        validator : function(value) { 
		            return /^[A-Za-z]+$/i.test(value); 
		        }, 
		        message : '请输入英文' 
		    }, 
		    unnormal : {// 验证是否包含空格和非法字符 
		        validator : function(value) { 
		            return /.+/i.test(value); 
		        }, 
		        message : '输入值不能为空和包含其他非法字符' 
		    }, 
		    username : {// 验证用户名 
		        validator : function(value) { 
		            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value); 
		        }, 
		        message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）' 
		    }, 
		    faxno : {// 验证传真 
		        validator : function(value) { 
//		            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value); 
		            return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value); 
		        }, 
		        message : '传真号码不正确' 
		    }, 
		    zip : {// 验证邮政编码 
		        validator : function(value) { 
		            return /^[1-9]\d{5}$/i.test(value); 
		        }, 
		        message : '邮政编码格式不正确' 
		    }, 
		    ip : {// 验证IP地址 
		        validator : function(value) { 
		            return /d+.d+.d+.d+/i.test(value); 
		        }, 
		        message : 'IP地址格式不正确' 
		    }, 
		    name : {// 验证姓名，可以是中文或英文 
		            validator : function(value) { 
		                return /^[\Α-\￥]+$/i.test(value)|/^\w+[\w\s]+\w+$/i.test(value); 
		            }, 
		            message : '请输入姓名' 
		    },
		    date : {// 验证姓名，可以是中文或英文 
		        validator : function(value) { 
		         //格式yyyy-MM-dd或yyyy-M-d
		            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value); 
		        },
		        message : '清输入合适的日期格式'
		    },
		    msn:{ 
		        validator : function(value){ 
		        return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value); 
		    }, 
		    message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)' 
		    },
		    same:{ 
		        validator : function(value, param){ 
		            if($("#"+param[0]).val() != "" && value != ""){ 
		                return $("#"+param[0]).val() == value; 
		            }else{ 
		                return true; 
		            } 
		        }, 
		        message : '两次输入的密码不一致！'    
		    } 
		});

	/* 密码由字母和数字组成，至少6位 */
	var safePassword = function (value) {
	    return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
	};

	var idCard = function (value) {
	    if (value.length == 18 && 18 != value.length) return false;
	    var number = value.toLowerCase();
	    var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
	    var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
	    if (re == null || a.indexOf(re[1]) < 0) return false;
	    if (re[2].length == 9) {
	        number = number.substr(0, 6) + '19' + number.substr(6);
	        d = ['19' + re[4], re[5], re[6]].join('-');
	    } else d = [re[9], re[10], re[11]].join('-');
	    if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
	    for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
	    return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
	};
	
	var isDateTime = function (format, reObj) {
	    format = format || 'yyyy-MM-dd';
	    var input = this, o = {}, d = new Date();
	    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
	    var len = f1.length, len1 = f3.length;
	    if (len != f2.length || len1 != f4.length) return false;
	    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
	    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
	    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
	    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
	    o.dd = s(o.dd, o.d, d.getDate(), 31);
	    o.hh = s(o.hh, o.h, d.getHours(), 24);
	    o.mm = s(o.mm, o.m, d.getMinutes());
	    o.ss = s(o.ss, o.s, d.getSeconds());
	    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
	    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
	    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
	    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
	    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
	    return reVal && reObj ? d : reVal;
	    function s(s1, s2, s3, s4, s5) {
	        s4 = s4 || 60, s5 = s5 || 2;
	        var reVal = s3;
	        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
	        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
	        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
	    }
	};

	//获得url中某参数的值
	function getUrlParam(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

	function formatDate(now) {
		var year = now.getYear();
		var month = now.getMonth() + 1;
		var date = now.getDate();
		var hour = now.getHours();
		var minute = now.getMinutes();
		var second = now.getSeconds();
		return year + "-" + month + "-" + date + "   " + hour + ":" + minute + ":"
				+ second;
	}

	// 获得web应用contextPath
	function getBasePath() {
		var curWwwPath = window.document.location.href;
		var pathName = window.document.location.pathname;
		var pos = curWwwPath.indexOf(pathName);
		var localhostPaht = curWwwPath.substring(0, pos);
		var projectName = pathName
				.substring(0, pathName.substr(1).indexOf('/') + 1);
		return (localhostPaht + projectName);
	};

	/*
	函数：格式化日期
	参数：formatStr-格式化字符串
		d：将日显示为不带前导零的数字，如1
		dd：将日显示为带前导零的数字，如01
		ddd：将日显示为缩写形式，如Sun
		dddd：将日显示为全名，如Sunday
		M：将月份显示为不带前导零的数字，如一月显示为1
		MM：将月份显示为带前导零的数字，如01
		MMM：将月份显示为缩写形式，如Jan
		MMMM：将月份显示为完整月份名，如January
		yy：以两位数字格式显示年份
		yyyy：以四位数字格式显示年份
		h：使用12小时制将小时显示为不带前导零的数字，注意||的用法
		hh：使用12小时制将小时显示为带前导零的数字
		H：使用24小时制将小时显示为不带前导零的数字
		HH：使用24小时制将小时显示为带前导零的数字
		m：将分钟显示为不带前导零的数字
		mm：将分钟显示为带前导零的数字
		s：将秒显示为不带前导零的数字
		ss：将秒显示为带前导零的数字
		l：将毫秒显示为不带前导零的数字
		ll：将毫秒显示为带前导零的数字
		tt：显示am/pm
		TT：显示AM/PM
		返回：格式化后的日期
	*/
	Date.prototype.format = function (formatStr) 
	{
		var date = this;

		/*
		函数：填充0字符
		参数：value-需要填充的字符串, length-总长度
		返回：填充后的字符串
		*/
		var zeroize = function (value, length) 
		{
			if (!length) 
			{
				length = 2;
			}
			value = new String(value);
			for (var i = 0, zeros = ''; i < (length - value.length); i++) 
			{
				zeros += '0';
			}
			return zeros + value;
		};

		return formatStr.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|M{1,4}|yy(?:yy)?|([hHmstT])\1?|[lLZ])\b/g, function($0) 
		{
			switch ($0) 
			{
				case 'd': return date.getDate();
				case 'dd': return zeroize(date.getDate());
				case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][date.getDay()];
				case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][date.getDay()];
				case 'M': return date.getMonth() + 1;
				case 'MM': return zeroize(date.getMonth() + 1);
				case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][date.getMonth()];
				case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][date.getMonth()];
				case 'yy': return new String(date.getFullYear()).substr(2);
				case 'yyyy': return date.getFullYear();
				case 'h': return date.getHours() % 12 || 12;
				case 'hh': return zeroize(date.getHours() % 12 || 12);
				case 'H': return date.getHours();
				case 'HH': return zeroize(date.getHours());
				case 'm': return date.getMinutes();
				case 'mm': return zeroize(date.getMinutes());
				case 's': return date.getSeconds();
				case 'ss': return zeroize(date.getSeconds());
				case 'l': return date.getMilliseconds();
				case 'll': return zeroize(date.getMilliseconds());
				case 'tt': return date.getHours() < 12 ? 'am' : 'pm';
				case 'TT': return date.getHours() < 12 ? 'AM' : 'PM';
			}
		});
	}