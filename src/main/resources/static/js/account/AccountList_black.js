

layui.use('table', function () {
    var table = layui.table;

    table.render({
        elem: '#AccountList_black'
        , url: 'getAccountList_black'
        , toolbar: true  //开启头部工具栏，让工具栏左侧显示默认的内置模板
        , cellMinWidth: 1 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , cols: [[
            {field: 'account', align: 'center', title: '帐号',width:'10%', sort: true},
            {field: 'password', align: 'center', title: '密码', width:'10%',sort: true},
            {field: 'email', align: 'center', title: '超级密码', width:'10%',sort: true},
            {field: 'gold_coin', align: 'center', title: '金元宝', width:'8%',sort: true},
            {field: 'silver_coin', align: 'center', title: '银元宝', width:'8%',sort: true},
            {field: 'privilege', align: 'center', title: '权限', width:'8%',sort: true},
            {field: 'blocked_reason', align: 'center', title: '封禁原因',width:'26%', sort: true},
            {field: 'update_time', align: 'center', title: '更新时间', width:'12%',sort: true},
            {fixed: 'right', align: 'center',title: '操作', width:'8%',toolbar: '#barDemo_locked'}
        ]]

    });
    table.on('tool(acclist_black)', function (obj) {
        var data = obj.data;
        if (obj.event === 'unlock') {
            layer.confirm('确定要解封该帐号吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "unBlockMacAccount",
                    data: {account: data.account},
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
