package com.example.lcdemo.modular.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.ForumsMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.model.Forums;
import com.example.lcdemo.modular.admin.model.Like;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.backend.service.BackendForumsService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BackendForumsServiceImpl implements BackendForumsService {
    @Autowired
    ForumsMapper forumsMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 后台分页获取所有讨论
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllForums(int page, int limit) {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        wrapper.orderBy("is_top", false);   //置顶优先
        wrapper.orderBy("create_time", false); //再按时间排序
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Forums> list = forumsMapper.selectPage(rowBounds, wrapper); //分页获取
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Forums f : list) {
            Map<String, Object> map = new HashMap<>();
            int userId = f.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(userId);
            map.put("nickName", userInfo.getNickname()); //获取用户信息
            map.put("userImg", userInfo.getUserimg());
            map.put("forums", f);
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获取所有讨论的数量
     *
     * @return
     */
    @Override
    public Integer getAllForumsNum() {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        return forumsMapper.selectCount(wrapper);
    }

    /**
     * 置顶讨论
     *
     * @param forumsId
     */
    @Override
    public void topForums(int forumsId) {
        Forums forums = forumsMapper.selectById(forumsId);
        if (forums == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        forums.setIsTop("1");           //置顶,设为1
        forumsMapper.updateById(forums);
    }


    /**
     * 取消置顶讨论
     *
     * @param forumsId
     */
    @Override
    public void notTopForums(int forumsId) {
        Forums forums = forumsMapper.selectById(forumsId);
        if (forums == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        forums.setIsTop("0");           //取消置顶,设为0
        forumsMapper.updateById(forums);
    }


    /**
     * 删除讨论
     * @param forumsId
     */
    @Override
    public void deleteForums(int forumsId) {
        Forums forums = forumsMapper.selectById(forumsId);
        if (forums == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        forumsMapper.deleteById(forumsId);
    }

}
