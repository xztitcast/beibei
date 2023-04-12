document.getElementById("addButton").addEventListener("click", addConfig)

function addConfig() {
    //iframe层-父子操作

    layer.open({
        type: 2,
        title: "添加回收配置",
        area: ['700px', '450px'],
        shadeClose: true,
        shade: 0.8,
        maxmin: true,
        content: 'AddRecallConfig.html'
    });
}

layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#SingleConfigList'
        , url: 'GetSingleRecallConfig'
        , toolbar: '#toolbarSingle' //开启头部工具栏，并为其绑定左侧模板
        , cellMinWidth: 10 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'name', align: 'center', title: '名称', sort: true},
            // {field: 'type', align: 'center', title: '类型', sort: true},
            {field: 'goldnum', align: 'center', title: '金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '银元宝', sort: true},
            {fixed: 'right', align: 'center', title: '操作', toolbar: '#barDemo'}
        ]]

    });
    table.on('tool(recalllistsingle)', function (obj) {
        var data = obj.data;
        if (obj.event === 'delsingle') {
            layer.confirm('确定要删除这条配置吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "DelRecallConfig",
                    data: {name: data.name},
                    success: function (result) {
                        layer.msg(result)
                    }
                })

            }, function () {
                layer.msg("好的", {icon: 1})
            });
        }
    });

    table.render({
        elem: '#MulConfigList'
        , url: 'GetDoubleRecallConfig'
        , toolbar: '#toolbarDouble' //开启头部工具栏，并为其绑定左侧模板
        , cellMinWidth: 10 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'name', align: 'center', title: '名称', sort: true},
            // {field: 'type', align: 'center', title: '类型', sort: true},
            {field: 'goldnum', align: 'center', title: '金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '银元宝', sort: true},
            {fixed: 'right', align: 'center', title: '操作', toolbar: '#barDemo2'}
        ]]

    });
    table.on('tool(recalllistdouble)', function (obj) {
        var data = obj.data;
        if (obj.event === 'deldouble') {
            layer.confirm('确定要删除这条配置吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "DelRecallConfig",
                    data: {name: data.name},
                    success: function (result) {
                        layer.msg(result)
                    }
                })

            }, function () {
                layer.msg("好的", {icon: 1})
            });
        }
    });
});


