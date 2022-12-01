var translation = $("#translation");
var source = $("#source");
var text = $("#text");

function submitSource() {
    $.ajax({
        url:"/admin/addSource",
        type:"POST",
        data:{translation:translation.val(),source:source.val()},
        dataType:"JSON",
        success:function (data) {
            text.text(JSON.stringify(data))
        },
        error:function () {
            text.text("error2");
        }
    })
}