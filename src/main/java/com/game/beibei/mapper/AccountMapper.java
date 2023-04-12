package com.game.beibei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.game.beibei.entity.adball.Account;

public interface AccountMapper extends BaseMapper<Account> {

	int checksum(String account);
}
