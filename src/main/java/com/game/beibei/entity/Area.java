package com.game.beibei.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "tb_area")
public class Area implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Integer id;

	private String name;
	
	private String showname;
	
	private String goldCoin;
	
	private String silverCoin;

	private Integer reg;
	
	private Integer dfReg;
	
	private Integer faBaoGongSheng;
	
	private String fbgsTaskGold;
	
	private String fbgsTaskSilver;
	
	private Integer shuangShuXing;
	
	private String ssxTaskGold;
	
  	private String ssxTaskSilver;
  	
  	private Integer polarTrans;
  	
  	private String polarTransTaskGold;
  	
  	private String polarTransTaskSilver;
  	
  	private String publicInf;
  	
  	private Integer limitNum;
  	
  	private Integer polarTransCDK;
  	
  	private String chargeLink;
  	
  	private String cdkLink;
  	
  	private Integer system;
}
