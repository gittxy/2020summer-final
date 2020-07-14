var user_id= localStorage.id;
var tabledata=[];


$.ajax({
    type: 'POST',//get或post
    url: 'favorite',//请求的地址
    async: false,
    data : {
        "user_id":user_id
    },
    success: function (res) {//成功的回调函数
        tabledata=res;
        createtable(tabledata);
    },
    error:function(e){
        alert("显示失败");
        createtable(tabledata);
   }
})

function createtable(tabledata) {
    var tableStr = "<table id=\"tab\">";
    if (tabledata == null) {
        tableStr = tableStr + "<thead><td width=30%>商品名称</td>" +
            "<td width=20%>单价</td>" +
            "<td width=20%>数量</td>" +
            "<td width=30%>操作</td></thead>";
        tableStr = tableStr + "</table>";
        alert("收藏夹为空");
    } else {
        tableStr = tableStr + "<thead><td width=30%>选中</td>" +
            "<td width=30%>收藏号</td>" +
            "<td width=30%>商品名称</td>" +
            "<td width=20%>单价</td>" +
            "<td width=20%></td>" +
            "<td width=20%></td>"
            "</thead><tbody>";
        var len = tabledata.length;
        for(var i=0 ;i<len ; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id=\"roleaaa\" name=\"role"+i+"\" type=\"checkbox\" value=\""+i+"\"/></td>" +
                "<td>"+ i +"</td>"+
                "<td><a id=\"book_name\">"+ tabledata[i].name +"</a></td>"+
                "<td>"+tabledata[i].price + "</td>"+
                "<td></td>" +
                "<td></td>"
        }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
}


//删除收藏
$(function(){
    $("#deletecollect").click(function() {
        var len = tabledata.length;
        var favorite=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='role"+i+"']").prop("checked")){
                num = $("input[name='role"+i+"']:checked").val();
                favorite.push({favorite_id:tabledata[num].favorite_id});
            }
        }
        $.ajax({
            type: 'POST',//get或post
            url: 'deletefavorite',//请求的地址
            data: JSON.stringify(favorite),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("删除失败");
                } else {  
                    $.ajax({
                        type: 'POST',//get或post
                        url: 'favorite',//请求的地址
                        async: false,
                        data : {
                            "user_id":2
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

