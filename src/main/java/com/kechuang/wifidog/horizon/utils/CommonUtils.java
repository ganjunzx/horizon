package com.kechuang.wifidog.horizon.utils;

import java.util.Random;

public class CommonUtils {
	private static 	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
		'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
		'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	//生成验证码
	public static String generateVerifyCode(int verifyCodeLength) {
		StringBuffer randomCode = new StringBuffer();
		Random random = new Random();
		// 随机产生verifyCodeLength数字的验证码。
		for (int i = 0; i < verifyCodeLength; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(52)]);
			randomCode.append(strRand);
		}
		return randomCode.toString();
	}
}
