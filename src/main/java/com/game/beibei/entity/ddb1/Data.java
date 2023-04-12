package com.game.beibei.entity.ddb1;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "data")
public class Data {

	protected String path;
	
	protected String name;
	
	protected String branch;
	
	protected String content;
	
	protected String time;
	
	protected Integer checksum;

}
