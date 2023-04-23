layui.use('form', function () {
    var form = layui.form;
    form.on('submit(dataFormSubmitFilter)', function(data) {
        $.ajax({
            type: "POST",
            url: "/admin/mode/score",
            dataType: 'json',
            data: JSON.stringify(data.field),
            success: function (result) {
                layer.msg(result)
            }
        })
    })
});

function getRole() {
    var account = $("#account").val()
    $.ajax({
        type: "GET",
        url: `/admin/general/gidList/${account}`,
        success: function (res) {
            if(res && res.code === 0) {
                $("#gidList").empty();
                var gidList = JSON.parse(res.result)
                for(var idx in gidList){
                    var item = gidList[idx]
                    $("#gidList").add(new Option(item.label, item.value))
                }
            }
            layui.form.render()
        }
    })
}