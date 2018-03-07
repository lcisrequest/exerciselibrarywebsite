package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.ErrorSubject;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-07
 */
@Repository
public interface ErrorSubjectMapper extends BaseMapper<ErrorSubject> {
    List<ErrorSubject> getMyError(@Param("problemType") String problemType,@Param("userId") int userId,@Param("offset") int offset,@Param("limit") int limit);
}
