package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.CommentMapper;
import com.example.lcdemo.modular.admin.dao.ReplyMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.LikeMapper;
import com.example.lcdemo.modular.admin.dto.CommentDTO;
import com.example.lcdemo.modular.admin.model.Comment;
import com.example.lcdemo.modular.admin.model.Reply;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.model.Like;
import com.example.lcdemo.modular.admin.service.CommentService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    LikeMapper userLikeMapper;

    /**
     * 添加评论
     *
     * @param commentDTO
     * @param userId
     */
    @Override
    public void addComment(CommentDTO commentDTO, int userId) {
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        comment.setSubjectId(commentDTO.getSubjectId());
        comment.setCreateTime(DateUtil.getTime());
        comment.setLike(0);     //点赞数为0
        comment.setReply(0);    //回复数为0
        comment.setUserId(userId);
        commentMapper.insert(comment);
    }

    /**
     * 分页获取指定题目的评论
     *
     * @param subjectId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getComment(int subjectId, int page, int limit, int myId) {
        Wrapper<Comment> wrapper = new EntityWrapper<>();
        wrapper.eq("subject_id", subjectId);
        wrapper.orderBy("create_time", false);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Comment> list = commentMapper.selectPage(rowBounds, wrapper);//分页获取评论
        List<Map<String, Object>> listmap = new ArrayList<>();
        for (Comment c : list) {
            Map<String, Object> map = c.getMap();
            int userId = c.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(userId); //得到用户信息
            String username = userInfo.getUsername();
            String img = userInfo.getUserimg();
            map.put("username", username);       //得到用户名
            map.put("userImg", img);             //得到用户头像
            if (img == null) {
                map.put("userImg", "");
            }

            int commentId = c.getId();
            Wrapper<Like> wrapperLike = new EntityWrapper<>();
            wrapperLike.eq("user_id", myId);
            wrapperLike.eq("comment_id", commentId);
            int countLike = userLikeMapper.selectCount(wrapperLike);//判断我是否对该评论点过赞
            if (countLike == 0) {
                map.put("isLike", false); //没点过
            } else {
                map.put("isLike", true); //点过
            }
            listmap.add(map);
        }
        return listmap;
    }

    /**
     * 获取指定题目的所有评论数量
     *
     * @param subjectId
     * @return
     */
    @Override
    public int getCommentNum(int subjectId) {
        Wrapper<Comment> wrapper = new EntityWrapper<>();
        wrapper.eq("subject_id", subjectId);
        int num = commentMapper.selectCount(wrapper);
        return num;
    }

    /**
     * 新增回复
     *
     * @param reply
     * @param userId
     */
    @Override
    public void addReply(Reply reply, int userId) {
        reply.setCreateTime(DateUtil.getTime());
        reply.setUserId(userId);
        reply.setType("comment"); //类型设置为评论的回复
        reply.setIsRead(0);       //默认未读
        int commentId = reply.getCommentId();
        Comment comment = commentMapper.selectById(commentId);
        int getUserId = comment.getUserId();
        reply.setGetUserId(getUserId);
        replyMapper.insert(reply); //新增回复
        comment.setReply(comment.getReply() + 1); //评论回复数量+1
        commentMapper.updateById(comment);  //更新评论
    }

    /**
     * 分页获取指定评论的所有回复
     *
     * @param commentId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getReply(int commentId, int page, int limit) {
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("comment_id", commentId);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Reply> list = replyMapper.selectPage(rowBounds, wrapper);//根据评论id查找回复
        List<Map<String, Object>> listmap = new ArrayList<>();
        for (Reply reply : list) {
            Map<String, Object> map = reply.getMap();
            int userId = reply.getUserId();  //得到用户id
            UserInfo userInfo = userInfoMapper.selectById(userId);
            String img = userInfo.getUserimg();
            String username = userInfo.getUsername();
            map.put("username", username); //得到用户名
            map.put("userImg", img);       //得到用户头像
            if (img == null) {
                map.put("userImg", "");
            }
            listmap.add(map);
        }
        return listmap;
    }

    /**
     * 获取指定评论的所有回复数量
     *
     * @param commentId
     * @return
     */
    @Override
    public int getReplyNum(int commentId) {
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("comment_id", commentId);
        int num = replyMapper.selectCount(wrapper);
        return num;
    }

    /**
     * 新增点赞
     *
     * @param commentId
     * @param userId
     */
    @Override
    public synchronized void addLike(int commentId, int userId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        int likeNum = comment.getLike();
        Like like = new Like();
        like.setUserId(userId);
        like.setCommentId(commentId);
        like.setType("comment");
        Like flag = userLikeMapper.selectOne(like);
        if (flag != null) {
            Wrapper<Like> wrapper = new EntityWrapper<>();
            wrapper.eq("user_id", userId);
            wrapper.eq("comment_id", commentId);
            wrapper.eq("type", "comment");
            userLikeMapper.delete(wrapper); //若点过赞,则取消点赞
            comment.setLike(likeNum - 1);
            commentMapper.updateById(comment);//点赞数减一
        } else {
            userLikeMapper.insert(like);   //若没有点过赞，则新增点赞
            comment.setLike(likeNum + 1);
            commentMapper.updateById(comment);//点赞数加一
        }
    }

    /**
     * 获取我的所有未读回复
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getMyReply(int userId) {
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("get_user_id", userId);
        wrapper.eq("is_read", 0);
        wrapper.orderBy("create_time", false);
        List<Reply> list = replyMapper.selectList(wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Reply reply : list) {
            Map<String, Object> map = reply.getMap();
            int sendUserId = reply.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(sendUserId);
            map.put("nickname", userInfo.getNickname());
            map.put("userimg", userInfo.getUserimg());
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 将回复设置成已读
     *
     * @param replyId
     */
    @Override
    public void MakeReplyIsRead(int replyId) {
        Reply reply = replyMapper.selectById(replyId);
        if (reply == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        reply.setIsRead(1);     //1为已读
        replyMapper.updateById(reply);
    }
}
