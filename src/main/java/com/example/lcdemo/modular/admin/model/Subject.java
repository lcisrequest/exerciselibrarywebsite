package com.example.lcdemo.modular.admin.model;

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
 * @since 2018-03-02
 */
public class Subject extends Model<Subject> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 问题
     */
    private String problem;
    /**
     * 图片
     */
    private String img;
    @TableField("answer_one")
    private String answerOne;
    @TableField("answer_two")
    private String answerTwo;
    @TableField("answer_three")
    private String answerThree;
    @TableField("answer_four")
    private String answerFour;
    @TableField("answer_five")
    private String answerFive;
    /**
     * 正确答案
     */
    @TableField("right_key")
    private Integer rightKey;
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

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAnswerOne() {
        return answerOne;
    }

    public void setAnswerOne(String answerOne) {
        this.answerOne = answerOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getAnswerFive() {
        return answerFive;
    }

    public void setAnswerFive(String answerFive) {
        this.answerFive = answerFive;
    }

    public Integer getRightKey() {
        return rightKey;
    }

    public void setRightKey(Integer rightKey) {
        this.rightKey = rightKey;
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
        return "Subject{" +
                ", id=" + id +
                ", problem=" + problem +
                ", img=" + img +
                ", answerOne=" + answerOne +
                ", answerTwo=" + answerTwo +
                ", answerThree=" + answerThree +
                ", answerFour=" + answerFour +
                ", answerFive=" + answerFive +
                ", rightKey=" + rightKey +
                ", problemType=" + problemType +
                "}";
    }

    /**
     * 获取map
     * @return
     */
    public Map<String, Object> getMap() {
        Map<String, Object> mapSubject = new HashMap<>();
        mapSubject.put("id", this.getId());
        mapSubject.put("problem", this.getProblem());
        if (this.getAnswerOne() != null || "".equals(this.getAnswerOne())) {
            mapSubject.put("answerOne", this.getAnswerOne());
        } else {
            mapSubject.put("answerOne", "");
        }
        if (this.getAnswerTwo() != null || "".equals(this.getAnswerTwo())) {
            mapSubject.put("answerTwo", this.getAnswerTwo());
        } else {
            mapSubject.put("answerTwo", "");
        }
        if (this.getAnswerThree() != null || "".equals(this.getAnswerThree())) {
            mapSubject.put("answerThree", this.getAnswerThree());
        } else {
            mapSubject.put("answerThree", "");
        }
        if (this.getAnswerFour() != null || "".equals(this.getAnswerFour())) {
            mapSubject.put("answerFour", this.getAnswerFour());
        } else {
            mapSubject.put("answerFour", "");
        }
        if (this.getAnswerFive() != null || "".equals(this.getAnswerFive())) {
            mapSubject.put("answerFive", this.getAnswerFive());
        } else {
            mapSubject.put("answerFive", "");
        }
        if (this.getImg() != null || "".equals(this.getImg())) {
            mapSubject.put("img", this.getImg());
        } else {
            mapSubject.put("img", "");
        }
        mapSubject.put("rightKey", this.getRightKey());
        mapSubject.put("problemType", this.getProblemType());
        return mapSubject;
    }
}
