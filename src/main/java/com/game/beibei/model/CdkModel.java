package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.alibaba.fastjson2.JSON;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CdkModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "类型不能为空")
	private Integer type;
	
	private String name;
	
	@NotBlank(message = "必选项不能为空!")
	private String content;

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	} 
	
}
