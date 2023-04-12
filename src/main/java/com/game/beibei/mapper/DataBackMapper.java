package com.game.beibei.mapper;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.entity.ddb1.DataBack;

public interface DataBackMapper extends BaseMapper<DataBack> {

	DataBack selectByMultiId(@Param("path") String path, @Param("name") String name, @Param("branch") String branch);
	
	int updateByMultiId(Data entity);
	
	int removeByMultiId(Data entity);
}
