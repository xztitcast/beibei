package com.game.beibei.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.mdb1.ServerStatus;
import com.game.beibei.mapper.ServerStatusMapper;
import com.game.beibei.service.ServerStatusService;
 
@DS("mdb1")
@Service("serverStatusService")
public class ServerStatusServiceImpl extends ServiceImpl<ServerStatusMapper, ServerStatus> implements ServerStatusService {

}
