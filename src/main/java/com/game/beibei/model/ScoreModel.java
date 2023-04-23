package com.game.beibei.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author eden
 * @date 2023/4/23 21:25:25
 */
@Getter
@Setter
public class ScoreModel {

    @NotNull(message = "道具数量不能为空!")
    protected Integer num;

    @NotBlank(message = "账号不能为空!")
    protected String account;

    @NotBlank(message = "角色名不能为空!")
    protected String gid;
}
