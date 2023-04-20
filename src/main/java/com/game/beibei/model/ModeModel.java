package com.game.beibei.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author eden
 * @date 2023/4/20 22:06:06
 */
@Getter
@Setter
public class ModeModel implements Serializable {

    @NotNull(message = "id不能为空!")
    private Integer id;

    @NotBlank(message = "道具名称不能为空!")
    private String name;

    @NotNull(message = "道具数量不能为空!")
    private Integer num;

    @NotBlank(message = "账号不能为空!")
    private String account;

    @NotBlank(message = "角色名不能为空!")
    private String gid;
}
