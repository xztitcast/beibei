package com.game.beibei.common;

/**
 * 状态枚举
 * @author eden
 * 2022年5月11日
 */
public enum S {

	SUCCESS(0, "成功"),
	
	ERROR(-1, "系统异常稍后重试"),
	
	CODE_EXPIRE(1000, "验证码已失效!"),
	
	CODE_ERROR(1001, "验证码错误!"),
	
	USER_PWD_ERROR(1002, "账号或密码错误!"),
	
	USER_INACTIVE(1003, "账号已被禁用!"),
	
	ACCOUNT_NOTFUND_ERR(2000, "账号不存在!"),
	
	ACCOUNT_BLOCKED_ERR(2001, "账号已封禁, 请勿重复封禁操作!"),
	
	ACCOUNT_UNBLOCKED_ERR(2002, "账号未封禁, 请勿重复解封操作!"),
	
	ACCOUNT_BLOCKED_MSG_ERR(2003, "账号封禁的原因不能为空!"),
	
	ACCOUNT_REGISTERED_ERR(2004, "账号已注册, 请更换账号!"),
	
	ACCOUNT_REGISTER_ERR(2005, "账号注册失败, 请更换账号!"),
	
	ACCOUNT_PASSWORD_UPDATE_ERR(2006, "账号对应密码修改失败, 请重新操作!"),
	
	ACCOUNT_PRIVILEGE_UPDATE_ERR(2007, "账号对应的权限修改失败, 请重新操作!"),
	
	ACCOUNT_CHARGE_COIN_ERR(2008, "账号充值元宝失败, 请重新操作!"),
	
	ACCOUNT_CHARGE_CASH_ERR(2009, "账号充值金币失败, 请重新操作!"),
	
	ACCOUNT_ROLE_NOTFUND_ERR(2010, "账号对应的角色不存在, 请检查账号!"),
	
	ACCOUNT_ROLE_OFFLINE_ERR(2011, "账号对应的角色在线, 请先下线!"),
	
	ROLE_REGISTERED_ERR(3000, "角色名已注册, 请更换角色名!"),
	
	ROLE_GIVE_TAO_ERR(3001, "赠送角色道行失败, 请重新操作!"),
	
	ROLE_MOVE_ERR(3002, "角色转移失败,请重新操作!"),
	
	ROLE_DATA_NOTFUND_ERR(3003, "数据获取失败,请检查角色名!"),
	
	ROLE_DATA_UPDATE_ERR(3004, "数据修改失败, 请检查角色名!");
	
	private int value;
	
	private String message;
	
	S(int value, String message){
		this.value = value;
		this.message = message;
	}

	public int getValue() {
		return value;
	}

	public String getMessage() {
		return message;
	}
}
