package com.game.beibei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName(value = "tb_area")
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String name;
	
	private String showName;
	
	private String goldCoin;
	
	private String silverCoin;

	private Integer reg;
	
	private Integer dfReg;
	
	private Integer fabao;
	
	private String fbUseGold;
	
	private String fbUseSilver;
	
	private Integer shuxing;
	
	private String sxUseGold;
	
  	private String sxUseSilver;
  	
  	private Integer polar;

	private Integer polarCdk;
  	
  	private String polarUseGold;
  	
  	private String polarUseSilver;
  	
  	private String notice;
  	
  	private Integer limitNum;
  	
  	private String chargeLink;
  	
  	private String cdkLink;

}
