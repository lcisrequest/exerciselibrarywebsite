package com.example.lcdemo.modular.admin.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author itspeed
 * @since 2018-03-02
 */
public class Test extends Model<Test> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 测试图片
     */
    private String img;
    /**
     * 测试名称
     */
    @TableField("test_name")
    private String testName;
    /**
     * 测试内容
     */
    @TableField("test_content")
    private String testContent;
    /**
     * 测试时间
     */
    @TableField("test_time")
    private String testTime;
    /**
     * 测试分数
     */
    @TableField("test_fraction")
    private String testFraction;
    /**
     * 测试问题
     */
    @TableField("test_subject")
    private String testSubject;
    /**
     * 测试难度
     */
    @TableField("test_difficulty")
    private Integer testDifficulty;
    /**
     * 测试结果
     */
    @TableField("test_result")
    private String testResult;
    /**
     * 开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 结束时间
     */
    @TableField("end_time")
    private String endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestContent() {
        return testContent;
    }

    public void setTestContent(String testContent) {
        this.testContent = testContent;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getTestFraction() {
        return testFraction;
    }

    public void setTestFraction(String testFraction) {
        this.testFraction = testFraction;
    }

    public String getTestSubject() {
        return testSubject;
    }

    public void setTestSubject(String testSubject) {
        this.testSubject = testSubject;
    }

    public Integer getTestDifficulty() {
        return testDifficulty;
    }

    public void setTestDifficulty(Integer testDifficulty) {
        this.testDifficulty = testDifficulty;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Test{" +
        ", id=" + id +
        ", img=" + img +
        ", testName=" + testName +
        ", testContent=" + testContent +
        ", testTime=" + testTime +
        ", testFraction=" + testFraction +
        ", testSubject=" + testSubject +
        ", testDifficulty=" + testDifficulty +
        ", testResult=" + testResult +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        "}";
    }
}
