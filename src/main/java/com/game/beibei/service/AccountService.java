package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.common.P;
import com.game.beibei.entity.adball.Account;
import com.game.beibei.model.GeneralModel;

public interface AccountService extends IService<Account> {
	
	boolean save(GeneralModel gm);

	P<Account> getAccountList(int pageNum, int pageSize, boolean isGM, boolean isBlocked, String account);
	
	boolean blockWithUnblock(Account account);
	
}
