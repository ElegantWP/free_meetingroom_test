package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author 专门用来存储投屏信息的对象（包含分页设置）
 */
@Data
public class ScreenEntity {
    private String name; //会议室名称
    private String topic ;//会议室主题
    private String startTime; //开始时间
    private String endTime;// 结束时间
    private String state; //会议状态
}
