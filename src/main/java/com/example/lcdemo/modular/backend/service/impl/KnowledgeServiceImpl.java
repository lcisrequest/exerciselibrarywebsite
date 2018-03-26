package com.example.lcdemo.modular.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.admin.dao.CollectMapper;
import com.example.lcdemo.modular.admin.dao.CourseMapper;
import com.example.lcdemo.modular.admin.model.Collect;
import com.example.lcdemo.modular.admin.model.Course;
import com.example.lcdemo.modular.backend.dao.KnowledgeMapper;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    private HiguProperties higuProperties;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    CourseMapper courseMapper;

    /**
     * 新增考试大纲或课本知识
     *
     * @param knowledge
     */
    @Override
    public boolean addKnowledge(Knowledge knowledge) {
        knowledge.setCreateTime(DateUtil.getTime());
        knowledge.setUpdateTime(DateUtil.getTime());
        int num = knowledgeMapper.insert(knowledge); //新增知识点
        String problemType = knowledge.getProblemType();
        if (num > 0) {
            Course course = new Course();
            course.setName(problemType);
            course = courseMapper.selectOne(course);              //找到对应的课程信息
            course.setKnowledgeNum(course.getKnowledgeNum() + 1); //知识点数量+1
            courseMapper.updateById(course);                      //更新课程信息
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改考试大纲或课本知识
     *
     * @param knowledge
     */
    @Override
    public boolean updateKnowledge(Knowledge knowledge) {
        knowledge.setUpdateTime(DateUtil.getTime());
        int num = knowledgeMapper.updateById(knowledge);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除考试大纲或课本知识
     *
     * @param knowledgeId
     * @return
     */
    @Override
    public boolean deleteKnowledge(int knowledgeId) {
        if (knowledgeId == 0) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Knowledge k = knowledgeMapper.selectById(knowledgeId);
        String problemType = k.getProblemType();
        int num = knowledgeMapper.deleteById(knowledgeId); //删除知识点
        if (num > 0) {
            Course course = new Course();
            course.setName(problemType);
            course = courseMapper.selectOne(course);              //找到对应的课程信息
            course.setKnowledgeNum(course.getKnowledgeNum() - 1); //知识点数量-1
            courseMapper.updateById(course);                      //更新课程信息
            return true;
        } else {
            return false;
        }
    }

    /**
     * 分页获取指定类型的考试大纲或课本知识
     *
     * @param type
     * @param kind
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Knowledge> getKnowledge(int type, String kind, int page, int limit) {
        Wrapper<Knowledge> wrapper = new EntityWrapper<>();
        if (!kind.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", kind);                       //指定题目类型
        }
        if (type != 0) {
            wrapper.eq("type", type);                       //指定知识类型
        }
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<Knowledge> list = knowledgeMapper.selectPage(rowBounds, wrapper);
        return list;
    }

    /**
     * 获取指定类型的考试大纲或课本知识的数量
     *
     * @param type
     * @param kind
     * @return
     */
    @Override
    public int getKnowledgeNum(int type, String kind) {
        Wrapper<Knowledge> wrapper = new EntityWrapper<>();
        if (!kind.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("problem_type", kind);                       //指定题目类型
        }
        if (type != 0) {
            wrapper.eq("type", type);                       //指定知识类型
        }
        return knowledgeMapper.selectCount(wrapper);
    }


    /**
     * 文件上传
     *
     * @param file
     * @param req
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file, HttpServletRequest req) {
        String fileDir = higuProperties.getFileUploadPath();
        String dateDir = DateUtil.getAllTime();//时间戳
        String serviceName = UUID.randomUUID().toString().replaceAll("-", "")
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        File tempFile = new File(fileDir + dateDir + serviceName);
        if (!tempFile.getParentFile().exists()) {
            tempFile.getParentFile().mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(tempFile));
                // "d:/"+file.getOriginalFilename() 指定目录
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            String filename = higuProperties.getImgUrlPath() + "/" + dateDir + serviceName; //文件路径
            return filename;
        } else {
            System.out.println("上传失败，因为文件是空的");
            return null;
        }
    }

    /**
     * 对知识添加收藏或取消收藏
     *
     * @param knowledgeId
     * @param userId
     * @return
     */
    @Override
    public String addKnowledgeCollect(int knowledgeId, int userId) {
        Collect collect = new Collect();
        collect.setProblemType("knowledge");
        collect.setUserId(userId);
        collect.setSubjectId(knowledgeId);
        Collect c = collectMapper.selectOne(collect); //判断该用户是否收藏了该知识
        if (c == null) {
            collectMapper.insert(collect);         //若没有收藏则添加收藏
            return "收藏成功";
        } else {
            collectMapper.deleteById(c.getId());   //若收藏了则取消收藏
            return "取消成功";
        }

    }

    /**
     * 分页分类获取收藏的知识
     *
     * @param type
     * @param userId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Knowledge> getAllMyCollectKnowledge(int type, int userId, int page, int limit) {
        Wrapper<Collect> wrapper = new EntityWrapper<>();
        wrapper.eq("problem_type", "knowledge"); //problem_type为knowledge时，为收藏的知识
        wrapper.eq("user_id", userId);
        boolean notKind = false;
        if (type == 0) {        //若type为0
            notKind = true;  //则取出全部
        }
        List<Collect> listCollect = collectMapper.selectList(wrapper);
        List<Knowledge> list = new ArrayList<>();
        for (Collect c : listCollect) {
            Knowledge knowledge = knowledgeMapper.selectById(c.getSubjectId());
            if (notKind) {
                list.add(knowledge);
            } else {
                if (knowledge.getType() == type) {
                    list.add(knowledge);
                }
            }
        }
        int flag = 0;
        int count = 0;
        int offset = (page - 1) * limit;
        List<Knowledge> listKnowledge = new ArrayList<>();
        for (Knowledge k : list) {  //算法分页
            if (flag >= offset) {
                if (count < limit) {
                    listKnowledge.add(k);
                }
                count++;
            }
            flag++;
        }
        return listKnowledge;
    }

    /**
     * 分类获取我的收藏数量
     *
     * @param type
     * @param userId
     * @return
     */
    @Override
    public Integer getAllMyCollectKnowledgeCount(int type, int userId) {
        Wrapper<Collect> wrapper = new EntityWrapper<>();
        wrapper.eq("problem_type", "knowledge"); //problem_type为knowledge时，为收藏的知识
        wrapper.eq("user_id", userId);
        boolean notKind = false;
        if (type == 0) {        //若type为0
            notKind = true;  //则取出全部
        }
        List<Collect> listCollect = collectMapper.selectList(wrapper);
        List<Knowledge> list = new ArrayList<>();
        for (Collect c : listCollect) {
            Knowledge knowledge = knowledgeMapper.selectById(c.getSubjectId());
            if (notKind) {
                list.add(knowledge);
            } else {
                if (knowledge.getType() == type) {
                    list.add(knowledge);
                }
            }
        }
        return list.size();
    }

}
