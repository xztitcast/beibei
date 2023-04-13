package com.game.beibei.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.common.P;
import com.game.beibei.common.utils.ChecksumUtil;
import com.game.beibei.entity.adball.Account;
import com.game.beibei.mapper.AccountMapper;
import com.game.beibei.model.GeneralModel;
import com.game.beibei.model.SpecialModel;
import com.game.beibei.service.AccountService;
import com.game.beibei.support.chain.RegisterChainHandler;
import com.game.beibei.support.chain.StandardChainHandler;

@DS("adball")
@Service("accountService")
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService, RegisterChainHandler {
	
	private RegisterChainHandler handler;
	
	@Override
	@Transactional
	public boolean save(GeneralModel gm) {
		Account entity = new Account();
		entity.setAccount(gm.getAccount());
		entity.setMemo(gm.getMemo());
		entity.setGoldCoin(gm.getGoldCoin().toString());
		entity.setSilverCoin(gm.getSilverCoin().toString());
		entity.setPrivilege(gm.getPrivilege());
		entity.setBlockedTime("");
		entity.setPassword(ChecksumUtil.md5(gm.getAccount(), gm.getPassword()));
		boolean save = this.save(entity);
		if(save) {
			this.baseMapper.checksum(gm.getAccount());
		}
		return save;
	}

	@Override
	public P<Account> getAccountList(int pageNum, int pageSize, boolean isGM, Integer block, String account) {
		IPage<Account> page = new Page<>(pageNum, pageSize);
		QueryWrapper<Account> wrapper = new QueryWrapper<>();
		if(isGM) {
			wrapper.gt("privilege", 0);
		}
		if(block != null) {
			wrapper.eq("blocked_time", block.toString());
		}
		wrapper.eq(StringUtils.isNotBlank(account), "`account`", account);
		page(page, wrapper);
		return new P<>(page.getTotal(), page.getRecords());
	}

	@Override
	@Transactional
	public boolean blockWithUnblock(Account account) {
		boolean update = updateById(account);
		if(update) {
			this.baseMapper.checksum(account.getAccount());
		}
		return update;
	}

	/**
	 * 由于垮库此register方法不能添加@Transaction来维持事物
	 * 这样将导致this.save()方法事物失效
	 */
	@Override
	public boolean doHandler(SpecialModel sm) {
		boolean save = this.save(sm);
		if(save) {
			save = this.handler.doHandler(sm);
		}
		return save;
	}

	@Override
	public void setNextHandler(StandardChainHandler handler) {
		this.handler = (RegisterChainHandler) handler;
	}

}
