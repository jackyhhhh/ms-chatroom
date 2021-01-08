var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
document.cookie="token="+localStorage.getItem("token");
console.log("uid:"+uid)
console.log("host:"+host)
//if(isNull(uid)){
//    window.alert("您还未登录, 请先登录!")
//    window.location.assign(host+"/login.html")
//}

function modifyPassword() {
    var newPwd1 = document.getElementById("newPwd1").value
    var newPwd2 = document.getElementById("newPwd2").value
    var error_box = document.getElementById("error_box")
    if(newPwd1 === newPwd2){
        var oldPwd = document.getElementById("oldPwd").value
        const url= host+ "/user/modifyPassword";
        const Data={
            "uid": uid,
            "oldPwd": oldPwd,
            "newPwd": newPwd1
        }
        putData(url, Data).then(res=>{
            if(res.result == "SUCCESS"){
                window.location.assign(host + "/myPage.html");
            }else{
                error_box.innerHTML = res.errorMsg;
                return;
            }
        }).catch(error=>console.log(error));
    }else{
        error_box.innerHTML = "两次输入的新密码不一样, 请重新输入! ";
        return;
    }
}