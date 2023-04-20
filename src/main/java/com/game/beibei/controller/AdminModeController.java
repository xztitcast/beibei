package com.game.beibei.controller;

import cn.hutool.core.util.CharsetUtil;
import com.game.beibei.common.R;
import com.game.beibei.common.S;
import com.game.beibei.entity.Mode;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.model.ModeModel;
import com.game.beibei.service.DataService;
import com.game.beibei.service.ModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author eden
 * @date 2023/4/20 22:05:05
 */
@RestController
@RequestMapping("/admin/mode")
public class AdminModeController {

    @Autowired
    private ModeService modeService;

    @Autowired
    private DataService dataService;

    @PostMapping("/item")
    public R item(@Valid @RequestBody ModeModel mm){
        Mode entity = this.modeService.getById(mm.getId());
        if(entity == null){
            return R.error("模式不能存在!");
        }
        Data data = this.dataService.getDataEntity(mm.getAccount());
        if(data != null){
            return R.error(S.ACCOUNT_ROLE_OFFLINE_ERR);
        }
        String content = entity.getContent().replace("#NAME", mm.getName());
        String name = CharsetUtil.convert(entity.getName(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
        if(name.contains("不可叠加")){
            if(mm.getNum() > 80){
                return R.error("发送失败,不可叠加物品数量必须小于等于80");
            }
            for (int i = 0; i < mm.getNum(); i++) {
                dataService.setContentItem(mm.getGid(), content);
            }
        }else{
            dataService.setContentItem(mm.getGid(), content.replace("#NUM", mm.getNum()+""));
        }
        return R.ok();
    }
}
