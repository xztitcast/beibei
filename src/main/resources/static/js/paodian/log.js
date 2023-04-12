layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#PaoDianLog'
        , url: 'GetPaoDianLog'
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
            {field: 'account', align: 'center', title: '帐号', sort: true},
            {field: 'goldnum', align: 'center', title: '金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '银元宝', sort: true},
            {field: 'time', align: 'center', title: '时间', sort: true}
        ]]

    });

});


document.getElementById("demoReload").addEventListener("click", function () {
    var acc=document.getElementById("acc").value
    var table = layui.table;
    table.render({
        elem: '#PaoDianLog'
        , url: 'GetPaoDianLog?account='+acc
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
            {field: 'account', align: 'center', title: '帐号', sort: true},
            {field: 'goldnum', align: 'center', title: '金元宝', sort: true},
            {field: 'silvernum', align: 'center', title: '银元宝', sort: true},
            {field: 'time', align: 'center', title: '时间', sort: true}
        ]]

    });

})






document.getElementById("cleanPaoDianLog").addEventListener("click", function () {
    layer.confirm('确定要清空日志吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: "CleanPaoDianLog",
            success: function (result) {
                layer.msg(result,{icon:1})
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });


})


