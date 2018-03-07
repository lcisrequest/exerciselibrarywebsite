package com.example.lcdemo.modular.backend.service.impl;


import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.backend.dao.AdminMapper;
import com.example.lcdemo.modular.backend.model.Admin;
import com.example.lcdemo.modular.backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    /**
     * 新增管理员
     *
     * @param admin
     */
    @Override
    public void addNewAdmin(Admin admin) {
        String username = admin.getUsername();
        String password = admin.getPassword();
        if (username == null || password == null || "".equals(username) || "".equals(password)) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        Admin a = adminMapper.selectOne(admin);
        if (a != null) {
            throw new LcException(LcExceptionEnum.HAS_REGISTER);
        }
        adminMapper.insert(admin);
    }
}
