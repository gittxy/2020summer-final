var tabledata=[];
var select_user;

// res= [
//    {"user_id":0,"username":"stockton","nickname":null,"password":"000000","gender":"0","email":null,"phone":null,"zip_code":null,"location":" california","age":18,"country":" usa","detail_address":"广西贵港","identity":null,"active":1,"code":null,"end":1596968809000,"created":1594376809000,"points":0,"role_id":2},
//     {"user_id":1,"username":"aaa","location":"北京","role_id":1},
//     {"user_id":2,"username":"bbb","location":"上海","role_id":2}   //测试
// ]
// tabledata=res;  //测试

//读取数据显示
createAll();

//回调读取数据库
function createAll(){
    $.ajax({
        type: 'get',//get或post
        url: 'User',//请求的地址
        success: function (res) {//成功的回调函数
            tabledata=res;
            createtable(tabledata);
        },
        error:function(e){
            alert("test")
            //tabledata=res;
            //createtable(tabledata);  //测试
       }
    })
}


//创建动态表
function createtable(tabledata) {
    var tableStr = "<table id=\"tab\">";
    if (tabledata == null) {
        ttableStr = tableStr + "<thead><td width=5%>选中</td>" +
            "<td width=20%>用户id</td>" +
            "<td width=20%>用户名字</td>" +
            "<td width=20%>角色</td>" +
            "<td width=40%>地址</td>" 
        tableStr = tableStr + "</table>";
    } else {
        tableStr = tableStr + "<thead><td width=5%></td>" +
            "<td width=10%>用户id</td>" +
            "<td width=20%>用户名字</td>" +
            "<td width=10%>角色</td>" +
            "<td width=40%>地址</td>" +
        "</thead><tbody>";
        var len = tabledata.length;
        for(var i=0 ;i<len ; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id='user_id"+i+"' name='user"+i+"' type='checkbox' value='"+i+"'/></td>" +  //选择框
                "<td><ul><lh id='user_idi"+i+"' contenteditable=true>" + tabledata[i].user_id +"</lh></ul></td>"+ //用户id显示
                "<td><ul><lh id='user_name "+i+"' contenteditable=true>" + tabledata[i].username +"</lh></ul></td>"+   //用户名显示
                "<td><ul><lh id='user_location"+i+"' contenteditable=true>" + tabledata[i].role_id +"</lh></ul></td>"+  //用户角色
                "<td><ul><lh id='user_role_id"+i+"' contenteditable=true>" + tabledata[i].location +"</lh></ul></td>"  //角色介绍
            }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
}

//删除用户
$(function(){
    $("#delete").click(function() {
        var len = tabledata.length;
        var user=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='user"+i+"']").prop("checked")){i
                user.push({user_id:tabledata[i].user_id});   //选中行的user_id
            }
        }
        $.ajax({
            type: 'POST',//get或post
            url: 'deleteUser',//请求删除用户的地址
            data: JSON.stringify(user),  //传选中行的user_id
            dataType: 'json',
            contentType:'application/json;charset=utf-8',  //转换格式
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("删除失败");
                } else {
                    createAll();
                    alert("删除成功");
                }
            },
            error:function (res){
                alert("fail")
            }
        })
    })
})

//选中行
function select_user(i){
    select_user = i;
}


//增加用户
$(function(){
    $("#add").click(function() {
 
        var tableStr = "<table id=\"tab\">";
      
            tableStr = tableStr + "<thead><td width=5%></td>" +
                "<td width=20%>用户id</td>" +
                "<td width=20%>用户名字</td>" +
                "<td width=20%>角色</td>" +
                "<td width=40%>地址</td>" 
                "</thead><tbody>";
                
            tableStr = tableStr + "<tr id='row_add'>" +
           
                "<td></td>" +  //选择框
                "<td><input id='user_id_add'/></td>"+ //id显示
                "<td><input id='user_name_add'/></td>"+   //用户名显示
                "<td><input id='user_role_add'/></td>"+  //角色
                "<td><input id='user_location_add'/></td>"+  //地址
                "<td><button onclick='add_user()' class='edit' id='add'>确定添加</button></td>" 
                
            tableStr = tableStr + "</tbody></table>";
  $("#dataTable").html(tableStr);
    
    })
})


//确定添加
function add_user(){
    var user_id_add = $("#user_id_add").val();
    var user_name_add = $("#user_name_add").val();
    var user_role_add = $("#user_role_add").val();
    var user_location_add = $("#user_location_add").val();
    $.ajax({
        type:'post',//get或post
        url:'addUser',//请求的地址
        data : JSON.stringify({
            user_id:user_id_add,
            username:user_name_add,
            role_id:user_role_add,
            location:user_location_add}),
        dataType: 'json',
        contentType:'application/json;charset=utf-8',
        success:function(res){//成功的回调函数
        alert("添加成功")
            createAll();
        },
        error:function(e){
            alert("添加失败")
            //  createAll();  //测试
        }
    })  
}
