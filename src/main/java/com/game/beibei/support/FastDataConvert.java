package com.game.beibei.support;

import cn.hutool.core.util.HexUtil;

public class FastDataConvert {

	private static final String PREFIX = "5D77E13A0002";
	
	public static String toPath(String format, int value) {
		return toPath(true, format, value);
	}
	
	public static String toPath(boolean isPrefix, String format, long value) {
		String content = String.format(format, HexUtil.toHex(value)).replace(" ", "0").toUpperCase();
		return isPrefix ? PREFIX + content : content;
	}
	
	public static class DataFormatTemplate {
		
		public static final String FORMAT_8 = "%8s";
		
		public static final String FORMAT_16 = "%16s";
	}
}
