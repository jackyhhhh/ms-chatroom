var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
console.log("uid is null:"+isNull(uid))
console.log("host:"+host)
//if(isNull(uid)){
//    window.alert("您还未登录, 请先登录!")
//    window.location.assign(host+"/login.html")
//}

function showDeleteElement(){
    var deleteElements = document.getElementById("deleteElements");
    deleteElements.removeAttribute("hidden")
    var show_del = document.getElementById("show_del");
    show_del.setAttribute("disabled", "disabled")
}

function removeUser(){
    var del_pwd = document.getElementById("del_pwd").value;
    var username = localStorage.getItem("username");
    var error_box = document.getElementById("error_box");
    var Data = {
        "username": username,
        "password": del_pwd
    }
    url = host + "/user/removeUser"
    postForSuccess(url, Data, "index.html", error_box)

}

function cancelRemove(){
    var deleteElements = document.getElementById("deleteElements")
    deleteElements.setAttribute("hidden", "hidden")
    var show_del = document.getElementById("show_del");
    show_del.removeAttribute("disabled")

}

function onload(){
    checkToken();
    var name = localStorage.getItem("nickname");
    if(! isNull(name)){
        user.innerHTML = name+" ";
    }else{
        user.innerHTML = localStorage.getItem("username")+" ";
    }
}


function logout(){
    removeLocalStorageItem();
    getData(host + "/user/logout?uid="+uid).then(res=>{
        if(isNotNull(res) && res.result=="FAILED"){
            window.alert("发生未知错误");
        }
        console.log(res)
        window.alert(res)
    })
    window.location.assign(host+"/index.html")
}

function goChatting(){
    var chatId = localStorage.getItem("chatId");
    console.log("chatId: "+chatId);
    if(isNull(chatId)){
        chatId = randomString(8);
        localStorage.setItem("chatId", chatId);
        var username = localStorage.getItem("username");
        var url = host + "/user/turnOn?username=" + username;

        getData(url).then(res=>{
            if(res.msg == "OK" && res.result == "SUCCESS"){
                window.location.assign(host+"/chatroom.html");
            }else{
                window.alert(res.msg);
                return;
            }
        })
        .catch(error=>console.log(error))
    }else {
        window.location.assign(host+"/chatroom.html");
    }

}
