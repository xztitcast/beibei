package com.game.beibei.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.common.P;
import com.game.beibei.entity.Cdk;
import com.game.beibei.mapper.CdkMapper;
import com.game.beibei.service.CdkService;

@Service("cdkService")
public class CdkServiceImpl extends ServiceImpl<CdkMapper, Cdk> implements CdkService {

	@Override
	public P<Cdk> getCdkList(int pageNum, int pageSize, Integer type, String name, Integer status) {
		IPage<Cdk> page = new Page<>(pageNum, pageSize);
		QueryWrapper<Cdk> query = new QueryWrapper<>();
		query.eq(type != null, "`type`", type).eq(StringUtils.isNotBlank(name), "`name`", name).eq(status != null, "status", status);
		page(page, query);
		return new P<>(page.getTotal(), page.getRecords());
	}

}
