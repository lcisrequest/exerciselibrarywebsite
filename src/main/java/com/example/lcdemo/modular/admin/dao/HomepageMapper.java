package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.Homepage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-09
 */
@Repository
public interface HomepageMapper extends BaseMapper<Homepage> {
    List<Homepage> selectTitleHomePage(@Param("infoType")String infoType,@Param("title")String title,@Param("offset") int offset,@Param("limit") int limit);

    Integer selectTitleHomePageCount(@Param("infoType")String infoType,@Param("title")String title);
}
