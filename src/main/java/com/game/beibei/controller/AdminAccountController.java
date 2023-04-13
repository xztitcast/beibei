package com.game.beibei.controller;

import java.util.Arrays;

import javax.validation.constraints.NotBlank;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game.beibei.common.P;
import com.game.beibei.common.R;
import com.game.beibei.common.S;
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
	
	@PostMapping(value = "/privilege", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public R privilege(@RequestParam @NotBlank(message = "账号不能为空") String account,
			@RequestParam @NotBlank(message = "权限不能为空") String privilege) {
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		entity.setPrivilege(privilege);
		boolean blockWithUnblock = accountService.blockWithUnblock(entity);
		if(blockWithUnblock) {
			return R.ok();
		}
		return R.error(S.ACCOUNT_PRIVILEGE_UPDATE_ERR);
	}
	
	@PostMapping(value = "/block/{account}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public R block(@PathVariable String account, @RequestParam(defaultValue = "封禁MAC") String message) {
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		if(StringUtils.isNotBlank(entity.getBlockedTime())) {
			return R.error(S.ACCOUNT_BLOCKED_ERR);
		}
		if(StringUtils.isBlank(message)) {
			return R.error(S.ACCOUNT_BLOCKED_MSG_ERR);
		}
		entity.setBlockedTime("1");
		entity.setBlockedReason(entity.getBlockedReason() + message);
		accountService.blockWithUnblock(entity);
		return R.ok();
	}
	
	@GetMapping("/unblock/{account}")
	public R unblock(@PathVariable String account) {
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		if(StringUtils.isBlank(entity.getBlockedTime())) {
			return R.error(S.ACCOUNT_UNBLOCKED_ERR);
		}
		entity.setBlockedTime("");
		entity.setBlockedReason("");
		accountService.blockWithUnblock(entity);
		return R.ok();
	}
	
	@PostMapping("/delete")
	public R delete(String[] ids) {
		accountService.removeBatchByIds(Arrays.asList(ids));
		return R.ok();
	}
	
}
