var user_id= localStorage.id;
var tabledata=[];
var select_book;
createAll();

$(function(){
    $("#deletecart").click(function() {
        var len = tabledata.length;
        var cart=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='role"+i+"']").prop("checked")){
                num = $("input[name='role"+i+"']:checked").val();
                cart.push({cart_id:tabledata[num].cart_id});
            }
        }
        $.ajax({
            type: 'POST',//get或post
            url: 'deletecart',//请求的地址
            data: JSON.stringify(cart),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("删除失败");
                } else {
                    createAll();
                }
            },
            error:function (res){
                alert("fail")
            }
        })
    })
})

//结算
$(function(){
    $("#submitcart").click(function() {
        var len = tabledata.length;
        var cart=new Array();
        var cart_o=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='role"+i+"']").prop("checked")){
                num = $("input[name='role"+i+"']:checked").val();
                cart.push({cart_id:tabledata[num].cart_id,user_id:2});
                cart_o.push({user_id:user_id,payment:tabledata[num].money,status:1,payment_type:1});
            }
        }
        $.ajax({
            type: 'POST',//get或post
            url: 'gettotalcart',//请求的地址
            data: JSON.stringify(cart),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("对不起，出现错误无法购买！");
                } else {
                    var result = confirm('总计：'+res.total+' 元。确认购买？');
                    if(result){
                                $.ajax({
                                    type: 'POST',//get或post
                                    url: 'deletecart',//请求的地址
                                    data: JSON.stringify(cart),
                                    dataType: 'json',//text,json,xml,jsonp
                                    contentType:'application/json;charset=utf-8',
                                    success:function(res){
                                        $.ajax({
                                            type: 'POST',//get或post
                                            url: 'addorder',//请求的地址
                                            data: JSON.stringify(cart_o),
                                            dataType: 'json',//text,json,xml,jsonp
                                            contentType:'application/json;charset=utf-8',
                                            success:function(res){
                                                createAll();
                                                alert('购买成功！');  
                                            }
                                        })

                                    }
                                })
                            }else{  
                                alert('滚吧，别买了！');  
                            }
                }
            },
            error:function(e){
                alert("没买成，省钱了");      
           }
        })      
    })
})

$(function(){
    $(this).on("click",".editcart",function(){
    /*$(".editcart").click(function() {*/
        var num = $("#input_id" + select_book).val();
        var cart_id = ($("#cart_idi"+select_book).html().trim());
        var price = $("#book_price"+select_book).html().trim();

        $.ajax({
            type: 'POST',//get或post
            url: 'editcart',//请求的地址
            /*async: false,*/
            data : JSON.stringify({cart_id:cart_id,num:num,price:price}),
            dataType: 'json',//text,json,xml,jsonp
            contentType:'application/json;charset=utf-8',
            success: function (res) {//成功的回调函数
                if (res == null) {
                    alert("修改失败");
                } else {
                    createAll();
                }
            },
            error:function (res){
                alert("fail")
            }
        })
    })
})

function createAll(){
    $.ajax({
        type: 'POST',//get或post
        url: 'cart',//请求的地址
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

function createtable(tabledata) {
    var tableStr = "<table id=\"tab\">";
    if (tabledata == null) {
        ttableStr = tableStr + "<thead><td width=30%>选中</td>" +
            "<td width=30%>购物车</td>" +
            "<td width=30%>商品名称</td>" +
            "<td width=20%>单价</td>" +
            "<td width=20%>数量</td>" +
            "<td width=20%>总价</td></thead>";
        tableStr = tableStr + "</table>";
        alert("购物车为空");
    } else {
        tableStr = tableStr + "<thead><td width=30%>选中</td>" +
            "<td width=30%>购物车</td>" +
            "<td width=30%>商品名称</td>" +
            "<td width=20%>单价</td>" +
            "<td width=20%>数量</td>" +
            "<td width=20%>总价</td>"
        "</thead><tbody>";
        var len = tabledata.length;
        for(var i=0 ;i<len ; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id='cart_id"+i+"' name='role"+i+"' type='checkbox' value='"+i+"'/></td>" +
                "<td><label id='cart_idi"+i+"'>"+ tabledata[i].cart_id +"</td>"+
                "<td><a id='book_name'>"+ tabledata[i].name +"</a></td>"+
                "<td><label id='book_price"+i+"'>"+tabledata[i].price + "</label></td>"+
                "<td><button onclick='subclick("+i+")'>-</button>" +
                "<input value='"+tabledata[i].num +"' id='input_id"+i+"' readonly>" +
                "<button onclick='addclick("+i+")'>+</button>" +
                "<button class='editcart' id='editcart'>确定</button></td>" +
                "<td>"+tabledata[i].money+"</td>"
        }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
}

function addclick(select_name){
    var num = $("#input_id" + select_name).val();
    $("#input_id" + select_name).val(parseInt(num)+1);
    select_book = select_name;
}

function subclick(select_name){
    var num = $("#input_id" + select_name).val();
    $("#input_id" + select_name).val(parseInt(num)-1);
    select_book = select_name;
}
