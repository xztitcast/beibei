package com.game.beibei.service.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson2.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.entity.ddb1.DataBack;
import com.game.beibei.mapper.DataBackMapper;
import com.game.beibei.service.DataBackService;

import cn.hutool.core.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DS("ddb1")
@Service("dataBackService")
public class DataBackServiceImpl extends ServiceImpl<DataBackMapper, DataBack> implements DataBackService {

	@Override
	public void backUpdateData(Data entity) {
		DataBack db = this.baseMapper.selectByMultiId(entity.getPath(), entity.getName(), entity.getBranch());
		if(db == null) {
			db = new DataBack(entity);
			this.save(db);
		}else {
			entity.setContent(CharsetUtil.convert(entity.getContent(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK));
			log.info("原始数据已经备份,每次修改数据记录日志 data: {}", JSON.toJSONString(entity));
		}
	}

}
