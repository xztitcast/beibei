package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveModel implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "原账号不能为空")
	private String source;
	
	@NotNull(message = "角色名不能为空")
	private long gid;
	
	@NotBlank(message = "目标账号不能为空!")
	private String target;
	
	private String chainParam;
}
