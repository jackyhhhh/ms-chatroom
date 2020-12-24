var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
console.log("uid:"+uid)
console.log("host:"+host)
if(isNull(uid)){
    window.alert("您还未登录, 请先登录!")
    window.location.assign(host+"/login.html")
}

function modifyPassword() {
    var newPwd1 = document.getElementById("newPwd1").value
    var newPwd2 = document.getElementById("newPwd2").value
    console.log("new1:"+newPwd1)
    console.log("new2:"+newPwd2)
    if(newPwd1 === newPwd2){
        var oldPwd = document.getElementById("oldPwd").value
        console.log("old:"+oldPwd)
        const url= host+ "/user/modifyPassword";
        const Data={
            "uid": uid,
            "oldPwd": oldPwd,
            "newPwd": newPwd1
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
                window.alert("修改密码成功!")
                window.location.assign(host+"/myPage.html")
            }else{
                window.alert("修改密码失败, "+res.msg)
            }
        })
        .catch(error=>console.log(error))
    }else{
        window.alert("两次输入的新密码不一样, 请重新输入! ")
        return;
    }
}