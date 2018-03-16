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
 * @since 2018-03-14
 */
public class Reply extends Model<Reply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    private String content;
    /**
     * 评论id
     */
    @TableField("comment_id")
    private Integer commentId;
    /**
     * 图片
     */
    private String img;
    /**
     * 时间
     */
    @TableField("create_time")
    private String createTime;
    /**
     * 讨论的id
     */
    @TableField("forums_id")
    private Integer forumsId;
    /**
     * 回复类型
     */
    private String type;
    /**
     * 收到回复的人的id
     */
    @TableField("get_user_id")
    private Integer getUserId;
    /**
     * 是否已读
     */
    @TableField("is_read")
    private Integer isRead;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getForumsId() {
        return forumsId;
    }

    public void setForumsId(Integer forumsId) {
        this.forumsId = forumsId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getGetUserId() {
        return getUserId;
    }

    public void setGetUserId(Integer getUserId) {
        this.getUserId = getUserId;
    }

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Reply{" +
                ", id=" + id +
                ", userId=" + userId +
                ", content=" + content +
                ", commentId=" + commentId +
                ", img=" + img +
                ", createTime=" + createTime +
                ", forumsId=" + forumsId +
                ", type=" + type +
                "}";
    }

    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", this.id);
        map.put("content", this.content);
        map.put("createTime", this.createTime);
        map.put("img", this.img);
        if (this.img == null) {
            map.put("img", "");
        }
        map.put("userId", this.userId);
        map.put("commentId", this.commentId);
        map.put("type", this.type);
        map.put("forumsId", this.forumsId);
        return map;
    }
}
