var uid = localStorage.getItem("uid")
var host = localStorage.getItem("host")
console.log("uid is null:"+isNull(uid))
console.log("host:"+host)
if(isNull(uid)){
    window.alert("您还未登录, 请先登录!")
    window.location.assign(host+"/login.html")
}

$(document).ready(function(){
    var name = localStorage.getItem("nickname");
    if(! isNull(name)){
        user.innerHTML = name+" ";
    }else{
        console.log(localStorage.getItem("username")+" ");
        user.innerHTML = localStorage.getItem("username")+" ";
    }
})

function logout(){
    removeLocalStorageItem();
    window.location.assign(host+"/index.html")
}

function goChatting(){

}
