package com.kechuang.wifidog.horizon.utils;

public class HorizonConfig {
	// 时间类型的处理以及格式化字符串
	public static String DATA_FORMAT = "yyyy-MM-dd";
	public static String DATA_FORMAT_STRING = "yyyy-MM-dd hh:mm";

	// 热点默认值
	public static final long NODE_DEFAULT_TURNOFFTIME = 0;
	public static final long NODE_DEFAULT_TURNOFFFREETIME = 0;
	public static final long NODE_DEFAULT_TOTALLIMITINCOMING = 0;
	public static final long NODE_DEFAULT_TOTALLIMITOUTGOING = 0;
	public static final long NODE_DEFAULT_EACHLIMITINCOMING = 0;
	public static final long NODE_DEFAULT_EACHLIMITOUTGOING = 0;
	
	public static final long NODE_DEFAULT_REMAINSMS = 0;
	public static final int NODE_DEFAULT_LIMITONLINEUSERNUM = 0;
	public static final long NODE_DEFAULT_SMSCODEVALIDTIME = 0;
	public static final byte NODE_DEFAULT_SMSCODEVALIDTIMETYPE = 1;
	public static final int NODE_DEFAULT_SMSCODELENGTH = 6;
	public static final int NODE_DEFAULT_SMSCODEDAYNUM = 3;
	public static final long NODE_DEFAULT_SMSCODEOBTAININTERVAL = 60 * 1000;

	public static final String NODECONNECTION_DEFAULT_LOGIN_USER = "sys_anonymous_user";
	public static final String SMSRECORD_DEFAULT_SMS_CONTENT = "欢迎使用T-Link热点管理系统";
	
	public static final double USER_DEFAULT_ACOUNT = 0;

	public static final String[] MOBILE_SPECIFIC_SUBSTRING = { "iPad", "iPhone",
			"Android", "MIDP", "Opera Mobi", "Opera Mini", "BlackBerry",
			"HP iPAQ", "IEMobile", "MSIEMobile", "Windows Phone", "HTC", "LG",
			"MOT", "Nokia", "Symbian", "Fennec", "Maemo", "Tear", "Midori",
			"armv", "Windows CE", "WindowsCE", "Smartphone", "240x320",
			"176x220", "320x320", "160x160", "webOS", "Palm", "Sagem",
			"Samsung", "SGH", "SIE", "SonyEricsson", "MMP", "UCWEB", "Windows NT", "Mac", "Linux"};

	// tokens生存状态
	public static enum TOKENS_STATUS {
		TOKEN_UNUSED("token_unused", 1), TOKEN_INUSE("token_inuse", 2), TOKEN_USED(
				"token_used", 3);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private TOKENS_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum USERTRADE_TRADE_TYPE {
		RECHARGE("recharge", 1), ENCHASHMENT("enchashment", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private USERTRADE_TRADE_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum USERTRADE_TRADE_WAY {
		BANK("bank", 1), ALIPAY("alipay", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private USERTRADE_TRADE_WAY(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum USERTRADE_TRADE_STATUS {
		CHECKING("checking", 1), SUCESSED("successed", 2),FAILED("failed", 3), CANCEL("cancel", 4);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private USERTRADE_TRADE_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum NODE_SMSCODEVALIDTIMETYPE {
		MINUTE("minute", 1), HOUR("hour", 2),DAY("day", 3);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private NODE_SMSCODEVALIDTIMETYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum SMSTRADE_SMS_PACKAGE {
		ONE_HUNDRED("授权100条(单价9分/条，合计9元)", 1, 9, 100), FIVE_HUNDRED("授权500条(单价8.5分/条，合计42.5元)", 2, 42.5, 500),
		ONE_THOUSAND("授权1000条(单价8分/条，合计80元)", 3, 80, 1000), FIVE_THOUSAND("授权5000条(单价7.5分/条，合计375元)", 4, 375, 5000),
		TEN_THOUSAND("授权10000条(单价7分/条，合计700元)", 5, 375, 10000), ONE_HUNDRED_THOUSAND("授权100000条(单价6分/条，合计6000元)", 6, 6000, 100000);

		private int index;
		private String name;
		private double totalPrice;
		private long applySms;

		public String getName() {
			return name;
		}

		private SMSTRADE_SMS_PACKAGE(String name, int index, double totalPrice, long applySms) {
			this.name = name;
			this.index = index;
			this.totalPrice = totalPrice;
			this.applySms = applySms;
		}

		public int getIndex() {
			return index;
		}
		
		public long getApplySms() {
			return applySms;
		}
		public double getTotalPrice() {
			return totalPrice;
		}
	};
	
	public static enum SMSTRADE_TRADE_STATUS {
		SUCCESS("success", 1);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private SMSTRADE_TRADE_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	public static enum NODE_LOGINTYPE {
		DIRECTORY("直接连接", 1), WEB("用户认证", 2), CELLPHONE("短信验证码认证", 3);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private NODE_LOGINTYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum STAGE_STATUS {
		STAGE_LOGIN("login", 1), STAGE_LOGOUT("logout", 2), STAGE_COUNTERS(
				"counters", 3);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private STAGE_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum COMMONUSER_MULTITERMINALLOGIN {
		NOSET("yes", 1), ALLOW("yes", 1), FORBID("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private COMMONUSER_MULTITERMINALLOGIN(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum COMMONUSER_ISLOGINED {
		UNLOGINED("yes", 1), LOGINED("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private COMMONUSER_ISLOGINED(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum COMMONUSER_USERTYPE {
		ONCEUSER("yes", 1), NORMALUSER("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private COMMONUSER_USERTYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum NODE_RUNNING {
		RUNNING("yes", 1), STOP("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private NODE_RUNNING(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum NODE_SMSTYPE {
		ONCE("yes", 1), MULTIPLE("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private NODE_SMSTYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	public static enum COMMONUSER_STATUS {
		NORMAL("yes", 1), LIMIT("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private COMMONUSER_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum SMSCONTENT_STATUS {
		CHECK("yes", 1), UNCHECK("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private SMSCONTENT_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};

	// 路由auth阶段生存状态
	public static enum HORIZON_ERROR {
		ERRER1("权限认证错误", 1), ERRER2("一次性用户已登录过", 2), ERRER3("用户多终端登录错误", 3), ERRER4(
				"路由多终端登录错误", 4), ERRER5("强制时间断开", 5), ERRER6("空闲时间强制断开", 6), ERRER7(
				"热点总下载流量限制", 7), ERRER8("热点总上传流量限制", 8), ERRER9("热点单用户下载流量限制",
				9), ERRER10("热点单用户上传流量限制", 10), ERRER11("未到热点开放时间", 11), ERRER12(
				"正常退出登录", 12), ERRER13("管理员强制推出登录", 13), ERRER14("热点未激活", 14), ERRER15(
				"热点未连接服务器", 15), ERRER16("用户已经受限", 16), ERRER17("用户已经过期", 17);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private HORIZON_ERROR(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum SMSSECURITYCODE_STATUS {
		LOGINED("yes", 1), NOLOGIN("no", 2);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private SMSSECURITYCODE_STATUS(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
	
	public static enum SMSSECURITYCODE_MOBILE_TYPE {
		CHINA_MOBILE("中国移动", 1), CHINA_UNICOM("中国联通", 2), CHINA_NET("中国电信", 3), NO_TYPE("未知", 4);

		private int index;
		private String name;

		public String getName() {
			return name;
		}

		private SMSSECURITYCODE_MOBILE_TYPE(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public int getIndex() {
			return index;
		}
	};
}
