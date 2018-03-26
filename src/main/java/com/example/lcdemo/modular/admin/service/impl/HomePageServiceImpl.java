package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.admin.dao.HomepageMapper;
import com.example.lcdemo.modular.admin.model.Homepage;
import com.example.lcdemo.modular.admin.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomePageServiceImpl implements HomepageService {
    @Autowired
    HomepageMapper homepageMapper;

    /**
     * 新增首页信息
     *
     * @param homepage
     */
    @Override
    public void addHomePage(Homepage homepage) {
        if (homepage.getInfoType() == null || "".equals(homepage.getInfoType()) || homepage.getTitle() == null || "".equals(homepage.getTitle())) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        homepageMapper.insert(homepage); //新增首页
    }

    /**
     * 删除首页信息
     *
     * @param homepageId
     */
    @Override
    public void deleteHomePage(int homepageId) {
        homepageMapper.deleteById(homepageId);
    }

    /**
     * 更改首页信息
     *
     * @param homepage
     */
    @Override
    public void updateHomePage(Homepage homepage) {
        if (homepage.getId() == 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        homepageMapper.updateById(homepage);
    }

    /**
     * 分页搜索首页信息
     * @param infoType
     * @param title
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Homepage> getHomePage(String infoType, String title, int page, int limit) {
        if(infoType.equals("all")){
            infoType = "%";
        }
        if(title.equals("")){
            title = "%";
        }
        List<Homepage> list = homepageMapper.selectTitleHomePage(infoType, title, (page - 1) * limit, limit);//分页搜索
        return list;
    }

    /**
     * 获取首页信息数量
     * @param infoType
     * @param title
     * @return
     */
    @Override
    public Integer getHomePageCount(String infoType, String title){
        if(infoType.equals("all")){
            infoType = "%";
        }
        if(title.equals("")){
            title = "%";
        }
        return homepageMapper.selectTitleHomePageCount(infoType,title);
    }

    /**
     * 获取所有首页信息
     * @return
     */
    @Override
    public List<Homepage> getAllHomepage(){
        Wrapper<Homepage> wrapper = new EntityWrapper<>();
        List<Homepage> list = homepageMapper.selectList(wrapper); //获取所有
        return list;
    }
}
