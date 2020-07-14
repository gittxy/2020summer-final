var user_name= localStorage.user_name;
alert(user_name);

window.onload = function () {
    $("#hint").hide();
  }
  function register() {
     window.location.href = "./register";
  }

  function produsave() {
    var id = $("#num").val();
    var event_name = $("#name").val();
    var event_time = $("#ptime1").val();
    var location = $("#total").val();
    var person = $("#state").val();
    

    req = { "id": id, "event_name": event_name, "event_time": event_time, "location": location, "person": person, "tel": tel, "desc": desc };
    $.ajax({
        type: 'POST',
        url: "/add_block",
        data: JSON.stringify(req),
        dataType: "json",
        success: function (data) {

        alert("update")
        window.location.reload();
        }
    });
  }

  function delRow(){
    if(number>1){
        
        flag=!flag;
        table1.deleteRow(table1.rows.length-1);
        number--;
    }
}

window.onload = function () {
    $.ajax({
        type: 'GET',
        url: "/load_somesource",
        dataType: "json",
        success: function (data) {
            var number = data['number'];
            for (var i = 1; i <= number; i++) {
                for (var j = (i - 1) * 7 + 1; j <= 7 * i; j++) {
                    var message = data[String(i)];
                    obj = document.getElementById(String(j));
                    obj.innerHTML = String(message[String((j - 1) % 7 + 1)]) + "<br>";
                }
            }
        }
    });
}