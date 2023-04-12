package com.game.beibei.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game.beibei.common.P;
import com.game.beibei.common.R;
import com.game.beibei.entity.adball.Server;
import com.game.beibei.entity.mdb1.ServerStatus;
import com.game.beibei.service.ServerService;
import com.game.beibei.service.ServerStatusService;
import com.game.beibei.system.ServerInfo;

import cn.hutool.core.util.CharsetUtil;

@RestController
@RequestMapping("/admin/server")
public class AdminServerController {
	
	@Autowired
	private ServerService serverService;
	
	@Autowired
	private ServerStatusService serverStatusService; 

	@GetMapping("/list")
	public R list(@RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize) {
		P<Server> p = serverService.getServerList(pageNum, pageSize);
		p.getPageList().stream().forEach(server -> {
			String encode = CharsetUtil.convert(server.getServer(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
			ServerStatus serverStatus = serverStatusService.getById(encode);
			if(serverStatus == null) {
				server.setOnline(0);
			}else {
				server.setOnline(serverStatus.getOnline());
			}
		});
		return R.of(p);
	}
	
	@PostMapping("/info")
	public R info() {
		return R.of(new ServerInfo());
	}

}
