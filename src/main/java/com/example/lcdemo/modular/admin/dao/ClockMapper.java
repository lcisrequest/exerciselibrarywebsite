package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.Clock;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-10
 */
@Repository
public interface ClockMapper extends BaseMapper<Clock> {

    Integer selectTodayClockNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer selectTodaymIsClock(@Param("startTime") String startTime, @Param("endTime") String endTime,int userId);
}
