layui.use('form', function () {
    var form = layui.form;
    //监听提交
});

layui.use('table', function () {
    var table = layui.table;
    table.render({
        elem: '#CdkList'
        , url: 'getCdk?type=polartrans&name=pt&status=0'
        , toolbar: true   //开启头部工具栏，让工具栏左侧显示默认的内置模板
        , cellMinWidth: 10 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            groups: 5 //只显示 1 个连续页码
            , limits: [5, 10, 15, 20, 10000]
            , limit: 15
            , prev: "上一页"
            , next: "下一页"
            , first: "首页" //不显示首页
            , last: "尾页" //不显示尾页
        }
        , cols: [[
            {field: 'cdk', align: 'center', title: 'CDK', sort: true},
            {field: 'name', align: 'center', title: '物品名称', width: '13%', sort: true},
            {field: 'status', align: 'center', title: '使用状态', width: '10%', sort: true},
            {field: 'account', align: 'center', title: '使用帐号', width: '10%', sort: true},
            {field: 'usetime', align: 'center', title: '使用时间', sort: true},
            {field: 'addtime', align: 'center', title: '添加时间', sort: true},
        ]]
    })
});

document.getElementById("select").addEventListener("click", function () {
    var type = document.getElementById("type").value;
    var name = document.getElementById("name").value;
    var status = document.getElementById("status").value;
    var table = layui.table;
    table.render({
        elem: '#CdkList'
        , url: 'getCdk?type=' + type + '&name=' + name + '&status=' + status
        , toolbar: true   //开启头部工具栏，让工具栏左侧显示默认的内置模板
        , cellMinWidth: 10 //全局定义常规单元格的最小宽度，layui 2.2.1 新增
        , page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            groups: 5 //只显示 1 个连续页码
            , limits: [5, 10, 15, 20, 10000]
            , limit: 15
            , prev: "上一页"
            , next: "下一页"
            , first: "首页" //不显示首页
            , last: "尾页" //不显示尾页
        }
        , cols: [[
            {field: 'cdk', align: 'center', title: 'CDK', sort: true},
            {field: 'name', align: 'center', title: '物品名称', width: '13%', sort: true},
            {field: 'status', align: 'center', title: '使用状态', width: '10%', sort: true},
            {field: 'account', align: 'center', title: '使用帐号', width: '10%', sort: true},
            {field: 'usetime', align: 'center', title: '使用时间', sort: true},
            {field: 'addtime', align: 'center', title: '添加时间', sort: true},
        ]]
    })
})

document.getElementById("delButton").addEventListener("click", function () {
    layer.confirm('确定要清空当前CDK吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: "delCdks",
            data: $('#CDKForm').serialize(),
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });
})
document.getElementById("delAllButton").addEventListener("click", function () {
    layer.confirm('确定要清空所有CDK吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
            type: "POST",
            url: "delAllCdks",
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });
})


document.getElementById("addButton").addEventListener("click", function () {
    layer.open({
        type: 2,
        title: "添加CDK",
        area: ['1000px', '600px'],
        shadeClose: true,
        shade: 0.8,
        content: 'addCdks.html'
    });
})
document.getElementById("getnames").addEventListener("click", getCdkNames)

function getCdkNames() {
    var type = document.getElementById("type").value;
    $("#name").empty();
    $.ajax({ //
        type: "POST",
        url: "GetCdkNames?type=" + type,
        success: function (result) {
            var arr = result.split("-")
            for (i = 0; i < arr.length; i++) {
                document.getElementById("name").add(new Option(arr[i], arr[i]))
            }
            layui.form.render()
        }
    })
}


