package com.example.lcdemo.modular.admin.model;

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
 * @since 2018-03-02
 */
@TableName("user_test")
public class UserTest extends Model<UserTest> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 测试类型
     */
    @TableField("test_type")
    private String testType;
    /**
     * 课程内容
     */
    @TableField("course_type")
    private String courseType;
    /**
     * 习题id
     */
    @TableField("subject_id")
    private String subjectId;
    /**
     * 习题数量
     */
    @TableField("subject_num")
    private Integer subjectNum;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 测试结果
     */
    @TableField("test_result")
    private String testResult;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(Integer subjectNum) {
        this.subjectNum = subjectNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "UserTest{" +
        ", id=" + id +
        ", userId=" + userId +
        ", testType=" + testType +
        ", courseType=" + courseType +
        ", subjectId=" + subjectId +
        ", subjectNum=" + subjectNum +
        ", startTime=" + startTime +
        ", testResult=" + testResult +
        "}";
    }
}
