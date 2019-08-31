package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.myweb.app.enums.UserPermissionsTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author nilg
 * @since 2019/8/31 12:28
 */
@Data
@TableName("permission")
public class UserPermissions {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField(value = "iType")
    private UserPermissionsTypeEnum type;
    @TableField(value = "iUserId")
    private Long userId;
    @TableField(value = "dCreatedTime")
    private LocalDateTime createdTime;
    @TableField(value = "dModifyTime")
    private LocalDateTime modifyTime;

}
