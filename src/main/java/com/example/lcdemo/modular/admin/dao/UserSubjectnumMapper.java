package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.UserSubjectnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-22
 */
@Repository
public interface UserSubjectnumMapper extends BaseMapper<UserSubjectnum> {
    List<Map<String, Object>> selectSubjectRank();

    List<Map<String, Object>> selectTodaySubhectRank(@Param("startTime")String startTime);
}
