var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
if(isNull(uid)){
    window.alert("您还未登录, 请先登录!")
    window.location.assign(host+"/login.html")
}

function modifyInfo() {
    var username = document.getElementById("username").value
    var nickname = document.getElementById("nickname").value
    var password = document.getElementById("password").value
    var error_box = document.getElementById("error_box")

    if(isNull(username) && isNull(nickname)){
        error_box.innerHTML = "用户名或昵称, 请至少选填一项!"
        return;
    }

    if(isNull(password)){
        error_box.innerHTML = "密码不能为空! ";
        return;
    }
    if(isNull(username)){
        username = localStorage.getItem("username");
    }
    if(isNull(nickname)){
        nickname = localStorage.getItem("nickname");
    }

    const url= host+ "/user/modify";
    const Data={
        "uid": localStorage.getItem("uid"),
        "username": username,
        "password": password,
        "nickname": nickname
    }
    postData(url, Data).then(res=>{
        if(res.result == "SUCCESS"){
        　　localStorage.setItem("username", username);
        　　localStorage.setItem("nickname", nickname);
            window.location.assign(host + "/myPage.html");
        }else{
            error_box.innerHTML = res.msg;
            return;
        }
    })
}