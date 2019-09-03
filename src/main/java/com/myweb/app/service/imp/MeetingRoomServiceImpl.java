package com.myweb.app.service.imp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.myweb.app.core.ServiceException;
import com.myweb.app.dao.MeetingRoomMapper;
import com.myweb.app.entity.MeetingRoom;
import com.myweb.app.entity.ScreenEntity;
import com.myweb.app.service.MeetingRoomService;
import com.myweb.app.utils.PageCondition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    private final MeetingRoomMapper mapper;
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
        try{
            rtn =  mapper.deleteById(id);//直接删除
        }catch (Exception e){
            log.error("停用会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("停用会议室错误");
        }
        return rtn;
    }

    //预定
    @Override
    public int order(Integer id) {
        Integer rtn = 0;
        MeetingRoom room = new MeetingRoom();
        room.setId(id);
        room.setState(3);
        try{
            QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            MeetingRoom room1 = mapper.selectOne(queryWrapper);
            if(room1.getState() != 0){
                throw new ServiceException("会议室不是可用状态，不能预定");
            }
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("使用会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("使用会议室错误");
        }
        return rtn;
    }

    //取消预定，只有预定状态下可以
    @Override
    public int unOrder(Integer id) {
        Integer rtn = 0;
        MeetingRoom room = new MeetingRoom();
        room.setId(id);
        room.setState(1);
        try{
            QueryWrapper<MeetingRoom> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",id);
            MeetingRoom room1 = mapper.selectOne(queryWrapper);
            if(room1.getState() != 3){
                throw new ServiceException("会议室不是预定状态，不能取消");
            }
            rtn =  mapper.updateById(room);
        }catch (Exception e){
            log.error("使用会议室错误,原因:{}",e.getMessage());
            throw new ServiceException("使用会议室错误");
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
            if(room1.getState() == 1){
                throw new ServiceException("会议室已经为使用状态");
            }else if(room1.getState() != 3){
                throw new ServiceException("没有预定不能直接使用会议室");
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
            if(room1.getState() != 1){
                throw new ServiceException("会议室未在使用，不能释放");
            }
            boolean flag = isOrderState(id);
            if(!flag){
                room.setState(3);
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

    //获取 投屏 信息
    @Override
    public List<ScreenEntity> getScreen(Integer layer){
        return mapper.getScreen(layer);
    }


    //判断释放后的会议室 ，处于什么状态
    private boolean isOrderState(Integer id){
        boolean flag = true;
        List<ScreenEntity> order = mapper.getOneMeetingScreen(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Date now = new Date();
        Date end = null;
        String endTime = null;
        for (ScreenEntity entity:order) {
            endTime = df.format(new Date()) + " "+entity.getEndTime();
            try {
                end = df.parse(endTime);
            } catch (ParseException e) {
                throw new ServiceException(e.getMessage());
            }
            int compareFlag = now.compareTo(end);
            if(compareFlag > 0){
                flag = false;
                break;
            }
        }

        return flag;
    }
}
