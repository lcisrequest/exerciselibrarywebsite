package com.example.lcdemo.modular.admin.model;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author itspeed
 * @since 2018-03-05
 */
public class Comment extends Model<Comment> {

    private static final long serialVersionUID = 1L;

    private Integer id;
    /**
     * 内容
     */
    private String content;
    /**
     * 时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 图片
     */
    private String img;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 点赞数
     */
    private Integer like;
    /**
     * 评论数
     */
    private Integer reply;
    /**
     * 题目id
     */
    @TableField("subject_id")
    private Integer subjectId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLike() {
        return like;
    }

    public void setLike(Integer like) {
        this.like = like;
    }

    public Integer getReply() {
        return reply;
    }

    public void setReply(Integer reply) {
        this.reply = reply;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Comment{" +
        ", id=" + id +
        ", content=" + content +
        ", createTime=" + createTime +
        ", img=" + img +
        ", userId=" + userId +
        ", like=" + like +
        ", reply=" + reply +
        ", subjectId=" + subjectId +
        "}";
    }

    public Map<String, Object> getMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("id",this.id);
        map.put("content",this.content);
        map.put("createTime",this.createTime);
        map.put("img",this.img);
        if(this.img==null){
            map.put("img","");
        }
        map.put("userId",this.userId);
        map.put("like",this.like);
        map.put("reply",this.reply);
        map.put("subjectId",subjectId);
        return map;
    }
}
