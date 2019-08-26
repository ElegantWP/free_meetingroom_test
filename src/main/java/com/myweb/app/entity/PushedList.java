package com.myweb.app.entity;

import com.myweb.app.dto.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 已发送消息列表的实体类
 *
 * @author chenweiat
 */
@Getter
@Setter
@Generated(value = "com.yonyou.ocm.util.codegenerator.CodeGenerator")
public class PushedList extends BaseEntity {

    /**
     * 用户ID
     */
    @Size(max = 40)
    private String userId;

    /**
     * 目标ID
     */
    @Size(max = 40)
    private String targetId;

    /**
     * CID
     */
    @Size(max = 100)
    private String cid;

    /**
     * 名称
     */
    @Size(max = 200)
    private String title;

    /**
     * 标题栏简述
     */
    @Size(max = 1000)
    private String brief;

    /**
     * 消息内容
     */
    @Size(max = 1000)
    private String content;

    /**
     * 发送时间
     */
    private Timestamp pushTime;

    /**
     * 消息类型
     */
    private Integer msgType;
    
	/**
	 * 是否已读 0：未读，1：已读
	 */
	private Integer isRead;
	
	/**
	 * 是否已发送 0：未发送，1：已经发送
	 */
	private Integer isSend;
	
    /**
     * 业务参数
     */
    @Size(max = 2000)
	private String bussinessParams;

}
