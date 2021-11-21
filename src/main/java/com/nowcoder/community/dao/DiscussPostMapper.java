package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    //声明方法

    //首页不需要传入userId，但个人主页需要传入userId，因此sql中需要用到动态条件
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);  //offset每页的起始行号， limit每页限制的行数

    //@Param注解用于给参数取别名，
    // 如果某方法只有一个参数，且该参数在sql语句的<if>里使用，即sql中需要用到动态条件，则该参数必须取别名，否则会报错
    int selectDiscussPostRows(@Param("userId") int userId);


}
