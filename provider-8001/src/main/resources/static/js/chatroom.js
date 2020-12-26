$(document).ready(function(){
    var int=self.setInterval("clock()", 1000);
})

function clock(){
    var msg_box = document.getElementById("msg_box");
    var record = document.getElementById("record");
    var msg = document.getElementById("msg");
    // 重置窗口消息
    msg_box.innerHTML = "";

    var username = localStorage.getItem("username");
    var host = localStorage.getItem("host");
    console.log("username:"+username)
    var pageNumber = 0;
    var pageSize = 10;
    var url = host + "/msg/describeMsgData?username=" + username + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;

    console.log("num:"+pageNumber)
    console.log("size:"+pageSize)
    fetch(url, {
            method: "GET",
            mode: "cors",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
    }).then(data=>{return data.json()})
    .then(res=>{
        console.log("res.result:"+res.result)
        if(res.msg == "OK" && res.result == "SUCCESS"){
            var content = res.obj.content;
            for(var m in content){
                console.log(content[m].username+":"+content[m].content);
                if(content[m].username === username){
                    record.setAttribute("class", "msgright");
                }else{
                    record.setAttribute("class", "msgleft");
                }
                msg.innerHTML = res.obj.content[m].content;
                msg_box.appendChild(record);
            }
        }else{
            window.alert(res.msg);
            return;
        }
    })
    .catch(error=>console.log(error))
}

