function register(){
    var name=document.getElementById("login_name").value;
   var pass=document.getElementById("login_password").value;
      
   if(document.getElementById('role_1').checked=true){
        var role_id = 2; //用户  
    }
    if(document.getElementById('role_2').checked=true) {
        var role_id = 3;//商家  
    } 
    if(document.getElementById('role_3').checked=true) {
        var role_id = 1;//管理员
    }

    $.ajax({
             type: 'POST',
             url: "/login",
             data: {"username":name,"password":pass,"role_id":role_id},
             dataType:"json"
    });

}