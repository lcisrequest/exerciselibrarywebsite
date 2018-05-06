package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-01
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    Integer selectRegisterNum(@Param("startTime") String startTime, @Param("endTime") String endTime);

    Integer selectUserIsBan(@Param("username") String username);
}
