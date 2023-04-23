package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemModel extends ScoreModel implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "ID不能为空!")
	private Integer id;
	
	@NotBlank(message = "物品名称不能为空!")
	private String name;

}
