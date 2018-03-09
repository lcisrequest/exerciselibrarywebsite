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
     * 题目类型
     */
    @TableField("problem_type")
    private String problemType;
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
     * 测试答案
     */
    @TableField("test_result")
    private String testResult;
    /**
     * 模拟练习id
     */
    @TableField("test_id")
    private int testId;

    private String score;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

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

    public String getProblemType() {
        return problemType;
    }

    public void setProblemType(String problemType) {
        this.problemType = problemType;
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

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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
        ", problemType=" + problemType +
        ", subjectId=" + subjectId +
        ", subjectNum=" + subjectNum +
        ", startTime=" + startTime +
        ", testResult=" + testResult +
        "}";
    }
}
