var ids = []
layui.use(['form', 'table'], function () {
	var form = layui.form,
		table = layui.table;
		
	var baseModel = {
		elem: '#dataList',
		id: 'dataList',
		url: '/admin/cdk/list',
	    toolbar: true,  //开启头部工具栏，让工具栏左侧显示默认的内置模板
	    cellMinWidth: 10, //全局定义常规单元格的最小宽度，layui 2.2.1 新增,
	    where: {},
	    request: {
	  		pageName: 'pageNum',
	  		limitName: 'pageSize'
	  	},
	    parseData: function(res) {
	    	return {
		  		'code': res.code,
		  		'msg': res.msg,
		  		'count': res.result.total,
		  		'data': res.result.pageList
	    	}
	 	},
	    page: { //支持传入 laypage 组件的所有参数（某些参数除外，如：jump/elem） - 详见文档
	        groups: 20, //只显示 1 个连续页码
	        limits: [10, 20, 50, 100],
	        limit: 20,
	        prev: "上一页",
	        next: "下一页",
	        first: "首页", //不显示首页
	        last: "尾页" //不显示尾页
	    },
	    cols: [[
			{type:'checkbox'},
	        {field: 'number', align: 'center', title: 'CDK'},
	        {field: 'name', align: 'center', title: '物品名称'},
	        {field: 'status', align: 'center', title: '使用状态', templet: '#statusTpl'},
	        {field: 'account', align: 'center', title: '使用帐号'},
			{field: 'updated', align: 'center', title: '使用时间', sort: true},
			{field: 'created', align: 'center', title: '添加时间', sort: true},
	        {fixed: 'right', align: 'center',title: '操作', toolbar: '#dataToolbar'}
	    ]]
	}
	form.on('submit(search)', function(data) {
		baseModel.where = data.field
		table.reload('dataList', baseModel)
		return false
	})
	table.on('tool(dataListFilter)', function (obj) {
	    var data = obj.data;
		if(obj.event === 'remove') {
	        remove([data.id])
	    }
	})
	table.on('checkbox(dataListFilter)', function() {
		ids = table.checkStatus('dataList').data.map(e => e.id)
	})
	
	table.render(baseModel);
})

function removeAll(){
	if(ids.length === 0){
		layer.msg("请选择需要删除的行", {icon: 5, time: 1000})
	}else{
		remove(ids)
	}
}

function remove(ids = []){
	layer.confirm('确定要删除该记录吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        $.ajax({
	        type: "POST",
	        url: "/admin/cdk/delete",
	        dataType: 'json',
	        contentType:"application/json",
	        data: JSON.stringify(ids),
	        success: function (res) {
				if(res.code === 0){
					layer.msg(res.msg, {icon: 1, time: 1000})
					location.reload()
				}else{
					layer.msg(res.msg, {icon: 5, time: 1000})
				}
	        }
	    })
    }, function () {
        layer.msg("好的", {icon: 1})
    });
}