var host = localStorage.getItem("host")

function checkUsername(){
    var username = document.getElementById("username_reg").value
    var error_box1 = document.getElementById("checkUsernameLabel1")
    var error_box2 = document.getElementById("checkUsernameLabel2")
    var url = host + "/user/checkUsername?username=" + username;

    if(isNull(username)){
        error_box1.innerHTML = "";
        error_box2.innerHTML = "";
    }

    fetch(url, {
            method: "GET",
            mode: "cors",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        }).then(data=>{return data.json()})
        .then(res=>{
            if(res.msg == "OK" && res.result == "SUCCESS"){
                error_box1.innerHTML = ''
                if(! isNull(username)){
                    error_box2.innerHTML = '<font color="green">&radic;</font>'
                }
            }else{
                error_box2.innerHTML = '<font color="red">&radic;</font>'
                error_box1.innerHTML = '<font color="red">' + res.msg + '</font>'
            }
        })
        .catch(error=>console.log(error))


}

function register() {
    var username = document.getElementById("username_reg")
    var password = document.getElementById("password")
    var nickname = document.getElementById("nickname")
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

    const url= host+ "/user/reg";
    const Data={
        "username": username.value,
        "password": password.value,
        "nickname": nickname.value
    }

    postForSuccess(url, Data, "reg_successful.html", oError)
}