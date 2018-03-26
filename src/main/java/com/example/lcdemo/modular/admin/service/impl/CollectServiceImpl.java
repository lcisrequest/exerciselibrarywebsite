package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.CollectMapper;
import com.example.lcdemo.modular.admin.dao.SubjectMapper;
import com.example.lcdemo.modular.admin.model.Collect;
import com.example.lcdemo.modular.admin.model.Subject;
import com.example.lcdemo.modular.admin.service.CollectService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    SubjectMapper subjectMapper;

    /**
     * 收藏题目
     *  @param subjectId
     * @param userId
     */
    @Override
    public String addCollect(int subjectId, int userId) {
        Subject subject = subjectMapper.selectById(subjectId);
        if (subject == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        String problemType = subject.getProblemType();
        Collect collect = new Collect();
        collect.setProblemType(problemType);
        collect.setSubjectId(subjectId);
        collect.setUserId(userId);
        Collect c = collectMapper.selectOne(collect);//判断是否收藏过该题
        if (c == null) { //若没有收藏过
            collectMapper.insert(collect); //则收藏这道题
            return "收藏成功";
        } else {         //若收藏过
            collectMapper.deleteById(c);   //则取消该收藏
            return "取消成功";
        }
    }


    /**
     * 分页获取指定类型的我的收藏题目
     *
     * @param problemType
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllMYCollect(String problemType, int userId, int page, int limit) {
        if (problemType == null || "".equals(problemType) || page == 0 || limit == 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Wrapper<Collect> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {   //当参数为all时，课程类型为所有
            wrapper.eq("problem_type", problemType);
        }
        wrapper.eq("user_id", userId);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Collect> list = collectMapper.selectPage(rowBounds, wrapper);//分页查询
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Collect c : list) {
            Map<String, Object> map = c.getMap();
            Subject subject = subjectMapper.selectById(c.getSubjectId()); //循环查询出题目
            map.put("subject", subject.getMap());
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获取指定类型的收藏题目的数量
     *
     * @param problemType
     * @param userId
     * @return
     */
    @Override
    public Integer getAllMyCollectNum(String problemType, int userId) {
        if (problemType == null || "".equals(problemType)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Wrapper<Collect> wrapper = new EntityWrapper<>();
        if (!problemType.equals("all")) {
            wrapper.eq("problem_type", problemType);
        }
        wrapper.eq("user_id", userId);
        return collectMapper.selectCount(wrapper);
    }
}
