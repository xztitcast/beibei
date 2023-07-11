package com.game.beibei.controller;

import com.game.beibei.common.R;
import com.game.beibei.entity.Area;
import com.game.beibei.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author eden
 * @date 2023/7/11 21:01:01
 */
@RestController
@RequestMapping("/admin/config")
public class AdminConfigController {

    @Autowired
    private AreaService areaService;

    @GetMapping("/info")
    public R info() {
        Area entity = this.areaService.getById(1);
        return R.of(entity);
    }
}
