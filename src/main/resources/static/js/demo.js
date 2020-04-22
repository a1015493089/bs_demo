

function post() {
    var questionId=$("#question_id").val();
    var content = $("#textarea_content").val();
    //因为输入域较为特殊 交由后端处理空内容

    $.ajax({
        type:"post",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentID": questionId,
            "content": content,
            "type": 1
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
    console.log(questionId);
    console.log(content);

}