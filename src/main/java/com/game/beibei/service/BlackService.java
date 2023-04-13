package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.entity.Black;
import com.game.beibei.entity.adball.Account;

public interface BlackService extends IService<Black> {

	void save(Account entity);
	
}
