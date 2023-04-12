layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
document.getElementById("GetRoles_send").addEventListener("click", function () {
    var account=document.getElementById("sendAccount").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "../../User/UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_send").empty();
            for (let namesKey in names) {
                document.getElementById("rolename_send").add(new Option(names[namesKey],namesKey))
            }
            layui.form.render()
        }
    })
})
document.getElementById("SendButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "AdminSendItem",
        data: $('#SendItemForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})