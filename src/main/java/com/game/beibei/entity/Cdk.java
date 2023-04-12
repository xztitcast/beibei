package com.game.beibei.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cdk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String cdk;
	
	private String type;
	
	private String name;
	
	private Integer status;
	
	private String account;
	
	private String addtime;
	
	private String usetime;
}
