

function login() {
    var username = $("#username");
    var password = $("#password");
    var text = $("#text")
    $.ajax({
        url:"/admin/verifyUser",
        type:"POST",
        data:{username:username.val(),password:password.val()},
        dataType:"JSON",
        success: function (data) {
            if (data.info === "0"){
                text.text("用户不存在或密码错误");
            }else if (data.info === "2"){
                text.text("您没有权限访问该页面");
            }else {
                alert("登陆成功");
                window.location.replace("/admin/backstage");
            }
        },
        error:function () {
            text.text("发生未知错误");
        }
    })
}