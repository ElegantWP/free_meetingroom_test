package com.myweb.app.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Generated;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * 已发送消息列表的数据传输对象类
 *
 * @author chenweiat
 */
@Getter
@Setter
@ToString
@Generated(value = "com.yonyou.ocm.util.codegenerator.CodeGenerator")
public class PushedListDto {

    /**
     * 用户ID
     */
    @Size(max = 40)
    private String userId;

    /**
     * 会议室ID
     */
    private Integer roomId;

    /**
     * Token
     */
    private String accesstoken;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 用户ID
     */
    @Size(max = 40)
    private String userCode;

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
    @Size(max = 200)
    private String brief;

    /**
     * 消息内容
     */
    @Size(max = 500)
    private String content;

    /**
     * 发送时间
     */
    private Timestamp pushTime;

    /**
     * 消息类型
     */
    @Size(max = 40)
    private String msgType;

    /**
     * 消息类别
     */
    @Size(max = 40)
    private String msgCatalogId;
    
    /**
     * 消息类别编码
     */
    @Size(max = 40)
    private String msgCatalogCode;
    
    /**
     * 消息类别名称
     */
    @Size(max = 100)
    private String msgCatalogName;
    
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
