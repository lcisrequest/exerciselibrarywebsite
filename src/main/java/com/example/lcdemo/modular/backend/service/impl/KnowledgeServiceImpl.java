package com.example.lcdemo.modular.backend.service.impl;

import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.config.properties.HiguProperties;
import com.example.lcdemo.modular.backend.dao.KnowledgeMapper;
import com.example.lcdemo.modular.backend.model.Knowledge;
import com.example.lcdemo.modular.backend.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
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
