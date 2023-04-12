package com.game.beibei.system;

import com.alibaba.fastjson2.JSON;

public class Sys {

	/**
     * 服务器名称
     */
    private String computerName;
    /**
     * 服务器Ip
     */
    private String computerIp;
    /**
     * 操作系统
     */
    private String osName;
    /**
     * 系统架构
     */
    private String osArch;
    
    public String getComputerName() {        return computerName;    }
    public void setComputerName(String computerName) {        this.computerName = computerName;    }
    public String getComputerIp() {        return computerIp;    }
    public void setComputerIp(String computerIp) {        this.computerIp = computerIp;    }
    public String getOsName() {        return osName;    }
    public void setOsName(String osName) {        this.osName = osName;    }
    public String getOsArch() {        return osArch;    }
    public void setOsArch(String osArch) {        this.osArch = osArch;    }
    
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

    
}
