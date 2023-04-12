package com.game.beibei.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.ddb1.GidInfo;
import com.game.beibei.mapper.GidInfoMapper;
import com.game.beibei.model.SpecialModel;
import com.game.beibei.service.GidInfoService;
import com.game.beibei.support.chain.RegisterChainHandler;
import com.game.beibei.support.chain.StandardChainHandler;

@DS("ddb1")
@Service("cidInfoService")
public class GidInfoServiceImpl extends ServiceImpl<GidInfoMapper, GidInfo> implements GidInfoService, RegisterChainHandler {
	
	private RegisterChainHandler handler;

	@Override
	public GidInfo getGidInfoByName(String name) {
		LambdaQueryWrapper<GidInfo> wrapper = Wrappers.lambdaQuery(GidInfo.class).eq(GidInfo::getName, name);
		return getOne(wrapper);
	}

	@Override
	@Transactional
	public boolean doHandler(SpecialModel sm) {
		GidInfo entity = new GidInfo(sm.getName());
		boolean save = this.save(entity);
		if(save) {
			sm.setChainParam(entity.getGid().toString());
			save = this.handler.doHandler(sm);
		}
		return save;
	}

	@Override
	public void setNextHandler(StandardChainHandler handler) {
		this.handler = (RegisterChainHandler) handler;
	}

}
