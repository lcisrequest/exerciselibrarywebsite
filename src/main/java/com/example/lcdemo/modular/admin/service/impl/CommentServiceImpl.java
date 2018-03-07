package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.CommentMapper;
import com.example.lcdemo.modular.admin.dao.ReplyMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.UserLikeMapper;
import com.example.lcdemo.modular.admin.dto.CommentDTO;
import com.example.lcdemo.modular.admin.model.Comment;
import com.example.lcdemo.modular.admin.model.Reply;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.model.UserLike;
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
    UserLikeMapper userLikeMapper;

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
    public List<Map<String, Object>> getComment(int subjectId, int page, int limit) {
        Wrapper<Comment> wrapper = new EntityWrapper<>();
        wrapper.eq("subject_id", subjectId);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Comment> list = commentMapper.selectPage(rowBounds, wrapper);//分页获取评论
        List<Map<String,Object>> listmap = new ArrayList<>();
        for (Comment c:list) {
            Map<String,Object> map = c.getMap();
            int userId = c.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(userId); //得到用户信息
            String username = userInfo.getUsername();
            String img = userInfo.getUserimg();
            map.put("username",username);       //得到用户名
            map.put("userImg",img);             //得到用户头像
            if(img==null){
                map.put("userImg","");
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
     * @param reply
     * @param userId
     */
    @Override
    public void addReply(Reply reply,int userId){
        reply.setCreateTime(DateUtil.getTime());
        reply.setUserId(userId);
        replyMapper.insert(reply);
    }

    /**
     * 分页获取指定评论的所有回复
     * @param commentId
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getReply(int commentId, int page, int limit){
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("comment_id", commentId);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Reply> list = replyMapper.selectPage(rowBounds, wrapper);//根据评论id查找回复
        List<Map<String,Object>> listmap = new ArrayList<>();
        for (Reply reply:list) {
            Map<String,Object> map = reply.getMap();
            int userId = reply.getUserId();  //得到用户id
            UserInfo userInfo = userInfoMapper.selectById(userId);
            String img = userInfo.getUserimg();
            String username = userInfo.getUsername();
            map.put("username",username); //得到用户名
            map.put("userImg",img);       //得到用户头像
            if(img==null){
                map.put("userImg","");
            }
            listmap.add(map);
        }
        return listmap;
    }

    /**
     * 获取指定评论的所有回复数量
     * @param commentId
     * @return
     */
    @Override
    public int getReplyNum(int commentId){
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("comment_id", commentId);
        int num = replyMapper.selectCount(wrapper);
        return num;
    }

    /**
     * 新增点赞
     * @param commentId
     * @param userId
     */
    @Override
    public synchronized void addLike(int commentId,int userId){
        Comment comment = commentMapper.selectById(commentId);
        if(comment==null){
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        int likeNum = comment.getLike();
        UserLike like = new UserLike();
        like.setUserId(userId);
        like.setCommentId(commentId);
        UserLike flag = userLikeMapper.selectOne(like);
        if(flag!=null){
            Wrapper<UserLike> wrapper = new EntityWrapper<>();
            wrapper.eq("user_id",userId);
            wrapper.eq("comment_id",commentId);
            userLikeMapper.delete(wrapper); //若点过赞,则取消点赞
            comment.setLike(likeNum-1);
            commentMapper.updateById(comment);//点赞数减一
        }else{
            userLikeMapper.insert(like);   //若没有点过赞，则新增点赞
            comment.setLike(likeNum+1);
            commentMapper.updateById(comment);//点赞数加一
        }
    }


}
