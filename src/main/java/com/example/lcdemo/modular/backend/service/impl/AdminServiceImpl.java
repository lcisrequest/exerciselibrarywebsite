package com.example.lcdemo.modular.backend.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.modular.backend.dao.AdminMapper;
import com.example.lcdemo.modular.backend.model.Admin;
import com.example.lcdemo.modular.backend.service.AdminService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void addNewAdmin(Admin admin, int userId) {
        Admin zheAdmin = adminMapper.selectById(userId);
        if (!zheAdmin.getUsername().equals("admin")) {
            throw new LcException(LcExceptionEnum.DONNOT_HAVE_PAWER);
        }
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

    /**
     * 分页获取所有管理员的信息
     *
     * @param userId
     * @param page
     * @param limit
     * @param username
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllAdmin(int userId, int page, int limit, String username) {
        Admin zheAdmin = adminMapper.selectById(userId);
        boolean isSuperAdmin = false;
        if (zheAdmin.getUsername().equals("admin")) {   //用户名为admin的管理员为超级管理员
            isSuperAdmin = true;
        }
        Wrapper<Admin> wrapper = new EntityWrapper<>();
        if (!username.equals("")) {
            wrapper.eq("username", username);
        }
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);//分页
        List<Admin> listAdmin = adminMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Admin a : listAdmin) {
            if (isSuperAdmin) {
                Map<String, Object> map = a.getMapForSuperAdmin(); //超级管理员可以查看密码
                list.add(map);
            } else {
                Map<String, Object> map = a.getMapForAdmin();   //普通管理员不能查看密码
                list.add(map);
            }
        }
        return list;
    }

    /**
     * 搜索的管理员的数量
     *
     * @param username
     * @return
     */
    @Override
    public Integer getAdminCount(String username) {
        Wrapper<Admin> wrapper = new EntityWrapper<>();
        if (!username.equals("")) {
            wrapper.eq("username", username);
        }
        return adminMapper.selectCount(wrapper);
    }

    /**
     * 删除管理员
     *
     * @param adminId
     */
    @Override
    public void deleteAdmin(int adminId, int userId) {
        Admin zheAdmin = adminMapper.selectById(adminId);
        if(zheAdmin==null){
            throw new LcException(LcExceptionEnum.ADMIN_NOT_EXIST);//管理员不存在
        }
        if (zheAdmin.getUsername().equals("admin")) {
            throw new LcException(LcExceptionEnum.SUPERADMIN_CANNOT_DELETE);//不能删除超级管理员
        }
        Admin zheAdmins = adminMapper.selectById(userId);
        if (!zheAdmins.getUsername().equals("admin")) {
            throw new LcException(LcExceptionEnum.DONNOT_HAVE_PAWER); //只有超级管理员才能删除管理员
        }
        adminMapper.deleteById(adminId);
    }
}
