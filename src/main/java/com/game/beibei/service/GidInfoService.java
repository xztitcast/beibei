package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.entity.ddb1.GidInfo;

public interface GidInfoService extends IService<GidInfo> {
	
	GidInfo getGidInfoByName(String name);

}
