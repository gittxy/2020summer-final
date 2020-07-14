$(function(){
    $("#login").click(function(){
    console.log("初始化成功");
    var loginname = $("#login_name").val();
    var password = $("#login_password").val();
        var role = $("input[type='radio']:checked").val();
    console.log("loginname==="+loginname);
    console.log("password==="+loginname);
    $.ajax({
        type:"post",
        url:"login",
        dataType:"json",
        data:{
            "username":loginname,
            "password":password,
            "role_id":role
        },
        success:function(data){
        if(data==null){
            alert("用户id或密码错误");
            }
        else{
            localStorage.id=data["user_id"];  //获取用户id
          if(data["role_id"]==2){
                window.open("./UserIndex.html");  //用户主页
            }
            if (data["role_id"]==3){
                window.open("./ShellerIndex.html");  //商家主页
            }
            if(data["role_id"]==1){
                window.open("./ManageIndex.html"); //管理员主页
            }
         }
        },
        error:function(datass){
            alert("登录失败");
        }
    });
});
});

function register() {
    window.open("./UserRegister.html");
}
   