package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "账号不能为空!")
	private String account;
	
	@NotBlank(message = "gid不能为空!")
	private String gid;
	
	@NotBlank(message = "物品类型不能为空!")
	private String type;
	
	@NotBlank(message = "物品名称不能为空!")
	private String name;
	
	@NotBlank(message = "物品数量不能小于等于0!")
	private String num;
}
