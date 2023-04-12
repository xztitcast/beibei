package com.game.beibei.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.ddb1.BasicCharInfo;
import com.game.beibei.mapper.BasicCharInfoMapper;
import com.game.beibei.model.MoveModel;
import com.game.beibei.model.SpecialModel;
import com.game.beibei.service.BasicCharInfoService;
import com.game.beibei.support.FastDataConvert;
import com.game.beibei.support.FastDataConvert.DataFormatTemplate;
import com.game.beibei.support.chain.MoveRoleChainHandler;
import com.game.beibei.support.chain.RegisterChainHandler;
import com.game.beibei.support.chain.StandardChainHandler;

import cn.hutool.core.date.DatePattern;

@DS("ddb1")
@Service("basicCharInfoService")
public class BasicCharInfoServiceImpl extends ServiceImpl<BasicCharInfoMapper, BasicCharInfo> implements BasicCharInfoService, RegisterChainHandler, MoveRoleChainHandler {
	
	private StandardChainHandler handler;
	
	@Override
	@Transactional
	public boolean doHandler(SpecialModel sm) {
		String gid = FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, Long.valueOf(sm.getChainParam()));
		BasicCharInfo entity = new BasicCharInfo();
		entity.setGid(gid);
		entity.setName(sm.getName());
		entity.setPolar(sm.getPolar());
		entity.setGender(sm.getGender());
		entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
		boolean save = this.save(entity);
		if(save) {
			sm.setChainParam(gid);
			save = ((RegisterChainHandler) this.handler).doHandler(sm);
		}
		return save;
	}

	/**
	 * 1
	 */
	@Override
	public boolean doHandler(MoveModel mm) {
		String gid = FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, mm.getGid());
		this.removeById(gid);
		mm.setChainParam(gid);
		return ((MoveRoleChainHandler)this.handler).doHandler(mm);
	}
	
	@Override
	public void setNextHandler(StandardChainHandler handler) {
		this.handler = handler;
	}


}
