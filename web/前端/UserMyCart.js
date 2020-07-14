var user_name= localStorage.user_name;
alert(user_name);

res=[{"cart_id":9,"user_id":2,"book_id":1,"store_id":1,"name":"python从入门到实践","image_url":"http://img3m0.ddimg.cn/67/4/24003310-1_b_5.jpg","price":61.50,"num":1,"money":61.50}];

var book_name = document.getElementById('book_name');
var book_price = document.getElementById('book_price');
var book_num=document.getElementById('book_num');

var book_name_1 = document.getElementById('book_name_1');
var book_price_1= document.getElementById('book_price_1');
var book_num_1=document.getElementById('book_num_1');

book_name.textContent=res[0].name;
book_price.textContent=res[0].price;
book_num.textContent=res[0].num;


$.ajax({
    type:'POST',//get或post
    url:'',//请求的地址
    data:{},
    dataType:'json',//text,json,xml,jsonp
    success:function(res){//成功的回调函数
        if(res=null){
            alert("购物车为空");
        }else{
            book_name.textContent=res[0].name;
            book_price.textContent=res[0].price;
            book_num.textContent=res[0].num;

        }
    }
})