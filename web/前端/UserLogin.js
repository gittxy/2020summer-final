// localStorage.li='哈哈哈';
// alert(localStorage.li);



function login(){
   var name=document.getElementById("login_name").value;
   var pass=document.getElementById("login_password").value;
   localStorage.user_name=name;

      
   if(document.getElementById('role_1').checked==true){
        var role_id = 2; //用户  
     window.open("./UserIndex.html");  //用户主页
    }
    else if(document.getElementById('role_2').checked==true) {
        var role_id = 3;//商家   
    } 
    else if(document.getElementById('role_3').checked==true) {
        var role_id = 1;//管理员
    }

    $.ajax({
     type: 'POST',
     url: "/login",
     data: {"username":name,"password":pass,"role_id":role_id},
     dataType:"json",
     success: function (data) {
        if(data=null){
            alert("用户id或密码错误");
            }
        else{
          if(data["role_id"]==2){  
                window.open("./UserIndex.html");  //用户主页
            }
            if (data["role_id"]==3){
                window.open("./UserIndex.html");  //商家主页
            }
            if(data["role_id"]==1){
                window.open("./ManageIndex.html"); //管理员主页
            }
         }
    }
  });
}



function register() {
    window.open("./UserRegister.html");  
}
   