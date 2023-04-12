
layui.use('form', function () {
    var form = layui.form;
    //监听提交
});


document.getElementById("addButton").addEventListener("click", function () {
    layer.open({
        type: 2,
        title: "添加配置",
        area: ['700px', '450px'],
        shadeClose: true,
        shade: 0.8,
        content: 'addChargeConfig.html'
    });
})
document.getElementById("delButton").addEventListener("click", function () {
    layer.confirm('确定要清空所有配置吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: "CleanChargeConfig",
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });

})

function getConfig() {

    $.ajax({
        type: "POST",
        url: "GetChargeConfig",
        success: function (result) {
            document.getElementById("ChargeConfigSpan").innerHTML=result
        }
    })
}






