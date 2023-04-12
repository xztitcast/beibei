package com.game.beibei.common.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.RandomUtil;

public abstract class DataRandomUtil {
	
	private static final int MAX = 99999;
	
	private static final int MIN = 10000;
	
	public static String hex() {
		int randomInt = RandomUtil.randomInt(MIN, MAX);
		return HexUtil.toHex(randomInt);
	}

}
