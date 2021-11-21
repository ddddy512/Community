package com.nowcoder.community.dao;
import com.nowcoder.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

//Mapper接口用于定义数据库访问行为，它可以由@Mapper或@Repository注解进行标记
@Mapper
public interface UserMapper {
    //声明查询、增加、修改等方法
    User selectById(int id);   //根据id查询

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);

}

