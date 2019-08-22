package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

@Data
@TableName("user")
public class User {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "username")
    private String username;

    private String password;

    private String nickName;

    private Integer sex;
    @JsonFormat(pattern="yyyyMMddHHmmssSSS", timezone="GMT+8")
    private Date registerDate;
}