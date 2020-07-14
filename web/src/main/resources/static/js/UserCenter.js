var user_id= localStorage.id;

$.ajax({
    type:'post',//get或post
    url:'userinfo',//请求的地址
    data:{"user_id":user_id}, //发送user_id
    dataType:'json',
    success:function(data){//成功的回调函数
        let username = document.getElementById('username');
        let password = document.getElementById('password');
        let address = document.getElementById('address');
        username.textContent=data.username;
        password.textContent=data.password;
        address.textContent=data.location;

    },
    error:function(e){
        alert("获取用户名密码地址失败");
    }
})

$(function(){
    $("#modify").click(function(){
        
        var li = document.getElementsByTagName("li");
        
        var name_val = li[3].innerHTML;
        var pass_val = li[4].innerHTML;
        var addr_val = li[5].innerHTML;

        $.ajax({
            type:'post',//get或post
            url:'modifyinfo',//请求的地址
            data:{
                "user_id":user_id,
                "username":name_val,
                "password":pass_val,
                "location":addr_val
            },
            dataType:'json',
            success:function(res){//成功的回调函数
                alert("修改成功");
            },
             error:function(e){
                alert("修改失败");
            }
        })
    });
});
