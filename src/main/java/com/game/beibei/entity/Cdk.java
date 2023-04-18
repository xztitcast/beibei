package com.game.beibei.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "tb_cdk")
public class Cdk implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Long id;

	@TableField(value = "`name`")
	private String name;
	
	@TableField(value = "`number`")
	private String number;
	
	@TableField(value = "`type`")
	private Integer type;
	
	private Integer status;
	
	private String account;
	
	private String created;
	
	private String updated;
}
