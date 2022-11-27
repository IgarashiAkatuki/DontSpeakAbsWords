var text = $("#text");
function getWords() {
    $.ajax({
        url:"/api/returnWords",
        type: "POST",
        success: function (data) {
            text.text(data)
        },
        error:function (){
            text.text("发生未知错误")
        }

    })
}

function addWords() {
    let word = prompt("请填入想要被翻译的抽象词");

    if (word !== null && word !== ""){
        $.ajax({
            url:"/api/addWords",
            type:"POST",
            data:{word:word},
            dataType:"JSON",
            success:function (data) {
                alert(JSON.stringify(data));
            },
            error:function (data) {
                alert("发生未知错误");
            }
        })
    }else{
        alert("请输入合法的数据")
    }

}

$("#inputWords").blur(function (){
    let words = $("#inputWords").val();
    $.ajax({
        url:"/api/getTranslationsFromTemp",
        type:"POST",
        data:{word:words},
        dataType: "JSON",
        success:function (data) {
            // text.text("");
            text.text(JSON.stringify(data))
            // var info =  JSON.parse(data);
            // for (let x in data) {
            //     text.append(x.translation+"   "+x.likes);
            // }
            //
        },
        error:function () {
            text.text("发生未知错误")
        }
    })
})

function queryWord() {
    let word = prompt("您想为哪个抽象词贡献释义");
    if (word !== null && word !== ""){
        $.ajax({
            url:"/api/queryWord",
            type:"POST",
            data:{word:word},
            dataType:"JSON",
            success:function (data) {
                if (data.info === '1'){
                    submitTranslations(word)
                }else {
                    alert("没有查找到数据,请先提交抽象词")
                }
            },
            error:function (data) {
                alert("发生未知错误")
            }
        })
    }else {
        alert("请输入合法的数据")
    }

}

function submitTranslations(text) {
    let word = prompt("请填入["+text+"]的释义");
    $.ajax({
        url:"/api/submitTranslationsToTemp",
        type:"POST",
        data:{word:text,translation:word},
        dataType:"JSON",
        success:function (data) {
            alert(data.info);
        },
        error:function (data) {
            alert("提交失败");
        }
    })
}

function getRandomQuestionnaire() {
    $.ajax({
        url:"/api/getRandomQuestionnaire",
        type:"POST",
        dataType:"JSON",
        success:function (data) {
            text.text(JSON.stringify(data))
        },
        error:function (data) {
            text.text("获取问卷失败")
        }
    })
}

function addLikes() {
    let translation = prompt("InputTranslation");
    $.ajax({
        url:"/api/temp",
        type:"POST",
        data:{translation:translation},
        dataType:"JSON",
        success:function (data) {
            if (data.info === "1"){
                $.ajax({
                    url:"/addLikesToPersistence",
                    type:"POST",
                    data:{translation:translation},
                    dataType:"JSON",
                    success:function (data) {
                        text.text("添加成功")
                    },
                    error:function () {
                        text.text("ERROR2")
                    }
                })
            }else {
                text.text("查询失败")
            }
        },
        error:function () {
            text.text("ERROR1")
        }
    })
}

