package com.game.beibei.entity.ddb1;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "basic_char_info")
public class BasicCharInfo {

	@TableId(type = IdType.INPUT)
	private String gid;
	
	private String name;
	
	private Integer polar;
	
	private Integer gender;
	
	private String time;

}
