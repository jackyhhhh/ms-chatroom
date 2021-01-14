var host = localStorage.getItem("host");
function onload(){
    var label = document.getElementById("checkStatus")
//    getData(host + "/checkToken").then(res=>{
//        if(isNotNull(res) && res.msg.indexOf("token无效") != -1){
//            label.innerHTML = '<a href="login.html"><h3>立即登录</h3></a>';
//        }else{
//            label.innerHTML = '<a href="myPage.html"><h3>我的主页</h3></a>';
//        }
//    }).catch(error=>console.log(error));
//
    fetch(host + "/checkToken",{
        method: "GET",
        mode: "cors",
        headers: {
            "Accept": "application/json",
        }
    })
    .then(response => response.json()) // parses response to JSON
    .then(res=>{
        if(isNotNull(res) && res.msg.indexOf("token无效") != -1){
            console.log(res);
            label.innerHTML = '<a href="login.html"><h3>立即登录</h3></a>';
        }else{
            label.innerHTML = '<a href="myPage.html"><h3>我的主页</h3></a>';
        }
    })

}