package com.game.beibei.entity.adball;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.game.beibei.common.jacson.EncodeSerializer;
import com.game.beibei.common.jacson.MacAddressSerializer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "account")
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.INPUT)
	private String account;
	
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	private String memo;
	
	private String goldCoin;
	
	private String silverCoin;
	
	private String privilege;
	
	@JsonSerialize(using = EncodeSerializer.class)
	private String blockedReason;
	
	private String blockedTime;
	
	@JsonIgnore
	private String lastLoginTime;
	
	private String lastLoginIp;
	
	@JsonSerialize(using = MacAddressSerializer.class)
	private String lastLoginId;
	
	private Date updateTime;
}
