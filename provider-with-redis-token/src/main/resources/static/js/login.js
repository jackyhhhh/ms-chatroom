function login() {
    var host = localStorage.getItem("host")
    console.log(host)
    var username = document.getElementById("username").value
    var password = document.getElementById("password").value
    var error_box = document.getElementById("error_box")
    if(isNull(username) || isNull(password)){
        error_box.innerHTML = "用户名或密码不能为空!";
        return;
    }

    const url= host+ "/user/login";
    const Data={
        "username": username,
        "password": password
    }
    putData(url, Data).then(res=>{
        console.log(res)
        if(res.errorMsg == "OK" && res.result == "SUCCESS"){
            setLocalStorageItem(res.data.uid, res.data.username, res.data.nickname)
//            window.alert(getCookie("token"))
//            localStorage.setItem("token", getCookie("token"))
            window.location.assign(host+"/myPage.html");
        }else{
            error_box.innerHTML = "登录失败, " + res.errorMsg;
        }
    })
    .catch(error=>console.log(error))
}
