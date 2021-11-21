package com.nowcoder.community.service;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;  //注入Mapper

    //返回的帖子，需要根据userId查询username，将username显示在网页上，但discuss_post表中没有username字段
    // 方式一，sql关联查询user用户数据;
    // 方式二，单独针对每一个discussPost查询对应的user数据，将用户数据和discusspost一起显示，此方式需要再建一个UserService.java
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit){
        return discussPostMapper.selectDiscussPosts(userId, offset, limit);
    }

    public int findDiscussPostRows(int userId){
        return discussPostMapper.selectDiscussPostRows(userId);
    }

}
