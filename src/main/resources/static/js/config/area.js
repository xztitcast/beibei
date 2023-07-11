layui.use('form', function () {
    var form = layui.form;
    form.on('submit(dataFormSubmit)', function(data) {
        $.ajax({
            type: "POST",
            url: "/admin/mode/item",
            dataType: 'json',
            data: JSON.stringify(data.field),
            success: function (res) {
                layer.msg(res.message)
            }
        })
    })
})

function init() {
    $.ajax({
        type: "GET",
        url: "/admin/config/info",
        success: function (res) {
            console.log(res.result)
            layui.form.val('setDataForm', res.result)
        }
    })
}