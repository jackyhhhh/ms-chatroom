var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
console.log("uid:"+uid)
console.log("host:"+host)
if(isNull(uid)){
    window.alert("您还未登录, 请先登录!")
    window.location.assign(host+"/login.html")
}

function modifyInfo() {
    var username = document.getElementById("username")
    var nickname = document.getElementById("nickname")
    var password = document.getElementById("password")

    window.alert(localStorage.getItem("uid"))
    const url= host+ "/user/modify";
    const Data={
        "uid": localStorage.getItem("uid"),
        "username": username.value,
        "password": password.value,
        "nickname": nickname.value
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
        console.log(res.result);
        if(res.msg == "OK" && res.result == "SUCCESS"){
            window.alert("注册成功!")
            window.location.assign(host+"/reg_successful.html")
        }else{
            window.alert("注册失败, "+res.msg)
        }
    })
    .catch(error=>console.log(error))
}