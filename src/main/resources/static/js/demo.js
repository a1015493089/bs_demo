
//评论回复功能 1为问题
function post() {
    var questionId=$("#question_id").val();
    var content = $("#textarea_content").val();
    comment2target(questionId,1,content);
}
//评论转换为JSON
function comment2target(targetId,type,content) {
    //因为输入域较为特殊 交由后端处理空内容
    $.ajax({
        type:"post",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentID": targetId,
            "content": content,
            "type": type
        }),
        success:function (response) {
            if(response.code==200){
                window.location.reload();
            }else {
                if(response.code==2003){
                    var isAccepted=confirm(response.message);
                    if(isAccepted){
                        window.open("https://github.com/login/oauth/authorize?client_id=06134c3c17d51fd76f7f&redirect_uri=http://localhost:9132/callback&scope=user&state=1");
                        window.localStorage.setItem("closable",true);
                    }
                }else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType:"json"
    });
}
//二级评论的增加功能
function comment(e) {
    var id=e.getAttribute("data-id");
    var content = $("#comment2-"+id).val();
    comment2target(id,2,content);
}
//二级评论的显示控制
function collapseComment(e) {
    var id=e.getAttribute("data-id");
    var T1 = $("#comment-"+id);
    // 状态→0关 1开
    console.log(T1);
    console.log(e.getAttribute("data-opened"));

    if(e.getAttribute("data-opened")==1){
        //展开状态
        e.classList.remove("active");
        e.setAttribute("data-opened",0);
        T1.removeClass("in");

    }else {
        var subCommentContainer =$("#comment-"+id);
        e.classList.add("active");
        e.setAttribute("data-opened",1);
        T1.addClass("in");
        //解决多次访问多次读取数据累加的情况
        if(subCommentContainer.children().length!=1){
        }else {
            $.getJSON("/comment/"+id,function (data) {
                $.each(data.data,function (index,comment) {
                    //左侧区域
                    var mediaLeftElement=$("<div/>",{
                        "class":"media-left"
                    }).append($("<img/>",{
                        "class":"media-object img-rounded",
                        "src":comment.user.avatarUrl
                    }));
                    //中心区域
                    var mediaBodyElement=$("<div/>",{
                        "class":"media-body"
                    }).append($("<h4/>",{
                        "class":"media-heading",
                        "html":comment.user.name
                    })).append($("<div/>",{
                        "html":comment.content
                    })).append($("<div/>",{
                        "class":"icon"
                    }).append($("<span/>",{
                        "class":"pull-right",
                        "html":moment().format('YYYY-MM-DD hh:mm')
                    })));
                    var mediaElement=$("<div/>",{
                    }).append(mediaLeftElement).append(mediaBodyElement);
                    var commentElement=$("<div/>",{
                        "class":"col-lg-12 .col-md-12.col-sm-12 .col-xs-12 comments"
                    }).append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
            })
        }
    }
}
function showSelectTag(e) {
    var Tags=$("#Select-Tag");
    var state = e.getAttribute("data-open");
    if(state=="off"){
        Tags.show();
        e.setAttribute("data-open","on");
    }else {
        Tags.hide();
        e.setAttribute("data-open","off");
    }


}
function selectTag(value,e) {
    var state=e.getAttribute("data-switch");
    var prevalue = $("#tag").val();
    if(state=="off"){
        if(prevalue==null){
            $("#tag").val(value);
        }
        if(prevalue.indexOf(value)==-1){
            if(prevalue)
                $("#tag").val(prevalue+','+value);
            else
                $("#tag").val(value);
        }
        e.setAttribute("data-switch","on");
    }

    if(state=="on"){
        if(prevalue.indexOf(','+value)==-1&&prevalue.indexOf(value)!=0){

            $("#tag").val(prevalue.replace(value,""));
        }
        else if(prevalue.indexOf(value)==0){
            $("#tag").val(prevalue.replace(value+',',""));
        }
        else {
            $("#tag").val(prevalue.replace(','+value,""));
        }
        e.setAttribute("data-switch","off");
    }


}