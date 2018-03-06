package com.example.lcdemo.modular.backend.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.backend.dao.KnowledgeMapper;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.UUID;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {
    @Autowired
    KnowledgeMapper knowledgeMapper;
    @Autowired
    private HiguProperties higuProperties;

    /**
     * 新增考试大纲或课本知识
     *
     * @param knowledge
     */
    @Override
    public boolean addKnowledge(Knowledge knowledge) {
        knowledge.setCreateTime(DateUtil.getTime());
        knowledge.setUpdateTime(DateUtil.getTime());
        int num = knowledgeMapper.insert(knowledge);
        if (num > 0) {
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
        int num = knowledgeMapper.deleteById(knowledgeId);
        if (num > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 分页获取指定类型的考试大纲或课本知识
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
            wrapper.eq("kind", kind);                       //指定题目类型
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
     * @param type
     * @param kind
     * @return
     */
    @Override
    public int getKnowledgeNum(int type,String kind){
        Wrapper<Knowledge> wrapper = new EntityWrapper<>();
        if (!kind.equals("all")) {                      //当类型为all时，为不指定类型
            wrapper.eq("kind", kind);                       //指定题目类型
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
            String filename = higuProperties.getImgUrlPath() + "/" + dateDir + serviceName;
            return filename;
        } else {
            System.out.println("上传失败，因为文件是空的");
            return null;
        }
    }

}
