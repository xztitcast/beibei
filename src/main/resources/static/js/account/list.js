layui.use(['table', 'form'], function () {
    var table = layui.table,
    	form = layui.form;
    var baseModel = {
        elem: '#dataList',
        id: 'dataList',
        url: '/admin/account/list',
        toolbar: true,   //开启头部工具栏，让工具栏左侧显示默认的内置模板
        cellMinWidth: 10, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
	  	request: {
	  		pageName: 'pageNum',
	  		limitName: 'pageSize'
	  	},
	  	where: {},
	  	parseData: function(res) {
	    	return {
		  	'code': res.code,
		  	'msg': res.msg,
		  	'total': res.result.total,
		  	'data': res.result.pageList
	    	}
	 	},
        page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
            groups: 10, //只显示 1 个连续页码
            limits: [10, 20, 50, 100],
            limit: 20,
            prev:"上一页",
            next:"下一页",
            first: "首页", //不显示首页
            last: "尾页" //不显示尾页
        },
        cols: [[
            {field: 'account', align: 'center', title: '帐号'},
            {field: 'password', align: 'center', title: '密码'},
            {field: 'email', align: 'center', title: '超级密码'},
            {field: 'goldCoin', align: 'center', title: '金元宝'},
            {field: 'silverCoin', align: 'center', title: '银元宝'},
            {field: 'privilege', align: 'center', title: '权限'},
            {field: 'blockedReason', align: 'center', title: '封禁原因'},
            {field: 'updateTime', align: 'center', title: '更新时间'},
            {fixed: 'right', align: 'center',title: '操作', toolbar: '#dataToolbar'}
        ]]
    }
    form.on('submit(search)', function(data) {
		console.log(data)
		baseModel.where = data.field
		table.reload('dataList', baseModel)
		return false
	})
    table.on('tool(dataListFilter)', function (obj) {
        var data = obj.data;
        if (obj.event === 'locked') {
            layer.prompt({title: '帐号：'+data.account+' 请输入封禁原因'}, function(val, index){
                layer.close(index);
                $.ajax({
                    type: "POST",
                    url: "/admin/account/block/" + data.account,
                    contentType: 'application/x-www-form-urlencoded',
                    data: 'message=' + val,
                    success: function (res) {
                        layer.msg(res)
                    }
                })

            });
        }
        if(obj.event === 'unlock'){
			layer.confirm('确定要解封这个账号吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "/admin/account/unblock/"  + data.account,
                    success: function (res) {
                        layer.msg(res)
                    }
                })
            }, function () {
                layer.msg("好的", {icon: 1})
            })
		}
        if (obj.event === 'black') {
            layer.confirm('确定要拉黑这台帐号登录的机器吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "/admin/account/block/"  + data.account,
                    success: function (res) {
                        layer.msg(res)
                    }
                })
            }, function () {
                layer.msg("好的", {icon: 1})
            })
        }
        if(obj.event === 'unGM') {
			layer.confirm('确定要撤销该帐号的GM权限吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "/admin/account/privilege",
                    contentType: 'application/x-www-form-urlencoded',
                    data: 'account='.concat(data.account).concat('&').concat('privilege=0'),
                    success: function (res) {
						layer.msg(res.msg)
                    }
                })
            }, function () {
                layer.msg("好的", {icon: 1})
            });
		}
		if (obj.event === 'del') {
            layer.confirm('确定要删除该帐号吗？', {
                btn: ['确定', '再想想'] //按钮
            }, function () {
                $.ajax({
                    type: "POST",
                    url: "/admin/account/delete",
                    dataType: 'json',
                    data: [data.account],
                    success: function (result) {
                        layer.msg(result)
                    }
                })
            }, function () {
                layer.msg("好的", {icon: 1})
            });
        }
    })
    table.render(baseModel);
})