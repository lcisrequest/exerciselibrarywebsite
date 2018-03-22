package com.example.lcdemo.modular.admin.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author itspeed
 * @since 2018-03-22
 */
@TableName("user_subjectnum")
public class UserSubjectnum extends Model<UserSubjectnum> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 题目总数
     */
    @TableField("subject_num")
    private Integer subjectNum;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 最后更新时间
     */
    @TableField("create_time")
    private String createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(Integer subjectNum) {
        this.subjectNum = subjectNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserSubjectnum{" +
        ", id=" + id +
        ", subjectNum=" + subjectNum +
        ", userId=" + userId +
        ", createTime=" + createTime +
        "}";
    }
}
