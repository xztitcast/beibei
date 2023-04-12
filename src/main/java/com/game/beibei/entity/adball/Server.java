package com.game.beibei.entity.adball;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.game.beibei.common.jacson.EncodeSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "`server`")
public class Server {

	@JsonSerialize(using = EncodeSerializer.class)
	@TableId(type = IdType.INPUT)
	private String server;
	
	private Integer maxUser;
	
	@JsonSerialize(using = EncodeSerializer.class)
	private String dist;
	
	private Integer enable;
	
	@TableField(exist = false)
	private Integer online;
	
}
