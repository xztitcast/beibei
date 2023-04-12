package com.game.beibei.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game.beibei.common.Constant.Sys;
import com.game.beibei.common.R;
import com.game.beibei.common.S;
import com.game.beibei.common.utils.ChecksumUtil;
import com.game.beibei.entity.adball.Account;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.entity.ddb1.GidInfo;
import com.game.beibei.enums.Path;
import com.game.beibei.model.GeneralModel;
import com.game.beibei.model.MoveModel;
import com.game.beibei.model.SpecialModel;
import com.game.beibei.model.UpdateModel;
import com.game.beibei.service.AccountService;
import com.game.beibei.service.DataBackService;
import com.game.beibei.service.DataService;
import com.game.beibei.service.GidInfoService;
import com.game.beibei.service.impl.AccountServiceImpl;
import com.game.beibei.service.impl.BasicCharInfoServiceImpl;
import com.game.beibei.service.impl.DataServiceImpl.AchieveAndCarryChainHandler;
import com.game.beibei.service.impl.DataServiceImpl.ChildAndGuardChainHandler;
import com.game.beibei.service.impl.DataServiceImpl.LoginDataChainHandler;
import com.game.beibei.service.impl.DataServiceImpl.UserLevelChainHandler;
import com.game.beibei.service.impl.GidInfoServiceImpl;
import com.game.beibei.support.FastDataConvert;
import com.game.beibei.support.FastDataConvert.DataFormatTemplate;
import com.game.beibei.support.chain.ChainBuilder;
import com.game.beibei.support.chain.MoveRoleChainHandler;
import com.game.beibei.support.chain.RegisterChainHandler;

import cn.hutool.core.util.CharsetUtil;

@Valid
@RestController
@RequestMapping("/admin/general")
public class AdminGeneralController implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private GidInfoService gidInfoService;
	
	@Autowired
	private DataBackService dataBackService;
	
	@Autowired
	private DataService dataService;

	@GetMapping("/verifyAcc/{account}")
	public R verifyAcc(@PathVariable String account) {
		Account entity = accountService.getById(account);
		if(entity != null) {
			return R.error(S.ACCOUNT_REGISTERED_ERR);
		}
		return R.ok();
		
	}
	
	@PostMapping(value = "/verifyRole", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public R verifyRole(@RequestParam String name) {
		GidInfo gidInfo = gidInfoService.getGidInfoByName(name);
		if(gidInfo != null) {
			return R.error(S.ROLE_REGISTERED_ERR);
		}
		return R.ok();
	}
	
	@PostMapping("/special")
	public R special(@RequestBody SpecialModel sm) {
		Account entity = accountService.getById(sm.getAccount());
		if(entity != null) {
			return R.error(S.ACCOUNT_REGISTERED_ERR);
		}
		GidInfo gidInfo = gidInfoService.getGidInfoByName(sm.getName());
		if(gidInfo != null) {
			return R.error(S.ROLE_REGISTERED_ERR);
		}
		ChainBuilder<RegisterChainHandler> builder = new ChainBuilder<>();
		RegisterChainHandler registerChainHandler = builder.addHandler(this.applicationContext.getBean(AccountServiceImpl.class))
				.addHandler(this.applicationContext.getBean(GidInfoServiceImpl.class))
				.addHandler(this.applicationContext.getBean(BasicCharInfoServiceImpl.class))
				.addHandler(this.applicationContext.getBean(LoginDataChainHandler.class))
				.addHandler(this.applicationContext.getBean(UserLevelChainHandler.class))
				.addHandler(this.applicationContext.getBean(AchieveAndCarryChainHandler.class))
				.addHandler(this.applicationContext.getBean(ChildAndGuardChainHandler.class)).build();
		boolean register = registerChainHandler.doHandler(sm);
		if(register) {
			return R.ok();
		}
		return R.error(S.ACCOUNT_REGISTER_ERR);
	}
	
	@PostMapping("/ordinary")
	public R ordinary(@RequestBody GeneralModel gm) {
		Account entity = accountService.getById(gm.getAccount());
		if(entity != null) {
			return R.error(S.ACCOUNT_REGISTERED_ERR);
		}
		boolean save = accountService.save(gm);
		if(save) {
			return R.ok();
		}
		return R.error(S.ACCOUNT_REGISTER_ERR);
	}
	
	@PostMapping(value = "/password", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public R password(@RequestParam @NotBlank(message = "账号不能为空") String account,
			@NotBlank(message = "密码不能为空") @RequestParam String password) {
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		entity.setPassword(ChecksumUtil.md5(account, password));
		boolean blockWithUnblock = accountService.blockWithUnblock(entity);
		if(blockWithUnblock) {
			return R.ok();
		}
		return R.error(S.ACCOUNT_PASSWORD_UPDATE_ERR);
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
	public R block(@PathVariable String account, String message) {
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
	
	@GetMapping("/chargeCoin")
	public R chargeCoin(@RequestParam @NotBlank(message = "账号不能为空") String account,
			@RequestParam @NotNull(message = "状态不能为空!") Boolean status,
			@RequestParam(value = "goldCoin", defaultValue = "1") Integer goldCoin,
			@RequestParam(value = "silverCoin", defaultValue = "1") Integer silverCoin) {
		List<Account> list = List.of();
		if("ALL".equals(account.toUpperCase())) {
			list = accountService.list();
		}else {
			Account entity = accountService.getById(account);
			if(entity != null) list = List.of(entity);
		}
		if(status) {
			list = list.stream().filter(a -> {
				Data entity = dataService.getDataEntity(a.getAccount());
				return entity != null;
			}).collect(Collectors.toList());
		}
		if(CollectionUtils.isEmpty(list)) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		list.forEach(entity -> {
			int gc = Integer.valueOf(entity.getGoldCoin()) + goldCoin;
			int sc = Integer.valueOf(entity.getSilverCoin()) + silverCoin;
			if(gc > Sys.GAME_NUMBER) {
				gc = Sys.GAME_NUMBER;
			}
			if(sc > Sys.GAME_NUMBER) {
				sc = Sys.GAME_NUMBER;
			}
			entity.setGoldCoin(String.valueOf(gc));
			entity.setSilverCoin(String.valueOf(sc));
			accountService.blockWithUnblock(entity);
		});
		return R.ok();
	}
	
	@GetMapping("/updateCoin")
	public R updateCoin(@RequestParam String account,
			@RequestParam Integer goldCoin,
			@RequestParam Integer silverCoin) {
		List<Account> list = null;
		if("ALL".equals(account.toUpperCase())) {
			list = accountService.list();
		}else {
			Account entity = accountService.getById(account);
			list = List.of(entity);
		}
		if(CollectionUtils.isEmpty(list)) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		list.forEach(entity -> {
			if(goldCoin != null && goldCoin >= 0) {
				entity.setGoldCoin(goldCoin.toString());
			}
			if(silverCoin!= null && silverCoin >= 0) {
				entity.setSilverCoin(silverCoin.toString());
			}
			accountService.blockWithUnblock(entity);
		});
		return R.ok();
	}
	
	@GetMapping("/gidList/{account}")
	public R gidList(@PathVariable String account) {
		List<Long> list = dataService.getGidList(account);
		if(CollectionUtils.isEmpty(list)) {
			return R.error(S.ACCOUNT_ROLE_NOTFUND_ERR);
		}
		List<GidInfo> idList = gidInfoService.listByIds(list);
		List<Map<String, Object>> result = idList.stream().collect(ArrayList::new, (left, right) -> {
			String name = CharsetUtil.convert(right.getName(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
			left.add(Map.of("label", name, "value", right.getGid()));
		}, List::addAll);
		return R.of(result);
	}
	
	@GetMapping("/chargeCash")
	public R chargeCash(@RequestParam @NotBlank(message = "账号不能为空") String account,
			@RequestParam @NotNull(message = "角色名不能为空!") Long gid,
			@RequestParam @NotNull(message = "金币不能为空!") Integer cash,
			@RequestParam @NotNull(message = "代金券不能为空!") Integer vcash) {
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		GidInfo gidInfo = gidInfoService.getById(gid);
		if(gidInfo == null) {
			return R.error(S.ACCOUNT_ROLE_NOTFUND_ERR);
		}
		boolean set = false;
		if(cash > 0) {
			set = dataService.setContentCash(FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, gid), cash);
		}
		if(vcash > 0) {
			set = dataService.setContentVcash(FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, gid), vcash);
		}
		if(!set) {
			return R.error(S.ACCOUNT_CHARGE_CASH_ERR);
		}
		return R.ok();
	}
	
	@GetMapping("/giveTao")
	public R giveTao(@RequestParam @NotBlank(message = "账号不能为空") String account,
			@RequestParam @NotNull(message = "角色名不能为空!") Long gid,
			@RequestParam @NotNull(message = "道行不能为空!") Integer tao){
		Account entity = accountService.getById(account);
		if(entity == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		GidInfo gidInfo = gidInfoService.getById(gid);
		if(gidInfo == null) {
			return R.error(S.ACCOUNT_ROLE_NOTFUND_ERR);
		}
		boolean set = dataService.setContentTao(FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, gid), tao);
		if(!set) {
			return R.error(S.ROLE_GIVE_TAO_ERR);
		}
		return R.ok();
	}
	
	@PostMapping("/move")
	public R move(@RequestBody MoveModel mm) {
		Account sa = accountService.getById(mm.getSource());
		if(sa == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		Account at = accountService.getById(mm.getTarget());
		if(at == null) {
			return R.error(S.ACCOUNT_NOTFUND_ERR);
		}
		Data entity = dataService.getDataEntity(mm.getSource());
		if(entity != null) {
			return R.error(S.ACCOUNT_ROLE_OFFLINE_ERR);
		}
		ChainBuilder<MoveRoleChainHandler> builder = new ChainBuilder<>();
		MoveRoleChainHandler moveRoleChainHandler = builder.addHandler(this.applicationContext.getBean(BasicCharInfoServiceImpl.class))
				.addHandler(this.applicationContext.getBean(UserLevelChainHandler.class))
				.addHandler(this.applicationContext.getBean(LoginDataChainHandler.class)).build();
		boolean doHandler = moveRoleChainHandler.doHandler(mm);
		if(doHandler) {
			
			return R.ok();
		}
		return R.error(S.ROLE_MOVE_ERR);
	}
	
	@GetMapping("/dataList")
	public R dataList(@RequestParam @NotNull(message = "角色名不能为空!") Long gid, @RequestParam String branch) {
		GidInfo gidInfo = gidInfoService.getById(gid);
		if(gidInfo == null) {
			return R.error(S.ACCOUNT_ROLE_NOTFUND_ERR);
		}
		String name = FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, gid);
		Data entity = this.dataService.getDataByMultiId(Path.user.name(), name, branch);
		if(entity == null) {
			return R.error(S.ROLE_DATA_NOTFUND_ERR);
		}
		String content = CharsetUtil.convert(entity.getContent(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
		return R.of(content);
	}
	
	@PostMapping("/update")
	public R update(@RequestBody UpdateModel um) {
		GidInfo gidInfo = gidInfoService.getById(um.getGid());
		if(gidInfo == null) {
			return R.error(S.ACCOUNT_ROLE_NOTFUND_ERR);
		}
		String name = FastDataConvert.toPath(false, DataFormatTemplate.FORMAT_16, um.getGid());
		Data entity = this.dataService.getDataByMultiId(Path.user.name(), name, um.getBranch());
		if(entity == null) {
			return R.error(S.ROLE_DATA_NOTFUND_ERR);
		}
		entity.setContent(um.getContent());
		entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getBranch() + entity.getContent()));
		boolean update = this.dataService.updateByMultiId(entity);
		if(update) {
			this.dataBackService.backUpdateData(entity);
			return R.ok();
		}
		return R.error(S.ROLE_DATA_UPDATE_ERR);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
