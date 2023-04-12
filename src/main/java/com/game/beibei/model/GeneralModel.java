package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "账号不能为空!")
	protected String account;
	
	@NotBlank(message = "密码不能为空!")
	protected String password;
	
	protected String memo;
	
	@NotNull(message = "金元宝不能为空!")
	@Min(value = 0, message = "金元宝数量不能小于0")
	@Max(value = 2000000000, message = "金元宝数量不能大于20亿")
	protected Integer goldCoin;
	
	@NotNull(message = "银元宝不能为空!")
	@Min(value = 0, message = "银元宝数量不能小于0")
	@Max(value = 2000000000, message = "银金元宝数量不能大于20亿")
	protected Integer silverCoin;
	
	protected String privilege = "0";
}
