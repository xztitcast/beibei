package com.game.beibei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game.beibei.common.P;
import com.game.beibei.common.R;
import com.game.beibei.entity.adball.Account;
import com.game.beibei.service.AccountService;

@RestController
@RequestMapping("/admin/account")
public class AdminAccountController {
	
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/save")
	public R save() {
		Account entity = new Account();
		entity.setAccount("a1234567");
		entity.setPassword("a123456789");
		entity.setMemo("a123456789");
		entity.setGoldCoin("2000000000");
		entity.setSilverCoin("2000000000");
		entity.setPrivilege("0");
		entity.setBlockedTime("");
		accountService.save(entity);
		return R.ok();
	}

	@GetMapping("/list")
	public R list(@RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize, 
			@RequestParam(value = "isGM", defaultValue = "false") Boolean isGM,
			@RequestParam(value = "isBlocked", defaultValue = "false") Boolean isBlocked,
			@RequestParam(required = false) String account) {
		P<Account> p = accountService.getAccountList(pageNum, pageSize, isGM, isBlocked, account);
		return R.of(p);
	}
	
	@GetMapping("/block")
	public R block(@RequestParam String account) {
		Account entity = new Account();
		entity.setAccount(account);
		entity.setBlockedReason("封禁MAC");
		return R.ok();
	}
}
