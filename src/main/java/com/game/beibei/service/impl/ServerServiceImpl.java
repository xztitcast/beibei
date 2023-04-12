package com.game.beibei.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.common.P;
import com.game.beibei.entity.adball.Server;
import com.game.beibei.mapper.ServerMapper;
import com.game.beibei.service.ServerService;

@DS("adball")
@Service("serverService")
public class ServerServiceImpl extends ServiceImpl<ServerMapper, Server> implements ServerService {

	@Override
	public P<Server> getServerList(int pageNum, int pageSize) {
		IPage<Server> page = new Page<>(pageNum, pageSize);
		page(page);
		return new P<>(page.getTotal(), page.getRecords());
	}

}
