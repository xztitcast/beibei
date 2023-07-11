package com.game.beibei.entity.ddb1;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.game.beibei.common.serialize.EncodeSerializer;

import cn.hutool.core.date.DatePattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "gid_info")
public class GidInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long gid;
	
	private String type;
	
	@JsonSerialize(using = EncodeSerializer.class)
	private String name;
	
	private String time;
	
	private String memo;

	public GidInfo() {
		super();
	}

	public GidInfo(String name) {
		super();
		this.type = "user";
		this.name = name;
		this.time = DatePattern.PURE_DATETIME_FORMAT.format(new Date());
	}
	
}
