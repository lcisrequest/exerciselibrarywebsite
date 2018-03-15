package com.example.lcdemo.modular.admin.model;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author itspeed
 * @since 2018-03-15
 */
public class Course extends Model<Course> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String type;
    /**
     * 题目数量
     */
    @TableField("subject_num")
    private Integer subjectNum;
    /**
     * 模拟练习数量
     */
    @TableField("test_num")
    private Integer testNum;
    /**
     * 喜欢的人数
     */
    @TableField("people_num")
    private Integer peopleNum;
    /**
     * 知识点数量
     */
    @TableField("knowledge_num")
    private Integer knowledgeNum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSubjectNum() {
        return subjectNum;
    }

    public void setSubjectNum(Integer subjectNum) {
        this.subjectNum = subjectNum;
    }

    public Integer getTestNum() {
        return testNum;
    }

    public void setTestNum(Integer testNum) {
        this.testNum = testNum;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public Integer getKnowledgeNum() {
        return knowledgeNum;
    }

    public void setKnowledgeNum(Integer knowledgeNum) {
        this.knowledgeNum = knowledgeNum;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Course{" +
        ", id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", subjectNum=" + subjectNum +
        ", testNum=" + testNum +
        ", peopleNum=" + peopleNum +
        ", knowledgeNum=" + knowledgeNum +
        "}";
    }
}
