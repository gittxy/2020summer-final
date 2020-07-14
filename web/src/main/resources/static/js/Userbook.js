
var user_id= localStorage.id;//获得当前用户id
var book_id=localStorage.book;
var book_name = document.getElementById('book_name');
var book_author =document.getElementById('book_author');
var book_price = document.getElementById('book_price');
var book_com = document.getElementById('book_com');
var book_num = document.getElementById('book_num');

var Store_id;
$.ajax({
    type:'post',//get或post
    url:'bookinfo',//请求的地址
    data:{
        "book_id":book_id
    },
    dataType:'json',
    success:function(data){//成功的回调函数
        book_name.textContent=data.name;
        book_author.textContent=data.author;
        book_price.textContent=data.price;
        book_com.textContent=data.outline;
        book_num.textContent=data.store_mount;
        document.getElementById('book_img').src = data.image_url;
        book_id=data.book_id;   //获取book_iid
        Store_id=data.store_id;
        book_name=data.name;
        book_price=data.price;

    },
     error:function(e){
         alert("获取失败");
    }
})

//添加购物车
$(function(){
    $("#mycart").click(function(){
        var lh = document.getElementsByTagName("lh");
        var cart_bookname = lh[3].innerHTML;
        var cart_price = lh[5].innerHTML;
        alert(lh[3].innerHTML);
        $.ajax({
            type:'post',//get或post
            url:'addcart',//请求的地址
            data:{
                "user_id":user_id,
                "book_id":book_id,
                "store_id":Store_id,
                "name":book_name,
                /*"image_url":*/
                "price":book_price
            },
            dataType:'json',
            success:function(res){//成功的回调函数
                alert("成功加入购物车");
            },
             error:function(e){
                 alert("加入购物车失败");
            }
        })
    })
})

//添加收藏
$(function(){
    $("#collet").click(function(){
        var lh = document.getElementsByTagName("lh");
        var cart_bookname = lh[3].innerHTML;
        var cart_price = lh[5].innerHTML;        
        alert(lh[3].innerHTML);
        $.ajax({
            type:'post',//get或post
            url:'addfavorite',//请求的地址
            data:{
                "user_id":user_id,
                "book_id":book_id,
                "store_id":Store_id,
                "name":book_name,
                "price":book_price
            },
            dataType:'json',
            success:function(res){//成功的回调函数
                alert("成功添加收藏");
            },
             error:function(e){
                 alert("加入收藏失败");
            }
        })
    })
})

