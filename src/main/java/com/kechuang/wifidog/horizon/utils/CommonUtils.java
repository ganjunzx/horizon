package com.kechuang.wifidog.horizon.utils;

import java.util.Random;

public class CommonUtils {
	private static 	char[] codeSequence = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	//生成验证码
	public static String generateVerifyCode(int verifyCodeLength) {
		StringBuffer randomCode = new StringBuffer();
		Random random = new Random();
		// 随机产生verifyCodeLength数字的验证码。
		for (int i = 0; i < verifyCodeLength; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(10)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
}
