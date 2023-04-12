package com.game.beibei.entity.ddb1;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "data1")
public class DataBack extends Data {

	public DataBack() {
		super();
	}

	public DataBack(Data entity) {
		super.path = entity.getPath();
		super.name = entity.getName();
		super.time = entity.getTime();
		super.branch = entity.getBranch();
		super.content = entity.getContent();
		super.checksum = entity.getChecksum();
	}
	
}
