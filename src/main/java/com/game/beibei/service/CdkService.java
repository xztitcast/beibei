package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.common.P;
import com.game.beibei.entity.Cdk;

public interface CdkService extends IService<Cdk> {

	P<Cdk> getCdkList(int pageNum, int pageSize, Integer type, String name, Integer status);
}
