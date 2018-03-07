package com.example.lcdemo.modular.admin.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
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
 * @since 2018-03-07
 */
public class Collect extends Model<Collect> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 题目id
     */
    @TableField("subject_id")
    private Integer subjectId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 题目类型
     */
    @TableField("problem_type")
    private String problemType;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Collect{" +
                ", id=" + id +
                ", subjectId=" + subjectId +
                ", userId=" + userId +
                ", problemType=" + problemType +
                "}";
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("subjectId", subjectId);
        map.put("userId", userId);
        map.put("problemType", problemType);
        return map;
    }
}
