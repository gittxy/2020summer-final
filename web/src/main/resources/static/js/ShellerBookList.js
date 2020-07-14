var manage_id= localStorage.id; //获取店铺id
var tabledata=[];
var select_book; 

/*res= [
    {"book_id":1,"name":"初级java","price":61.75,"outline":"Python基础教程（第3版）【图灵程序设计丛书】Python3.5编程从入门到实践 Python入门佳作 机器学习 人工智能 数据处理 网络爬虫热门编程语言 累计销售20万册","detail":"本书是经典的Python入门教程，层次鲜明、结构严谨、内容翔实，特别是后面几章，作者将前面讲述的内容应用到10个引人入胜的项目中，并以模板的形式介绍了项目的开发过程，手把手教授Python编程实践，让读者从项目中领略Python的真正魅力。 本书既适合初学者夯实基础，又能帮助Python程序员提升技能，即使是中高-级Python程序员，也能从书里找到耳目一新的内容。"},
    {"book_id":2,"name":"高级python","price":77777,"outline":"Python基础教程（第3版）【图灵程序设计丛书】Python3."}   //测试
]*/

//读取数据显示
createAll();


//回调读取数据库
function createAll(){
    $.ajax({
        type: 'post',//get或post
        url: 'Book',//请求的地址
        data : JSON.stringify({
            store_manager_id:manage_id}),
        dataType: 'json',
        contentType:'application/json;charset=utf-8',
        success: function (res) {//成功的回调函数
            tabledata=res;
            createtable(tabledata);
        },
        error:function(e){
            alert("test");
       }
    })
}


//创建动态表
function createtable(tabledata) {
    var tableStr = "<table id=\"tab\">";
    if (tabledata == null) {
        ttableStr = tableStr + "<thead><td width=5%>选中</td>" +
            "<td width=20%>书本id</td>" +
            "<td width=20%>书本名字</td>" +
            "<td width=20%>书本价格</td>" +
            "<td width=40%>简介</td>" 
        tableStr = tableStr + "</table>";
        alert("书本为空");
    } else {
        tableStr = tableStr + "<thead><td width=5%></td>" +
            "<td width=10%>书本id</td>" +
            "<td width=20%>书本名字</td>" +
            "<td width=10%>书本价格</td>" +
            "<td width=40%>简介</td>" +
            "<td width=10%>修改</td>"
        "</thead><tbody>";
        var len = tabledata.length;
        for(var i=0 ;i<len; i++){
            tableStr = tableStr + "<tr id='row'"+i+">" +
                "<td><input id='book_id"+i+"' name='book"+i+"' type='checkbox' value='"+i+"'/></td>" +  //选择框
                "<td><ul><lh id='book_idi"+i+"' contenteditable=true>" + tabledata[i].book_id +"</lh></ul></td>"+ //书本id显示
                "<td><ul><lh id='book_name "+i+"' contenteditable=true>" + tabledata[i].name +"</lh></ul></td>"+   //书本名显示
                "<td><ul><lh id='book_price"+i+"' contenteditable=true>" + tabledata[i].price +"</lh></ul></td>"+  //书本英文名
                "<td><ul><lh id='book_outline"+i+"' contenteditable=true>" + tabledata[i].outline +"</lh></ul></td>"+  //书本介绍
                "<td><button onclick='select_book("+i+")' class='edit' id='edit'>确定修改</button></td>" 
            }
        tableStr = tableStr + "</tbody></table>";
    }
    $("#dataTable").html(tableStr);
}


//删除书籍
$(function(){
    $("#delete").click(function() {
        var len = tabledata.length;
        var book=new Array();
        for(var i=0;i<len;i++){
            if($("input[name='book"+i+"']").prop("checked")){i
                book.push({book_id:tabledata[i].book_id});
            }
        }
        $.ajax({
            type: 'POST',//get或post
            url: 'deleteBook',//请求删除书本的地址
            data: JSON.stringify(book),
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
function select_book(i){
    select_book = i;
}

//修改
$(function(){
    $(this).on("click",".edit",function(){

        var li = document.getElementsByTagName("lh");
        var book_id_val=li[select_book*4].innerHTML; //获得修改后编号
        var name_val=li[select_book*4+1].innerHTML; //获得修改后名字
        var price_val=li[select_book*4+2].innerHTML; //获得修改后价格
        var outline_val=li[select_book*4+3].innerHTML; //获得修改后描述
        alert(book_id_val);


        $.ajax({
            type: 'POST',//get或post
            url: 'editBook',//请求的地址
            /*async: false,*/
            data : JSON.stringify({
                book_id:book_id_val,
                name:name_val,
                price:price_val,
                outline:outline_val}),
            dataType: 'json',
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


//增加书籍
$(function(){
    $("#add").click(function() {
        var tableStr = "<table id=\"tab\">";
      
            tableStr = tableStr + "<thead><td width=5%></td>" +
                "<td width=20%>书本id</td>" +
                "<td width=20%>书本名字</td>" +
                "<td width=20%>书本价钱</td>" +
                "<td width=40%>简介</td>" 
                "</thead><tbody>";
                
            tableStr = tableStr + "<tr id='row_add'>" +
           
                "<td></td>" +  //选择框
                "<td><input id='book_id_add'/></td>"+ //书本id显示
                "<td><input id='book_name_add'/></td>"+   //书本名显示
                "<td><input id='book_price_add'/></td>"+  //书本英文名
                "<td><input id='book_outline_add'/></td>"+  //书本介绍
                "<td><button onclick='add_book()' class='edit' id='add'>确定添加</button></td>" 
                
            tableStr = tableStr + "</tbody></table>";
  $("#dataTable").html(tableStr);
    
    })
})


//确定添加
function add_book(){
    alert("addnbook");
    var book_id_add = $("#book_id_add").val();
    var book_name_add = $("#book_name_add").val();
    var book_price_add = $("#book_price_add").val();
    var book_outline_add = $("#book_outline_add").val();
    $.ajax({
        type:'post',//get或post
        url:'addBook',//请求的地址
        data : JSON.stringify({
            book_id:book_id_add,
            store_id:manage_id,
            name:book_name_add,
            price:book_price_add,
            outline:book_outline_add}),
        dataType: 'json',
        contentType:'application/json;charset=utf-8',
        success:function(res){//成功的回调函数
            createAll();
        },
        error:function(e){
        }
    })  
}

