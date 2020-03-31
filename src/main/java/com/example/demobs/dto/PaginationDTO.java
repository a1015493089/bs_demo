package com.example.demobs.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages=new ArrayList();
    private Integer totalPage;
    //总页码计算
    public void setPagination(Integer totalCount, Integer page, Integer size) {
        if(totalCount%size==0){
            totalPage = totalCount/size;
        }else{
            totalPage = totalCount/size+1;
        };
        //提高容错
        if(page<1)page=1;
        else if(page>totalPage)page=totalPage;
        this.page=page;
        //分页列表构筑
        pages.add(page);
        for(int i=1;i<4;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        //换页符显示控制
        if(page==1){
            showPrevious=false;
        }else {
            showPrevious=true;
        }
        if(page==totalPage){
            showNext=false;
        }else {
            showNext=true;
        }
        if(pages.contains(1)){
            showFirstPage=false;
        }else {
            showFirstPage=true;
        }
        if(pages.contains(totalPage)){
            showEndPage=false;
        }else {
            showEndPage=true;
        }
        }
}
