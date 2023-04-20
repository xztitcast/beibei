package com.game.beibei.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.entity.ddb1.Data;

public interface DataService extends IService<Data> {
	
	List<Long> getGidList(String account);
	
	List<Data> getDataList(String path);
	
	Data getDataEntity(String account);
	
	boolean updateByMultiId(Data entity);
	
	boolean remveByMultiId(Data entity);
	
	Data getDataByMultiId(String path, String name, String branch);

	boolean setContentCash(String gid, int cash);
	
	boolean setContentVcash(String gid, int vcash);
	
	boolean setContentTao(String gid, int tao);

	boolean setContentItem(String gid, String mode);
}
