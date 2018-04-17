package com.example.lcdemo.modular.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.lcdemo.modular.admin.model.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itspeed
 * @since 2018-03-02
 */
@Repository
public interface TestMapper extends BaseMapper<Test> {
    List<Integer> subjectIsInTest(String subjectId); //参数要拼接% %
}
