layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#RecallLog'
        , url: 'GetRecallLog'
        , toolbar:true  //开启头部工具栏，让工具栏左侧显示默认的内置模板
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
            {field: 'account', align: 'center', title: '回收帐号', sort: true},
            {field: 'name', align: 'center', title: '回收物品名称', sort: true},
            {field: 'num', align: 'center', title: '回收数量', sort: true},
            {field: 'goldnum', align: 'center', title: '回收金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '回收银元宝', sort: true},
            {field: 'time', align: 'center', title: '回收时间', sort: true}
        ]]

    });

});

document.getElementById("demoReload").addEventListener("click", function () {
    var acc=document.getElementById("acc").value
    var table = layui.table;
    table.render({
        elem: '#RecallLog'
        , url: 'GetRecallLog?account='+acc
        , toolbar:true  //开启头部工具栏，让工具栏左侧显示默认的内置模板
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
            {field: 'account', align: 'center', title: '回收帐号', sort: true},
            {field: 'name', align: 'center', title: '回收物品名称', sort: true},
            {field: 'num', align: 'center', title: '回收数量', sort: true},
            {field: 'goldnum', align: 'center', title: '回收金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '回收银元宝', sort: true},
            {field: 'time', align: 'center', title: '回收时间', sort: true}
        ]]

    });
})











document.getElementById("cleanRecallLog").addEventListener("click", function () {
    layer.confirm('确定要清空日志吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: "CleanRecallLog",
            success: function (result) {
                layer.msg(result,{icon:1})
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });


})


