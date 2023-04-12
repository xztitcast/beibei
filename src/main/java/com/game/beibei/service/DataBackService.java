package com.game.beibei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.entity.ddb1.DataBack;

public interface DataBackService extends IService<DataBack> {

	void backUpdateData(Data entity);

}
