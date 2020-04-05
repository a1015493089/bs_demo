package com.example.demobs.mapper;

import com.example.demobs.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper{
    @Insert("INSERT INTO user(name,account_id,token,gmt_creat,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreat},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
    @Select("select * from user where id=#{id}")
    User findById(@Param("id")Integer id);
    @Select("select * from user where account_id =#{accountId}")
    User findByAccountID(@Param("accountId")String accountId);
    @Update("update user set name=#{name},token=#{token},gmt_modified=#{gmtModified},avatar_url=#{avatarUrl} where id=#{id}")
    void update(User user);
}
