package com.game.beibei.common.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import cn.hutool.core.convert.Convert;

public class ChecksumUtil {
	
	private static final String CHECKSUM = "20070201";

	public static String md5(String value) {
		try {
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(value.getBytes(), 0, value.length());
			String md5Val;
			for (md5Val = (new BigInteger(1, m.digest())).toString(16), md5Val = md5Val.toUpperCase(); md5Val.length() < 32; md5Val = "0" + md5Val);
			return md5Val;
	    }catch (NoSuchAlgorithmException e) {
	      return null;
	    } 
	}
	
	public static String md5(String account, String password) {
		return md5(account + md5(password) + CHECKSUM);
	}
	
	public static String md5(String account, String password, String goldCoin, String silverCoin, String privilege) {
		StringBuilder builder = new StringBuilder(account);
		String pwd = md5(password);
		pwd = md5(account + pwd);
		builder.append(pwd).append(TransHex(privilege)).append(TransHex(goldCoin)).append(TransHex(silverCoin)).append("ABCDEF");
		return md5(builder.toString());
	}
	
	public static String TransHex(String str) {
		int x = Convert.toInt(str).intValue();
		String result;
		for (result = Integer.toHexString(x).toUpperCase(); result.length() < 8; result = "0" + result);
		return result;
	}
	  
	public static String grp2(String a) { return a.substring((int)Math.floor(Math.toRadians(120.0D) + 1.0D)); }
	
	public static int checksum(String data) {
	    int a = 1;
	    int b = 0;
	    int result = 0;
	    try {
	      byte[] by = data.getBytes("gbk");
	      for (int i = 0; i < by.length; i++) {
	        a = (a + (by[i] + 256) % 256) % 65521;
	        b = (b + a) % 65521;
	      } 
	      result = b << 16 | a;
	      return result ^ 0xAB7932CF;
	    }catch (Exception e) {
	      return 0;
	    } 
	}
}
