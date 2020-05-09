package com.example.demobs.service;

import com.example.demobs.dto.NotificationDTO;
import com.example.demobs.dto.PaginationDTO;
import com.example.demobs.dto.QuestionDTO;
import com.example.demobs.enums.NotificationStatusEnum;
import com.example.demobs.enums.NotificationTypeEnum;
import com.example.demobs.exception.CustomizeException;
import com.example.demobs.exception.CustomizeExceptionCode;
import com.example.demobs.mapper.NotificationMapper;
import com.example.demobs.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<NotificationDTO> paginationDTO = new PaginationDTO<>();
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        Integer totalCount = (int)notificationMapper.countByExample(example);

        paginationDTO.setPagination(totalCount,page,size); //输入最大行目 当前页数 每页容量 然后设置对应的参数值
        if(page<1)page=1;
        else if(page>paginationDTO.getTotalPage())page=paginationDTO.getTotalPage();
        Integer offset=size*(page-1);       //数据库查询下标

        NotificationExample example1 = new NotificationExample();  //查询语句执行
        example1.createCriteria().andReceiverEqualTo(userId);
        example1.setOrderByClause("gmt_creat desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
       if(notifications.size()==0)
       { return paginationDTO;}

        List<NotificationDTO> notificationDTOS=new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO=new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setStatus(notification.getState());
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setData(notificationDTOS);
        return paginationDTO;
    }

    public Long unreadCount(Long id) {
        NotificationExample notificationExample =new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id)
                .andStateEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification==null){
            throw new CustomizeException(CustomizeExceptionCode.NOTIFICATION_NOT_EXIST);
        }
        if ((long) notification.getReceiver() != (long) user.getId()){
            System.out.println((notification.getReceiver() != user.getId())+"在此运行");
            throw new CustomizeException(CustomizeExceptionCode.TARGET_ERROR);
        }

        notification.setState(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO=new NotificationDTO();
        BeanUtils.copyProperties(notification,notificationDTO);
        notificationDTO.setStatus(notification.getState());
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));



        return notificationDTO;
    }
}
