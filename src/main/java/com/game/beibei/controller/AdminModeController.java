package com.game.beibei.controller;

import cn.hutool.core.util.CharsetUtil;
import com.game.beibei.common.R;
import com.game.beibei.common.S;
import com.game.beibei.common.utils.ChecksumUtil;
import com.game.beibei.entity.Mode;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.enums.Path;
import com.game.beibei.model.ItemModel;
import com.game.beibei.model.ScoreModel;
import com.game.beibei.service.DataService;
import com.game.beibei.service.ModeService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/admin/mode")
public class AdminModeController {

    @Autowired
    private ModeService modeService;

    @Autowired
    private DataService dataService;

    @PostMapping("/item")
    public R item(@Valid @RequestBody ItemModel im){
        Mode entity = this.modeService.getById(im.getId());
        if(entity == null){
            return R.error("模式不能存在!");
        }
        Data data = this.dataService.getDataEntity(im.getAccount());
        if(data != null){
            return R.error(S.ACCOUNT_ROLE_OFFLINE_ERR);
        }
        String content = entity.getContent().replace("#NAME", im.getName());
        String name = CharsetUtil.convert(entity.getName(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
        if(name.contains("不可叠加")){
            if(im.getNum() > 80){
                return R.error("发送失败,不可叠加物品数量必须小于等于80");
            }
            for (int i = 0; i < im.getNum(); i++) {
                dataService.setContentItem(im.getGid(), content);
            }
        }else{
            dataService.setContentItem(im.getGid(), content.replace("#NUM", im.getNum()+""));
        }
        return R.ok();
    }

    @PostMapping("/score")
    public R score(@Valid @RequestBody ScoreModel sm){
        if(this.dataService.getDataEntity(sm.getAccount()) != null){
            return R.error(S.ACCOUNT_ROLE_OFFLINE_ERR);
        }
        Data data = this.dataService.getDataByMultiId(Path.user.name(), sm.getGid(), "");
        if(data != null){
            return R.error(S.ACCOUNT_ROLE_OFFLINE_ERR);
        }
        int score = sm.getNum();
        String content = data.getContent();
        if (!content.contains("total_score\":0") && !content.contains("task_score\":([\"total\":")) {
            content = content.replace("\"first_login_mac", "\"total_score\":0,\"first_login_mac");
        }
        if (content.contains("total_score\":0")) {
            data.setContent(content.replace("total_score\":0", "task_score\":([\"total\":" + score + ",\"active_time\":1913292377,])"));
        }else{
            int index = content.indexOf("task_score\":([\"total\":");
            if (index < 0) {
                log.info("发送失败 : {}", content);
                return R.error("发送失败, 请先做任务获得1积分大使积分!");
            }
            String tcontent = content.substring(index + 22);
            int i = tcontent.indexOf(",");
            String oldScore = tcontent.substring(0, i);
            int newScore = Integer.valueOf(oldScore) + score;
            if (newScore > 2000000000) {
                newScore = 2000000000;
            }
            data.setContent(content.replace("task_score\":([\"total\":" + oldScore, "task_score\":([\"total\":" + newScore));
            data.setChecksum(ChecksumUtil.checksum(data.getPath() + data.getName() + data.getContent()));
            this.dataService.updateByMultiId(data);
        }
        return R.ok();
    }
}

