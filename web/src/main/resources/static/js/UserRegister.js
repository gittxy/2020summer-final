
$(function(){
    $("#register").click(function(){     
    var username = $("#username").val();
    var pass = $("#password").val();
    var re_pass=$("#re_pass").val();
    var role = $("input[type='radio']:checked").val();

    if(pass==re_pass){
        alert("密码输入相同");
        $.ajax({
            type:'post',//get或post
            url:'register',//请求的地址
            data:{
            "username":username,
            "password":pass,
            "role_id":role
            },
            dataType:'json',
            success:function(res){//成功的回调函数
                alert("注册成功");
            },
            error:function(datass){
                alert("注册失败");
            }
    
        });
        
    }else{
        alert("两次密码输入不相同");
    }

    });
});