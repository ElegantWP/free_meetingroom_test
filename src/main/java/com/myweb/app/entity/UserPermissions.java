package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author nilg
 * @since 2019/8/31 12:28
 */
public class UserPermissions {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "iType")
    private String type;
    @TableField(value = "iUserId")
    private String userId;
    @TableField(value = "dCreatedTime")
    private LocalDateTime createdTime;
    @TableField(value = "dModifyTime")
    private LocalDateTime modifyTime;

}
