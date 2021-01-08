var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
document.cookie="token="+localStorage.getItem("token");

function modifyInfo() {
    var username = document.getElementById("username").value
    var nickname = document.getElementById("nickname").value
    var error_box = document.getElementById("error_box")

    if(isNull(username) && isNull(nickname)){
        error_box.innerHTML = "用户名或昵称, 请至少选填一项!"
        return;
    }
    if(isNull(username)){
        username = localStorage.getItem("username");
    }
    if(isNull(nickname)){
        nickname = localStorage.getItem("nickname");
    }

    const url= host+ "/user/modifyInfo";
    const Data={
        "uid": localStorage.getItem("uid"),
        "username": username,
        "nickname": nickname
    }
    putData(url, Data).then(res=>{
        if(res.result == "SUCCESS"){
            localStorage.removeItem("username");
            localStorage.removeItem("nickname");
            localStorage.setItem("username", username);
            localStorage.setItem("nickname", nickname);
            window.location.assign(host+"/myPage.html");
        }else{
            error_box.innerHTML = res.errorMsg;
            return;
        }
    }).catch(error=>console.log(error));
}