data={"book_id":12,"book_category_id":2,"store_id":1,"name":"Python基础教程（第3版）",
"outline":"Python基础教程（第3版）【图灵程序设计丛书】Python3.5编程从入门到实践 Python入门佳作 机器学习 人工智能 数据处理 网络爬虫热门编程语言 累计销售20万册","detail":"本书是经典的Python入门教程，层次鲜明、结构严谨、内容翔实，特别是后面几章，作者将前面讲述的内容应用到10个引人入胜的项目中，并以模板的形式介绍了项目的开发过程，手把手教授Python编程实践，让读者从项目中领略Python的真正魅力。 本书既适合初学者夯实基础，又能帮助Python程序员提升技能，即使是中高-级Python程序员，也能从书里找到耳目一新的内容。",
"press":"人民邮电出版社","publish_data":null,"size":"16开","version":null,"author":"[挪]芒努斯・利・海特兰德（Magnus Lie Hetland）",
"translator":null,"isbn":"9787115474889","price":"68.30","pages":null,"catalog":"",
"market_price":"99.00","member_price":null,"deal_mount":"100","look_mount":"116",
"discount":"6.90","image_url":"http://img3m5.ddimg.cn/62/19/25218035-1_b_2.jpg","store_mount":200,
"store_time":1541084330000,"is_shelf":1,"canme":null,"description":"","cata":"","content":""};

var user_name= localStorage.user_name;
alert(user_name);

var book_name = document.getElementById('book_name');
var book_author =document.getElementById('book_author');
var book_price = document.getElementById('book_price');
var book_com = document.getElementById('book_com');
var book_num = document.getElementById('book_num');
book_name.textContent=data.name;
book_author.textContent=data.author;
book_price.textContent=data.price;
book_com.textContent=data.outline;
book_num.textContent=data.store_mount;

$.ajax({
    type:'GET',//get或post
    url:'',//请求的地址
    dataType:'json',//text,json,xml,jsonp
    success:function(res){//成功的回调函数
        console.log(res)
    }
})

function UserCart(){
    window.open("./UserMyCart.html");
}