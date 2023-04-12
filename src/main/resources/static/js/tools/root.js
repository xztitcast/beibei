layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
layui.use('element', function () {
    var element = layui.element;

    //一些事件监听
    element.on('tab(demo)', function (data) {
        console.log(data);
    });
});
//普通注册
document.getElementById("NoralRegButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=gmreg",
        data: $('#RegForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
})
//修改密码
document.getElementById("CPwdButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=gmcpwd",
        data: $('#CpwdForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
})
//修改权限
document.getElementById("CPriButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=gmchangeprivilege",
        data: $('#CpriForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
})
//充值元宝
document.getElementById("doChargeButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=charge_coin",
        data: $('#ChargeForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
})
//修改元宝
document.getElementById("doSetButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=set_coin",
        data: $('#ChargeForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
})
//全区充值元宝
document.getElementById("doChargeButtonAll").addEventListener("click", function () {
    layer.confirm('确定要全区充值元宝吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "do_GMTool?action=charge_coinAll",
            data: $('#ChargeForm').serialize(),
            success: function (result) {
                layer.msg(result, {icon: 1})
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });
})
//全区修改元宝
document.getElementById("doSetButtonAll").addEventListener("click", function () {
    layer.confirm('确定要全区修改元宝吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "do_GMTool?action=set_coinAll",
            data: $('#ChargeForm').serialize(),
            success: function (result) {
                layer.msg(result, {icon: 1})
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });


})
//删除帐号
document.getElementById("doDelButton").addEventListener("click", function () {
    layer.confirm('确定要删除帐号吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "do_GMTool?action=delAccount",
            data: $('#DelAccountForm').serialize(),
            success: function (result) {
                layer.msg(result, {icon: 1})
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });
})
//角色转移
document.getElementById("MoveRoleButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "GMMoveRole",
        data: $('#MoveRole').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})
//获取角色转移角色名
document.getElementById("GetRoles_moverole").addEventListener("click", function () {
    var account=document.getElementById("account_moverole").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "../../User/UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_moverole").empty();
            for (var namesKey in names) {
                document.getElementById("rolename_moverole").add(new Option(names[namesKey],namesKey))
            }
            layui.form.render()
        }
    })
})
/*大飞相关*/
parseInt(Math.random() * (10000 + 1), 10);
var num = Math.floor(Math.random() * (10000 + 1));
document.getElementById("RoleName").setAttribute("value", "最爱千纤" + num)
//大飞注册
document.getElementById("DaFeiRegButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=dfreg",
        data: $('#GMdafeiForm').serialize(),
        success: function (result) {
            layer.msg(result, {icon: 1})
        }
    })
});
//检测角色名
document.getElementById("RoleName").addEventListener("blur", function () {
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=checkRole",
        data: $('#RoleName'),
        success: function (result) {
            layer.tips(result, "#RoleName")
        }
    })
})
//检测大飞帐号
document.getElementById("acc").addEventListener("blur", function () {
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=checkUserDF",
        data: $('#acc'),
        success: function (result) {
            layer.tips(result, "#acc")
        }
    })
})
//检测普通帐号
document.getElementById("normalacc").addEventListener("blur", function () {
    $.ajax({
        type: "POST",
        url: "do_GMTool?action=checkUser",
        data: $('#normalacc'),
        success: function (result) {
            layer.tips(result, "#normalacc")
        }
    })
})
