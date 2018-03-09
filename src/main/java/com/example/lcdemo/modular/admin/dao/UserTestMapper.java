package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.UserTest;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-02
 */
@Repository
public interface UserTestMapper extends BaseMapper<UserTest> {

    List<Map<String, Object>> selectUserTest(@Param("testType") String testType, @Param("problemType") String problemType, @Param("username") String username
            , @Param("offset") int offset, @Param("limit") int limit);

    Integer selectUserTestCount(@Param("testType") String testType, @Param("problemType") String problemType, @Param("username") String username);

    List<UserTest> selectUserTestRank(@Param("testId") int testId,@Param("startTime") String startTime);
}
