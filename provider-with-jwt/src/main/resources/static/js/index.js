$(document).ready(function(){
    var uid = localStorage.getItem("uid");
    var label = document.getElementById("checkStatus")
    if(isNull(uid)){
        label.innerHTML = '<a href="login.html"><h3>立即登录</h3></a>';
    }else{
        label.innerHTML = '<a href="myPage.html"><h3>我的主页</h3></a>';
    }
})