package com.myweb.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import javafx.beans.DefaultProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author weipan
 * @date 2019/9/2 21:53
 * @description
 */
@Getter
@Setter
@ToString
@TableName("meeting_record")
public class MeetingRecord {

  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;
  @TableField("creator_id")
  private String creatorId;
  @TableField("meetingroom_id")
  private String meetingroomId;
  @TableField("reservation_time")
  private String reservationTime;
  @TableField("topic")
  private String topic;
  @TableField("start_time")
  private String startTime;
  @TableField("end_time")
  private String endTime;
  @TableField("person_count")
  private String personCount;
  @TableField("involved_persons")
  private String involvedPersons;
  @TableField("meeting_content")
  private String meetingContent;
  @TableField("remarks")
  private String remarks;

}
