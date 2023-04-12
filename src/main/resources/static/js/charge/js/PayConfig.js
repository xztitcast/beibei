layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
document.getElementById("PayConfigButton").addEventListener("click", function () {
    $.ajax({
        type: "POST",
        url: "UpdPayConfig",
        data: $('#PayConfigForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})

function getconfig() {
    $.ajax({
        type: "POST",
        url: "GetPayConfig",
        success: function (result) {
            var ac=JSON.parse(result)
            document.getElementById("payid").value=ac.payid
            document.getElementById("key").value=ac.key
            document.getElementById("ratio_gold").value=ac.ratio_gold
            document.getElementById("ratio_silver").value=ac.ratio_silver
            document.getElementById("ratio_score").value=ac.ratio_score
            if(ac.website.match("baolog")){
                $("#blwebsite").attr('checked','');
            }
            if(ac.website.match("wdsf8")){
                $("#opwebsite").attr('checked','');
            }
            if(ac.website.match("none")){
                $("#none").attr('checked','');
            }

            layui.form.render();
        }
    })
}