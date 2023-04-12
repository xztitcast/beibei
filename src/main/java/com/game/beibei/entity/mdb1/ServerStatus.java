package com.game.beibei.entity.mdb1;

import java.nio.charset.Charset;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import cn.hutool.core.util.CharsetUtil;

@TableName(value = "server_status")
public class ServerStatus {

	@TableId(type = IdType.INPUT)
	private String server;
	
	private String dist;
	
	private Integer available;
	
	private Integer online;
	
	private Integer maxUser;
	
	private Integer cpuCost;
	
	private Date reportTime;

	public String getServer() {
		return server == null || server.isBlank() ? server : new String(server.getBytes(Charset.forName(CharsetUtil.ISO_8859_1)), Charset.forName(CharsetUtil.GBK));
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public Integer getMaxUser() {
		return maxUser;
	}

	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}

	public Integer getCpuCost() {
		return cpuCost;
	}

	public void setCpuCost(Integer cpuCost) {
		this.cpuCost = cpuCost;
	}

	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	
}
