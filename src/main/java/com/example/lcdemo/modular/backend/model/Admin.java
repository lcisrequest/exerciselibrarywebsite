package com.example.lcdemo.modular.backend.model;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * @author lc
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
    /**
     * 创建时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private String updateTime;
    /**
     * 是否是超级管理员 1是 0不是
     */
    @TableField("is_super")
    private Integer isSuper;


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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(Integer isSuper) {
        this.isSuper = isSuper;
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
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isSuper=" + isSuper +
                "}";
    }

    public Map<String, Object> getMapForSuperAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("password", password);
        map.put("createTime", createTime);
        map.put("updateTime", updateTime);
        map.put("isSuper", isSuper);
        return map;
    }

    public Map<String, Object> getMapForAdmin() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("username", username);
        map.put("password", "您的权限不足");
        map.put("createTime", createTime);
        map.put("updateTime", updateTime);
        map.put("isSuper", isSuper);
        return map;
    }
}
