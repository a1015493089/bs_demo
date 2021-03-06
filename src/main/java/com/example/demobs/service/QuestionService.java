package com.example.demobs.service;

import com.example.demobs.dto.PaginationDTO;
import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.dto.QuestionQueryDTO;
import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.mapper.QuestionExtMapper;
import com.example.demobs.mapper.QuestionMapper;
import com.example.demobs.mapper.UserMapper;
import com.example.demobs.model.Question;
import com.example.demobs.model.QuestionExample;
import com.example.demobs.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    public PaginationDTO list(String search,Integer page, Integer size) {

        if(StringUtils.isNotBlank(search)){
            search=StringUtils.replace(search," ","|");
        }



        PaginationDTO paginationDTO = new PaginationDTO();

        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);

        paginationDTO.setPagination(totalCount,page,size);//输入最大行目 当前页数 每页容量 然后设置对应的参数值
        if(page<1)page=1;

        else if(page>paginationDTO.getTotalPage())
            page=paginationDTO.getTotalPage();

        Integer offset=size*(page-1);       //数据库查询下标

        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_creat desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions = questionExtMapper.countSelectSearch(questionQueryDTO);


        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //question到questionDTO的转换
        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);

        paginationDTO.setPagination(totalCount,page,size); //输入最大行目 当前页数 每页容量 然后设置对应的参数值
        if(page<1)page=1;
        else if(page>paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();
        Integer offset=size*(page-1);       //数据库查询下标

        QuestionExample example1 = new QuestionExample();  //查询语句执行
        example1.createCriteria().andCreatorEqualTo(userId);
        example1.setOrderByClause("gmt_creat desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));

        List<QuestionDTO> questionDTOList=new ArrayList<>();
        //question到questionDTO的转换
        for (Question question : questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        return paginationDTO;
    }
    //通过问题的ID码查找问题详情页面
    public QuestionDTO getQuestionById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);

        if(question==null){
            throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
        }
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user); //该对象负责profile页面右侧的创造者信息展示
        return  questionDTO;
    }

    public void creatOrUpdate(Question question) {
        if(question.getId()==null){ //没有问题ID 新建
            question.setViewCount(0);
            question.setCommentCount(0);
            question.setLikeCount(0);
            question.setGmtCreat(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreat());
            questionMapper.insert(question);
        }else {//已有问题ID 修改
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if(updated!=1){ //即为问题不存在 例如修改问题的同时 问题被删除了
                throw new CustomizeException(CustomizeExceptionCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {
        Question question=new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String tagsE=StringUtils.replace(questionDTO.getTag(),"，",",");
        String tags=StringUtils.replace(tagsE,",","|");
        Question question=new Question();
        question.setId(questionDTO.getId());
        question.setTag(tags);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS=questions.stream().map(q->{
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return  questionDTO1;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
