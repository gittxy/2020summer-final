var manager_id= localStorage.id; //获取店铺经理id

// data={"store_name":"逍遥书屋","store_position":"北京","store_telephone":123456}

//发怂店铺id获得相关信息
$.ajax({
    type:'post',//get或post
    url:'store',//请求的地址
    data :{
        "store_manager_id":manager_id
    },
    success:function(data){//成功的回调函数
        var store_name = document.getElementById('store_name');
        var store_position = document.getElementById('store_position');
        var store_telephone = document.getElementById('store_telephone');
        store_name.textContent=data.store_name;
        store_position.textContent=data.store_position;
        store_telephone.textContent=data.store_telephone;
        
    },
    error:function(e){
        alert("获取用户名密码地址失败");
    }
});

$(function(){
    $("#modify").click(function(){
        
        var li = document.getElementsByTagName("li");
        
        var name_val = li[3].innerHTML;
        var position_val = li[4].innerHTML;
        var telephone_val = li[5].innerHTML;

        $.ajax({
            type:'post',//get或post
            url:'editStore',//请求的地址
            data:{
                "store_manager_id":manager_id,
                "store_name":name_val,
                "store_position":position_val,
                "store_telephone":telephone_val
            },
            success:function(res){//成功的回调函数
                alert("修改成功");
            },
             error:function(e){
                alert("修改失败");
                
            }
        })
    });
});
