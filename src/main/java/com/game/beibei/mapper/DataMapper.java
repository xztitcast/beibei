package com.game.beibei.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.beibei.entity.ddb1.Data;

public interface DataMapper extends BaseMapper<Data> {

	Data selectByMultiId(@Param("path") String path, @Param("name") String name, @Param("branch") String branch);
	
	List<Data> selectByPath(String path);
	
	int updateByMultiId(Data entity);
	
	int removeByMultiId(Data entity);
}
