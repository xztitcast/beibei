layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
document.getElementById("ConfigButton").addEventListener("click", function () {
	layer.msg("请耐心等待。。。", {
            time: 10000
        })
    $.ajax({
        type: "POST",
        url: "SetPaoDianConfig",
        data: $('#PaoDianConfigForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})
function getconfig() {
    $.ajax({
        type: "POST",
        url: "GetPaoDianConfig",
        success: function (result) {
            var ac=JSON.parse(result)
            document.getElementById("goldnum").value=ac.goldnum
            document.getElementById("silvernum").value=ac.silvernum
        }
    })
}
