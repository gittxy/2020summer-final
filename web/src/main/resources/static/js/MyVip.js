var user_id= localStorage.id;

$.ajax({
    type:'post',//get或post
    url:'userinfo',//请求的地址
    data:{"user_id":user_id}, //发送user_id
    dataType:'json',
    success:function(data){//成功的回调函数
        var username = document.getElementById('username');
        var endtime = document.getElementById('endtime');
        var credit = document.getElementById('credit');
        username.textContent=data.username;
        endtime.textContent=data.end;
        credit.textContent=data.points;
        
    },
    error:function(e){
        alert("获取用户名到期日积分失败");
    }
});

$(function(){
    $("#open").click(function(){
        $.ajax({
            type:'post',//get或post
            url:'recharge',//请求的地址
            data:{
                "user_id":user_id,
            },
            dataType:'json',
            success:function(res){//成功的回调函数
                alert("开通成功");
            },
             error:function(e){
                alert("开通失败");
            }
        })

        
    });
});
