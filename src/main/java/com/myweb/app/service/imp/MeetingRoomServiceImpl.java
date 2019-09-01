package com.myweb.app.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.MeeingRoomMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.ScreenEntity;
import com.myweb.app.service.MeetingRoomService;
import com.myweb.app.utils.PageCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
            QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",room.getName());
            MeetingRoom room1 = mapper.selectOne(queryWrapper);
            if(room1 != null){
                throw new ServiceException("会议室名称已经存在！请重新输入");
            }
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
            rtn =  mapper.deleteById(id);//直接删除
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
            QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            MeetingRoom room1 = mapper.selectOne(queryWrapper);
            if(room.getState() == 1){
                throw new ServiceException("会议室已经为使用状态");
            }
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
            QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            MeetingRoom room1= mapper.selectOne(queryWrapper);
            if(room1.getState() == 0){
                throw new ServiceException("会议室已经为空闲状态");
            }
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

    //按条件查询信息，并进行分页
    @Override
    public IPage<MeetingRoom> selectPage(PageCondition pageCondition){
        Page<MeetingRoom> page = new Page<>();
        if(pageCondition.getCurrentPage() != null && pageCondition.getCurrentPage().toString().trim().length() != 0){
            page.setCurrent(pageCondition.getCurrentPage());
        }else{
            page.setCurrent(1);
        }
        page.setSize(pageCondition.getSize());
        QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
        if(pageCondition.getName() != null && pageCondition.getName().toString().trim().length() != 0){
            queryWrapper.eq("name",pageCondition.getName());
        }
        if(pageCondition.getActive() != null && pageCondition.getActive().toString().trim().length() != 0){
            queryWrapper.or().eq("state",pageCondition.getActive());
        }
        if(pageCondition.getCapacity() != null && pageCondition.getCapacity().toString().length() != 0){
            queryWrapper.ge("max_capacity",pageCondition.getCapacity());
        }
        if(pageCondition.getStates() != null){
            for (Integer state:pageCondition.getStates()) {
                queryWrapper.or().eq("state",state);
            }
        }
        IPage<MeetingRoom> rtn = mapper.selectPage(page,queryWrapper);
        return rtn;
    }

    //分页获取 投屏 信息
    public IPage<ScreenEntity> getScreen(Integer layer, Integer currentPage){
        Page<ScreenEntity> page = new Page<>();
        if(currentPage != null && currentPage.toString().trim().length() != 0){
            page.setCurrent(currentPage);
        }else{
            page.setCurrent(1);
        }
//        Page<ScreenEntity> page1 = new Page<ScreenEntity>(page.getCurrent(),page.getSize());
        return page.setRecords(this.mapper.getScreen(page,layer));
    }
}
