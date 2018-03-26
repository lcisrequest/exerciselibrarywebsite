package com.example.lcdemo.modular.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.example.lcdemo.base.exception.LcException;
import com.example.lcdemo.base.exception.LcExceptionEnum;
import com.example.lcdemo.base.util.DateUtil;
import com.example.lcdemo.modular.admin.dao.ForumsMapper;
import com.example.lcdemo.modular.admin.dao.ReplyMapper;
import com.example.lcdemo.modular.admin.dao.UserInfoMapper;
import com.example.lcdemo.modular.admin.dao.LikeMapper;
import com.example.lcdemo.modular.admin.model.Forums;
import com.example.lcdemo.modular.admin.model.Reply;
import com.example.lcdemo.modular.admin.model.UserInfo;
import com.example.lcdemo.modular.admin.model.Like;
import com.example.lcdemo.modular.admin.service.ForumsService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForumsServiceImpl implements ForumsService {
    @Autowired
    ForumsMapper forumsMapper;
    @Autowired
    LikeMapper userLikeMapper;
    @Autowired
    ReplyMapper replyMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    /**
     * 发布新的讨论
     *
     * @param forums
     */
    @Override
    public void addForums(Forums forums) {
        forums.setIsTop("no");      //置顶默认为no
        forums.setCreateTime(DateUtil.getTime());
    }

    /**
     * 删除讨论
     *
     * @param forumsId
     * @param userId
     */
    @Override
    public void deleteForums(int forumsId, int userId) {
        Forums forums = forumsMapper.selectById(forumsId);
        if (forums == null) {               //判断该讨论是否存在
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        if (forums.getUserId() != userId) {         //判断该讨论是否是我发布的
            throw new LcException(LcExceptionEnum.CANNOT_DELETE);
        }
        forumsMapper.deleteById(forumsId);
        Wrapper<Like> wrapper = new EntityWrapper<>();
        wrapper.eq("forums_id", forumsId);
        wrapper.eq("type", "forums");
        userLikeMapper.delete(wrapper);         //删除该讨论的所有点赞
        Wrapper<Reply> replyWrapper = new EntityWrapper<>();
        replyWrapper.eq("type", "forums");
        replyWrapper.eq("forums_id", forumsId);
        replyMapper.delete(replyWrapper);       //删除该讨论的所有回复
    }


    /**
     * 新增讨论区点赞或取消点赞
     *
     * @param forumsId
     * @param userId
     */
    @Override
    public void addForumsLike(int forumsId, int userId) {
        Forums forums = forumsMapper.selectById(forumsId);
        if (forums == null) {
            throw new LcException(LcExceptionEnum.PARAM_ERROR);
        }
        int likeNum = forums.getLike();
        Like userLike = new Like();
        userLike.setType("forums");
        userLike.setUserId(userId);
        userLike.setForumsId(forumsId);
        Like ul = userLikeMapper.selectOne(userLike);   //判断是否点过赞
        if (ul == null) {   //若没点过
            userLikeMapper.insert(userLike);    //则新增点赞
            forums.setLike(likeNum + 1);          //点赞数加一
        } else {          //若点过赞
            userLikeMapper.deleteById(ul.getId()); //则删除点赞
            forums.setLike(likeNum - 1);
        }
        forumsMapper.updateById(forums);     //更新讨论的点赞数
    }

    /**
     * 新增讨论的回复
     *
     * @param reply
     * @param userId
     */
    @Override
    public void addForumsReply(Reply reply, int userId) {
        reply.setCreateTime(DateUtil.getTime());
        reply.setUserId(userId);
        reply.setType("forums"); //类型设置为讨论的回复
        reply.setIsRead(0);      //默认为未读
        int forumsId = reply.getForumsId();
        Forums forums = forumsMapper.selectById(forumsId);
        int getUserId = forums.getUserId();
        reply.setGetUserId(getUserId);
        replyMapper.insert(reply); //添加讨论的回复
        forums.setReply(forums.getReply() + 1); //回复数量+1
        forumsMapper.updateById(forums); //更新讨论
    }

    /**
     * 分页获取指定讨论的所有回复
     *
     * @param page
     * @param limit
     * @param forumsId
     * @return
     */
    @Override
    public List<Map<String, Object>> getForumsAllReply(int page, int limit, int forumsId) {
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("forums_id", forumsId);
        wrapper.eq("type", "forums");
        wrapper.orderBy("create_time", false);
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
     * 获取指定讨论的回复数量
     *
     * @param forumsId
     * @return
     */
    @Override
    public Integer getForumsAllReplyNum(int forumsId) {
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("forums_id", forumsId);
        wrapper.eq("type", "forums");
        return replyMapper.selectCount(wrapper);
    }

    /**
     * 分页获取所有讨论
     *
     * @param page
     * @param limit
     * @return
     */
    @Override
    public List<Map<String, Object>> getAllForums(int page, int limit, int myId) {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        wrapper.orderBy("is_top", false);
        wrapper.orderBy("create_time", false);
        RowBounds rowBounds = new RowBounds((page - 1) * limit, limit);
        List<Forums> list = forumsMapper.selectPage(rowBounds, wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Forums f : list) {
            Map<String, Object> map = new HashMap<>();
            int userId = f.getUserId();
            UserInfo userInfo = userInfoMapper.selectById(userId);
            map.put("nickName", userInfo.getNickname());
            map.put("userImg", userInfo.getUserimg());
            map.put("forums", f);
            int forumsId = f.getId();
            Like like = new Like();
            like.setType("forums");
            like.setUserId(myId);
            like.setForumsId(forumsId);
            Like isLike = userLikeMapper.selectOne(like);
            if (isLike == null) {
                map.put("isLike", false);
            } else {
                map.put("isLike", true);
            }
            listMap.add(map);
        }
        return listMap;
    }

    /**
     * 获取所有讨论的数量
     *
     * @return
     */
    @Override
    public Integer getAllForumsNum() {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        return forumsMapper.selectCount(wrapper);
    }

    /**
     * 获取该用户发布的讨论数量
     *
     * @param userId
     * @return
     */
    @Override
    public Integer getUserForumsNum(int userId) {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        return forumsMapper.selectCount(wrapper);
    }

    /**
     * 获取该用户发布的所有讨论
     *
     * @param userId
     * @return
     */
    @Override
    public List<Forums> getTheUserForums(int userId, int myId) {
        Wrapper<Forums> wrapper = new EntityWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.orderBy("create_time", false);
        List<Forums> list = forumsMapper.selectList(wrapper);
        List<Map<String, Object>> listMap = new ArrayList<>();
        for (Forums f : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("forums", f);
            int forumsId = f.getId();
            Like like = new Like();
            like.setType("forums");
            like.setUserId(myId);
            like.setForumsId(forumsId);
            Like isLike = userLikeMapper.selectOne(like);
            if (isLike == null) {
                map.put("isLike", false);
            } else {
                map.put("isLike", true);
            }
        }
        return list;
    }

    /**
     * 获取该讨论的内容及所有回复
     *
     * @param forumsId
     */
    @Override
    public List<Map<String, Object>> getTheForums(int forumsId) {
        Forums forums = forumsMapper.selectById(forumsId);
        Wrapper<Reply> wrapper = new EntityWrapper<>();
        wrapper.eq("type", "forums");
        wrapper.eq("forums_id", forumsId);
        wrapper.orderBy("create_time", false);
        List<Reply> list = replyMapper.selectList(wrapper);//根据评论id查找回复
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

}
