package com.game.beibei.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginModel implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String username;
	
	private String password;
	
	private String code;

}
