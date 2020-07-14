var tabledata=[];
var select_role;

// res= [
//     {"role_id":1,"name":"用户","description":"购买书籍","code":"user"},
//     {"role_id":2,"name":"管理员","description":"管理用户","code":"manage"}   //测试
// ]
// tabledata=res;  //测试

//读取数据显示
createAll();

//回调读取数据库
function createAll(){
    $.ajax({
        type: 'get',//get或post
        url: 'role',//请求的地址
        async: false,
        success: function (res) {//成功的回调函数
            tabledata=res;
            createtable(tabledata);
        },
        error:function(e){
             //tabledata=e;
             //createtable(tabledata);  //测试
       }
    })
}


//创建动态表
function createtable(tabledata) {
    var tableStr = "<table id=\"tab\">";
    if (tabledata == null) {
        ttableStr = tableStr + "<thead><td width=5%>选中</td>" +
            "<td width=20%>角色id</td>" +
            "<td width=20%>角色名字</td>" +
            "<td width=20%>角色英文名</td>" +
            "<td width=40%>描述</td>"
        tableStr = tableStr + "</table>";
        alert("角色为空");
    } else {
        tableStr = tableStr + "<thead><td width=5%></td>" +
            "<td width=10%>角色id</td>" +
            "<td width=20%>角色名字</td>" +
            "<td width=10%>角色英文名</td>" +
            "<td width=40%>描述</td>" +
            "<td width=10%>修改</td>"
        "</thead><tbody>";
        var len = tabledata.length;
        for(var i=0 ;i<len ; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id='role_id"+i+"' name='role"+i+"' type='checkbox' value='"+i+"'/></td>" +  //选择框
                "<td><ul><lh id='role_idi"+i+"' contenteditable=true>" + tabledata[i].role_id +"</lh></ul></td>"+ //角色id显示
                "<td><ul><lh id='role_name "+i+"' contenteditable=true>" + tabledata[i].name +"</lh></ul></td>"+   //角色名显示
                "<td><ul><lh id='role_code"+i+"' contenteditable=true>" + tabledata[i].code +"</lh></ul></td>"+  //角色英文名
                "<td><ul><lh id='role_description"+i+"' contenteditable=true>" + tabledata[i].description +"</lh></ul></td>"+  //角色介绍
                "<td><button onclick='selectRole("+i+")' class='edit' id='edit'>确定修改</button></td>"
            }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
}

//删除角色
$(function(){
    $("#delete").click(function() {
        var len = tabledata.length;
        var role=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='role"+i+"']").prop("checked")){i
                role.push({role_id:tabledata[i].role_id});
            }
        }

        $.ajax({
            type: 'POST',//get或post
            url: 'deleteRole',//请求删除角色的地址
            data: JSON.stringify(role),
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
function selectRole(i){
    select_role = i;
}


//修改
$(function(){
    $(this).on("click",".edit",function(){

        var li = document.getElementsByTagName("lh");
        var role_id_val=li[select_role*4].innerHTML; //获得修改后编号
        alert(role_id_val)
        var name_val=li[select_role*4+1].innerHTML; //获得修改后名字
        var code_val=li[select_role*4+2].innerHTML; //获得修改后英文名
        var description_val=li[select_role*4+3].innerHTML; //获得修改后描述


        $.ajax({
            type: 'POST',//get或post
            url: 'editRole',//请求的地址
            /*async: false,*/
            data : JSON.stringify({
                role_id:role_id_val,
                name:name_val,
                code:code_val,
                description:description_val}),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {   
                    alert("修改失败");
                } else {
                    alert("修改成功");
                    createAll();
                }
            },
            error:function (res){
                alert("fail")
            }
        })
    })
})

//增加角色
$(function(){
    $("#add").click(function() {

        var tableStr = "<table id=\"tab\">";

        tableStr = tableStr + "<thead><td width=5%></td>" +
            "<td width=20%>角色id</td>" +
            "<td width=20%>角色名字</td>" +
            "<td width=20%>角色英文名</td>" +
            "<td width=40%>描述</td>"
        "</thead><tbody>";

        tableStr = tableStr + "<tr id='row_add'>" +

            "<td></td>" +  //选择框
            "<td><input id='role_id_add'/></td>"+ //角色id显示
            "<td><input id='role_name_add'/></td>"+   //角色名显示
            "<td><input id='role_code_add'/></td>"+  //角色英文名
            "<td><input id='role_des_add'/></td>"+  //角色介绍
            "<td><button onclick='add_role()' class='edit' id='add'>确定添加</button></td>"

        tableStr = tableStr + "</tbody></table>";
        $("#dataTable").html(tableStr);

    })
})

//确定添加
function add_role(){
    var role_id_add = $("#role_id_add").val();
    var role_name_add = $("#role_name_add").val();
    var role_code_add = $("#role_code_add").val();
    var role_des_add = $("#role_des_add").val();
    $.ajax({
        type:'post',//get或post
        url:'addRole',//请求的地址
        data : JSON.stringify({
            role_id:role_id_add,
            name:role_name_add,
            code:role_code_add,
            description:role_des_add}),
        dataType: 'json',//text,json,xml,jsonp
        contentType:'application/json;charset=utf-8',
        success:function(res){//成功的回调函数
            alert("添加成功");
            createAll();
        },
        error:function(e){
            alert("添加失败");
        }
    })
}
