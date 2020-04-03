package com.example.demobs.mapper;

import com.example.demobs.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(id,title,description,gmt_creat,gmt_modified,creator,tag) values(#{id},#{title},#{description},#{gmtCreat},#{gmtModified},#{creator},#{tag})")
    void creat(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param(value = "offset") Integer offset,@Param(value = "size") Integer size);

    @Select("select count(1) from QUESTION;")
    Integer count();

    @Select("select * from question where creator=#{userID} limit #{offset},#{size}")
    List<Question> listByUserId(@Param("userID")Integer userId, @Param("offset")Integer offset, @Param("size")Integer size);

    @Select("select count(1) from QUESTION where creator=#{userID};")
    Integer countByUserId(@Param("userID")Integer userId);

    @Select("select * from question where id=#{id}")
    Question getQuestionById(@Param("id")Integer id);
}
