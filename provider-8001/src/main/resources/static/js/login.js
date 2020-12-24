function login() {
    var host = localStorage.getItem("host")
    console.log(host)
    var username = document.getElementById("username")
    var password = document.getElementById("password")
    var oError = document.getElementById("error_box")
    var isError = true;
    if (username.value.length > 20 || username.value.length < 3) {
        oError.innerHTML = "用户名请输入3-20位字符";
        isError = false;
        return;
    }else if((username.value.charCodeAt(0)>=48) && (username.value.charCodeAt(0)<=57)){
        oError.innerHTML = "首字符必须为字母";
        return;
    }else for(var i=0;i<username.value.charCodeAt(i);i++){
        if((username.value.charCodeAt(i)<48)||(username.value.charCodeAt(i)>57) && (username.value.charCodeAt(i)<97)||(username.value.charCodeAt(i)>122)){
        oError.innerHTML = "必须为字母跟数字组成";
        return;
        }
    }

    if (password.value.length > 20 || password.value.length < 1) {
        oError.innerHTML = "密码请输入1-20位字符"
        isError = false;
        return;
    }

    const url= host+ "/user/login";
    const Data={
        "username": username.value,
        "password": password.value
    }
    const otherParams={
        method:"POST",
        mode: "cors",
        headers:{
            "Content-Type": "application/json"
        },
        body: JSON.stringify(Data),
    };

    fetch(url, otherParams)
    .then(data=>{return data.json()})
    .then(res=>{
        if(res.msg == "OK" && res.result == "SUCCESS"){
            localStorage.setItem("uid", res.obj.uid);
            localStorage.setItem("username", res.obj.username);
            localStorage.setItem("nickname", res.obj.nickname);
            window.location.assign(host+"/myPage.html");
        }else{
            window.alert("登录失败, "+res.msg);
        }
    })
    .catch(error=>console.log(error))
}
