package com.game.beibei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author eden
 * @date 2023/4/20 21:59:59
 */
@Getter
@Setter
@TableName(value = "tb_mode")
public class Mode implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String path;

    private String name;

    private String content;
}
