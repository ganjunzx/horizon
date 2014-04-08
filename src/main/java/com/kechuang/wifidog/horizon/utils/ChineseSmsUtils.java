package com.kechuang.wifidog.horizon.utils;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.log4j.Logger;

import com.kechuang.wifidog.horizon.service.impl.CommonUserServiceImpl;

public class ChineseSmsUtils {
	private static final Logger LOG = Logger.getLogger(CommonUserServiceImpl.class);
	public long remainSystemSms(String userName, String key) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/SMS/");
		post.addRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf8");// 在头文件中设置转码
		NameValuePair[] data = { new NameValuePair("Uid", userName),
				new NameValuePair("Action", "SMS_Num"),
				new NameValuePair("key", key) };
		post.setRequestBody(data);

		try {
			client.executeMethod(post);
			int statusCode = post.getStatusCode();
			if (statusCode == 200) {
				long result = Long.parseLong(new String(post.getResponseBodyAsString().getBytes(
						"utf8")));
				if (result < 0) {
					LOG.error("Chinese Sms status error:\t" + result);
					post.releaseConnection();
					return 0;
				} else {
					post.releaseConnection();
					return result;
				}
			} else {
				LOG.error("Chinese Sms connect error:\t" + statusCode);
				post.releaseConnection();
				return 0;
			}
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			post.releaseConnection();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			post.releaseConnection();
			return 0;
		}
	}
	
	public long sendVerifyCodeSms (String uid, String key, String celphone, String content) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/"); 
		 post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
		NameValuePair[] data ={ new NameValuePair("Uid", uid),new NameValuePair("Key", key),new NameValuePair("smsMob",celphone),new NameValuePair("smsText",content)};
		post.setRequestBody(data);

		 try {
			client.executeMethod(post);
			 int statusCode = post.getStatusCode();
			 if (statusCode == 200) {
				 long result = Long.parseLong(new String(post.getResponseBodyAsString().getBytes("utf8"))); 
				 if (result > 0) {
					 post.releaseConnection();
					 return result;
				 } else {
					 LOG.error("Chinese Sms status error:\t" + result);
					post.releaseConnection();
					 return 0;
				 }
			 } else {
				 LOG.error("Chinese Sms connect error:\t" + statusCode);
					post.releaseConnection();
				 return 0;
			 }
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			post.releaseConnection();
			return 0;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e);
			post.releaseConnection();
			return 0;
		}
	}
}
