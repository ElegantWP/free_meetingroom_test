package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.Data;

/**
 * @author  wanggqf
 * @desc 会议室实体类
 */

@Data
@TableName("meetingroom")
public class MeetingRoom {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;  //主键
    @TableField(value = "name")
    private String name;  //会议室名称
    @TableField(value = "location")
    private String location;   //位置
    @TableField(value = "equipment")
    private String equipment;   //会议室设备
    @TableField(value = "max_capacity")
    private Integer maxCapacity; //会议室最大可容纳人数
    @TableField(value = "type")
    private Integer type; //会议室类型。0：玻璃隔间；1：小型会议室；2：大型会议室
    @TableField(value = "state")
    private Integer state;//会议室状态。0：可用；1：使用中；2：已暂停使用
}
