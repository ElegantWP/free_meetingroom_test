package com.myweb.app.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.ScreenEntity;
import com.myweb.app.utils.PageCondition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author  wanggqf
 * @desc    会议室接口
 */
@Service
public interface MeetingRoomService {
    //添加会议室
    int add(MeetingRoom room);

    //修改会议室
    int update(MeetingRoom room);

    //停用会议室
    int disable(Integer id);

    //预定
    int order(Integer id);

    //取消预定
    int unOrder(Integer id);

    //使用会议室
    int use(Integer id);

    //释放会议室
    int release(Integer id);

    //查询。。。
    //通过id查询一条
    MeetingRoom selectById(Integer id);


     //条件 分页查询
     IPage<MeetingRoom> selectPage(PageCondition pageCondition);

     //获取 投屏信息
     List<ScreenEntity>  getScreen(Integer layer);
}