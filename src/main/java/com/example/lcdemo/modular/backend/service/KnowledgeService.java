package com.example.lcdemo.modular.backend.service;

import com.example.lcdemo.modular.backend.model.Knowledge;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface KnowledgeService {
    boolean addKnowledge(Knowledge knowledge);

    String fileUpload(MultipartFile file, HttpServletRequest req);

    boolean updateKnowledge(Knowledge knowledge);

    boolean deleteKnowledge(int knowledgeId);

    List<Knowledge> getKnowledge(int type, String kind, int page, int limit);

    int getKnowledgeNum(int type,String kind);
}
