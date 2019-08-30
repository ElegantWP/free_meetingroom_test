package com.myweb.app.service.imp;

import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.MeeingRoomMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.service.MeetingRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author  wanggqf
 * @desc    会议室接口实现类
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MeetingRoomServiceImpl implements MeetingRoomService {

    private final MeeingRoomMapper mapper;
    //新增
    @Override
    public int add(MeetingRoom room) {
        Integer rtn = 0;
        try{
            rtn =  mapper.insert(room);
        }catch (Exception e){
            log.error("新增会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("新增会议室错误");
        }
        return rtn;
    }

    //修改
    @Override
    public int update(MeetingRoom room) {
        Integer rtn = 0;
        try{
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("修改会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("修改会议室错误");
        }
        return rtn;
    }

    //删除   停用
    @Override
    public int disable(Integer id) {
        Integer rtn = 0;
        MeetingRoom room = new MeetingRoom();
        room.setId(id);
        room.setState(2);
        try{
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("停用会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("停用会议室错误");
        }
        return rtn;
    }

    //使用
    @Override
    public int use(Integer id) {
        Integer rtn = 0;
        MeetingRoom room = new MeetingRoom();
        room.setId(id);
        room.setState(1);
        try{
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("使用会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("使用会议室错误");
        }
        return rtn;
    }

    //释放会议室
    @Override
    public int release(Integer id) {
        Integer rtn = 0;
        MeetingRoom room = new MeetingRoom();
        room.setId(id);
        room.setState(0);
        try{
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("释放会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("释放会议室错误");
        }
        return rtn;
    }

    //通过id查询一条会议室信息
    @Override
    public MeetingRoom selectById(Integer id) {
        try{
            return mapper.selectById(id);
        }catch (Exception e){
            log.error("查询错误,原因:{}",e.getMessage());
            throw new ServiceException("查询出现错误");
        }

    }

}
