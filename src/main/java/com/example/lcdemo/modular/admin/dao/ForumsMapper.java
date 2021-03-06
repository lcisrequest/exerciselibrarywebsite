package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.Forums;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-14
 */
@Repository
public interface ForumsMapper extends BaseMapper<Forums> {

    Integer selectTodayForumsNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
