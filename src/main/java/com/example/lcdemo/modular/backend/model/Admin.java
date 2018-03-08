package com.example.lcdemo.modular.backend.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author itspeed
 * @since 2018-03-05
 */
public class Admin extends Model<Admin> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Admin{" +
                ", id=" + id +
                ", username=" + username +
                ", password=" + password +
                "}";
    }

    public Map<String, Object> getMapForSuperAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    public Map<String, Object> getMapForAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("password", "您的权限不足");
        return map;
    }
}
