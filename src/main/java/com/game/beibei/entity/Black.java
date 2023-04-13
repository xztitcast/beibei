package com.game.beibei.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_black")
public class Black implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Integer id;
	
	private String account;
	
	private String mac;
	
	private String ip;
	
	private Date created;
	
	private Date updated;

}
