var user_id= localStorage.id;
var recommenddata=new Array();
$.ajax({
    type: 'POST',//get或post
    url: 'booklist',//请求的地址
    async: false,
    data : {
        "user_id":user_id
    },
    success: function (res) {//成功的回调函数
        recommenddata=res;
        createlist(recommenddata);
    },
    error: function () {
        alert("fail");
    }
})

function createlist(recommenddata) {
    var tableStr = "<table id='tab'><thead><td width=30%>Top5</td>" + "</thead><tbody>";
    var len = recommenddata.length;
    for(var i=0 ;i<len ; i++){
        document.getElementById(i).innerHTML=recommenddata[i].name;
        }
}

$(function() {
    $("#0").click(function () {
        localStorage.book = recommenddata[0].book_id;
        window.location.href = 'UserBook.html';
        return false;
    })
})

function open(bookid){
    
    alert(bookid);
    localStorage.book=recommenddata[bookid].book_id;
    window.location.href = './UserBook.html';
    
}


//固定精品
data=[
    {image_url:"http://img3m4.ddimg.cn/31/12/25276414-1_b_13.jpg",id:7},
    {image_url:" http://img3m4.ddimg.cn/40/14/22783504-1_b_4.jpg",id:4},
    {image_url:"  http://img3m2.ddimg.cn/43/13/23958142-1_b_12.jpg",id:3},
    {image_url:"  http://img3m1.ddimg.cn/77/14/23259731-1_b_0.jpg",id:2},    
]
// document.getElementById('img4').src = data[0].image_url;
document.getElementById('img1').src = data[1].image_url;
document.getElementById('img2').src = data[2].image_url;
document.getElementById('img3').src = data[3].image_url;


$(function(){
    $("#img1").click(function(){
        localStorage.book= data[1].id;  //获取用户id或者商家id
        window.location.href = 'UserBook.html';
        return false;
    })
})

$(function(){
    $("#img2").click(function(){
        localStorage.book=  data[2].id;  //获取用户id或者商家id
        window.location.href = 'UserBook.html';
        return false;
    })
})

$(function(){
    $("#img3").click(function(){
        localStorage.book=  data[3].id;  //获取用户id或者商家id
        window.location.href = 'UserBook.html';
        return false;
    })
})