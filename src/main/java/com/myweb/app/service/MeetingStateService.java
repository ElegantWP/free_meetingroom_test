package com.myweb.app.service;

/**
 * 数据统计服务（统计会议室总数、空闲中（包括未开始和已结束的会议室）、使用中、故障中）
 * @author zhanghaoaj
 */
public interface MeetingStateService {

    /**
     * 会议室总数
     * @return count
     */
    int getList();

    /**
     * 空闲中
     * @return count
     */
    int getFree();

    /**
     * 使用中
     * @return count
     */
    int getUsering();

    /**
     * 故障中
     * @return count
     */
    int getBreakdown();

}
