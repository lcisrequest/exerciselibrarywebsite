package com.example.lcdemo.modular.backend.service;

import com.example.lcdemo.modular.backend.model.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
    void addNewAdmin(Admin admin, int userId);

    List<Map<String, Object>> getAllAdmin(int userId, int page, int limit, String username);

    void deleteAdmin(int adminId,int userId);

    Integer getAdminCount(String username);
}
