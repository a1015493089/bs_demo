package com.example.demobs.mapper;

import com.example.demobs.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper{
    @Insert("INSERT INTO user(name,account_id,token,gmt_creat,gmt_modified,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreat},#{gmtModified},#{avatarUrl})")
    void insert(User user);
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
