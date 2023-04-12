package com.game.beibei.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateModel {

	@NotBlank(message = "账号不能为空!")
	private String account;
	
	@NotNull(message = "角色名不能为空!")
	private Long gid;
	
	private String branch;
	
	@NotBlank(message = "修改的内容不能为空")
	private String content;
}
