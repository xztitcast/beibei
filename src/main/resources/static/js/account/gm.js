layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#AccountList_GM'
        , url: 'getGMAccountList'
        , toolbar: true   //开启头部工具栏，让工具栏左侧显示默认的内置模板
        , cellMinWidth: 10 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            groups: 5 //只显示 1 个连续页码
            , limits: [5, 10, 15, 20]
            , limit: 15
            , prev:"上一页"
            , next:"下一页"
            , first: "首页" //不显示首页
            , last: "尾页" //不显示尾页
        }
        , cols: [[
            {field: 'account', align: 'center', title: '帐号', sort: true},
            {field: 'password', align: 'center', title: '密码', sort: true},
            {field: 'email', align: 'center', title: '超级密码', sort: true},
            {field: 'gold_coin', align: 'center', title: '金元宝', sort: true},
            {field: 'silver_coin', align: 'center', title: '银元宝', sort: true},
            {field: 'privilege', align: 'center', title: '权限', sort: true},
            {field: 'update_time', align: 'center', title: '更新时间', sort: true},
            {fixed: 'right', align: 'center',title: '操作', toolbar: '#barDemo'}
        ]]

    });
    table.on('tool(acclist_gm)', function (obj) {
        var data = obj.data;
        if (obj.event === 'unGM') {
            layer.confirm('确定要撤销该帐号的GM权限吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "../Tools/GMTool/do_GMTool?action=gmchangeprivilege&privilege=0",
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


