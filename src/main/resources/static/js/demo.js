

function post() {
    var questionId=$("#question_id").val();
    var content = $("#textarea_content").val();
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
                $("#reply_section").hide();
            }else {
                alert(response.message);
            }
            console.log(response);
        },
        dataType:"json"
    });
    console.log(questionId);
    console.log(content);

}