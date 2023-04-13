package com.game.beibei.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.Area;
import com.game.beibei.mapper.AreaMapper;
import com.game.beibei.service.AreaService;

@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

}
