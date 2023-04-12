package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.common.P;
import com.game.beibei.entity.adball.Server;

public interface ServerService extends IService<Server> {

	P<Server> getServerList(int pageNum, int pageSize);
}
