package com.game.beibei.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.Mode;
import com.game.beibei.mapper.ModeMapper;
import com.game.beibei.service.ModeService;
import org.springframework.stereotype.Service;

/**
 * @author eden
 * @date 2023/4/20 22:03:03
 */
@DS("master")
@Service("modeService")
public class ModeServiceImpl extends ServiceImpl<ModeMapper, Mode> implements ModeService {
}
