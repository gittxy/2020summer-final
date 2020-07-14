var user_id= localStorage.id;
var tabledata=[];
$.ajax({
    type: 'POST',//get或post
    url: 'order',//请求的地址
    async: false,
    data : {
        "user_id":user_id
    },
    success: function (res) {//成功的回调函数
        alert(res);
        tabledata=res;
        createtable(tabledata);
    },
    error: function(e){
        alert("显示失败");
        createtable(tabledata);
    }
})

function createtable(tabledata) {
    var tableStr = "<table class=\"table table-bordered\" id=\"tab\">";
    if (tabledata == null) {
        tableStr = tableStr + "<thead><tr><th>选中</th>"
            "<th>用户名</th>" +
            "<th>订单编号</th>" +
            "<th>订单金额</th>" +
            "<th>创建时间</th>" +
            "<th>订单状态</th></tr></thead>";
        tableStr = tableStr + "</table>";
        alert("订单列表为空");
    } else {
        tableStr = tableStr + "<thead><th>选中</th>" +
            "<th scope=\"col\">用户名</th>" +
            "<th scope=\"col\">订单编号</th>" +
            "<th scope=\"col\">订单金额</th>" +
            "<th scope=\"col\">创建时间</th>" +
            "<th scope=\"col\">订单状态</th>" 
            "</thead><tbody id=\"newTB\">";
        var len = tabledata.length;
        for(var i=0 ;i<len ; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id=\"order_id\" name=\"role"+i+"\" type=\"checkbox\" value=\""+i+"\"/></td>" +
                "<td>"+ tabledata[i].user_id +"</td>"+
                "<td>"+ tabledata[i].order_id +"</td>"+
                "<td>"+tabledata[i].payment + "</td>"+
                "<td>"+tabledata[i].create_time + "</td>"+
                "<td>"+tabledata[i].status+"</td></tr>" 
                
        }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
} 

//删除收藏
$(function(){
    $("#delete").click(function() {
        var len = tabledata.length;
        var order=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='role"+i+"']").prop("checked")){
                num = $("input[name='role"+i+"']:checked").val();
                order.push({order_id:tabledata[num].order_id});
            }
        }
       
        $.ajax({
            type: 'POST',//get或post
            url: 'deleteorder',//请求的地址
            data: JSON.stringify(order),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("删除失败");
                } else {  
                    $.ajax({
                        type: 'POST',//get或post
                        url: 'order',//请求的地址
                        async: false,
                        data : {
                            "user_id":user_id
                        },
                        success: function (res) {//成功的回调函数
                            tabledata=res;
                            createtable(tabledata);
                        }
                    })
                }
                alert("删除成功");
            },
            error:function(e){
                alert("删除失败");      
           }
        })
    })
})