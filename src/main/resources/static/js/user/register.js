layui.use('form', function () {
    var form = layui.form;
    layer = layui.layer;
    //监听提交
});
layui.use('element', function () {
    var element = layui.element;
});
var checkacc = ""
var roleResult = ""
var accResult = ""
var num = Math.floor(Math.random() * (10000 + 1));//生成随机数，填充到角色名称的表单处
//获取需要扣除的元宝
var fbgs_task_gold = ""
var fbgs_task_silver = ""
var ssx_task_gold = ""
var ssx_task_silver = ""
var polarTrans_task_gold = ""
var polarTrans_task_silver = ""
//=普通帐号检测帐号是否存在=
var normalAccRes = ""
document.getElementById("NormalAcc").addEventListener("blur", isAccountExist);
//=普通注册判断帐号存在1111111111111
function isAccountExist() {
    $.ajax({
        type: "POST",
        url: "UserAccountCheck",
        data: $('#NormalAcc'),
        success: function (result) {
            accResult = result
            layer.tips(result, "#NormalAcc", {
                tipsMore: true
            })
            normalAccRes = result
        }
    })
}
function isAccountExistDF() {
    $.ajax({
        type: "POST",
        url: "UserAccountCheck",
        data: $('#DaFeiAcc'),
        success: function (result) {
            accResult = result
            layer.tips(result, "#DaFeiAcc", {
                tipsMore: true
            })
            normalAccRes = result
        }
    })
}
function isRoleExit() {
    $.ajax({
        type: "POST",
        url: "UserCheckRole",
        data: $('#RoleName'),
        success: function (result) {
            roleResult = result
            layer.tips(result, "#RoleName", {
                tipsMore: true
            })
        }
    })
}
function checkDFReg() {
    isRoleExit();
    isAccountExistDF();
    if (accResult.match("恭喜")) { //检测一下用户名和角色是否可用
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({    //执行注册操作
            type: "POST",
            url: "UserDFReg",
            data: $('#DaFeiRegForm').serialize(),
            success: function (result) {
                layer.msg(result)
                accResult = ""
                roleResult = ""
            }
        })
    } else {
        layer.alert("请更换可用的帐号或用户名后重试！")
    }
}
function doChangePWD() {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "UserChangePWD",
        data: $('#CPwdForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
}
function isAccountExistByCharge() {
    $.ajax({
        type: "POST",
        url: "UserPayAccountCheck",
        data: $('#payAcc'),
        success: function (result) {
            checkacc = result
            layer.tips(result, "#payAcc", {
                tipsMore: true,
                tips: 1
            })
        }
    })
}
function isAllowCharge() {
    isAccountExistByCharge()
    if (checkacc.match("恭喜")) {
        PayForm.submit()
    } else {
        layer.alert("帐号不存在")
        return
    }
}
//执行注册按钮前确认帐号检测通过才可以=1111111111111111
document.getElementById("RegButton").addEventListener("click", function () {
    isAccountExist()
    if (normalAccRes.match("恭喜")) { //检测一下用户名是否可用
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "UserReg",
            data: $('#RegForm').serialize(),
            success: function (result) {
                layer.msg(result)
                normalAccRes = ""
            }
        })
    } else {
        layer.alert("请更换可用的帐号后重试！")
    }
});
document.getElementById("RoleName").setAttribute("value", "贝贝问道" + num)
//判断大飞帐号是否存在
document.getElementById("DaFeiAcc").addEventListener("blur", isAccountExistDF)
//判断角色是否存在==
document.getElementById("RoleName").addEventListener("blur", isRoleExit)
//大飞注册
document.getElementById("DaFeiRegButton").addEventListener("click", checkDFReg)
//修改密码
document.getElementById("ChangepWDButton").addEventListener("click", doChangePWD)
//帐号充值
document.getElementById("payAcc").addEventListener("blur", isAccountExistByCharge)
//获取充值比例
document.getElementById("showratio").addEventListener("click", function () {
    $.ajax({
        type: "POST",
        url: "../Admin/Charge/GetChargeConfig",
        success: function (result) {
            layer.msg(result, {
                time: 10000
            })
        }
    })
})
//自助1009
document.getElementById("1009Button").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "UserUnlock1009",
        data: $('#unlock1009Form').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})
//添加法宝共生双属性
document.getElementById("addFaBaoGongShengButton").addEventListener("click", function () {
    layer.confirm('添加法宝共生需要扣除' + fbgs_task_gold + '金元宝和' + fbgs_task_silver + '银元宝！', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "UserFBGSandSSX?action=addFaBaoGongSheng",
            data: $('#addAbility').serialize(),
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });

})
document.getElementById("addShuangShuXingButton").addEventListener("click", function () {
    layer.confirm('添加双属性需要扣除' + ssx_task_gold + '金元宝和' + ssx_task_silver + '银元宝！', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "UserFBGSandSSX?action=addShuangShuXing",
            data: $('#addAbility').serialize(),
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {
        layer.msg("好的", {icon: 1})
    });

})
//门派转换
document.getElementById("TransPolarButton").addEventListener("click", function () {
    layer.confirm('转换门派需要扣除' + polarTrans_task_gold + '金元宝和' + polarTrans_task_silver + '银元宝！', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "UserPolarTrans",
            data: $('#TransPolar').serialize(),
            success: function (result) {
                layer.msg(result)
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
        url: "UserMoveRole",
        data: $('#MoveRole').serialize(),
        success: function (result) {
           layer.msg(result)
        }
    })
})
//CDK兑换
document.getElementById("doCdkChange").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "UserCdkExchange",
        data: $('#CDKForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
})

//获取法宝共生双属性角色名
document.getElementById("GetRoles_fbgsssx").addEventListener("click", function () {
    var account=document.getElementById("account_fbgsssx").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_fbgsssx").empty();
            for (var namesKey in names) {
                document.getElementById("rolename_fbgsssx").add(new Option(names[namesKey],namesKey))
            }
            layui.form.render()
        }
    })
})
//获取门派转换角色名
document.getElementById("GetRoles_polartrans").addEventListener("click", function () {
    var account=document.getElementById("account_polartrans").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_polartrans").empty();
            for (var namesKey in names) {
                document.getElementById("rolename_polartrans").add(new Option(names[namesKey],names[namesKey]))
            }
            layui.form.render()
        }
    })
})
//获取CDK角色名
document.getElementById("GetRoles_cdk").addEventListener("click", function () {
    var account=document.getElementById("account_cdk").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_cdk").empty();
            for (var namesKey in names) {
                document.getElementById("rolename_cdk").add(new Option(names[namesKey],namesKey))
            }
            layui.form.render()
        }
    })
})
//获取充值积分大使积分角色名
document.getElementById("GetRoles_charge").addEventListener("click", function () {
    var account=document.getElementById("payAcc").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "UserGetRoleNames?account="+account,
        success: function (result) {
            var names = JSON.parse(result)
            $("#rolename_charge").empty();
            for (var namesKey in names) {
                document.getElementById("rolename_charge").add(new Option(names[namesKey],namesKey))
            }
            layui.form.render()
        }
    })
})
//获取角色转移角色名
document.getElementById("GetRoles_moverole").addEventListener("click", function () {
    var account=document.getElementById("account_moverole").value;
    $.ajax({ //获取角色名和GID填充到option中
        type: "POST",
        url: "UserGetRoleNames?account="+account,
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


function init() {
    $.ajax({ //获取区组配置来获取扣除元宝数量并配置一下页面的显示与隐藏
        type: "POST",
        url: "../Admin/Config/AreaConfig/GetAreaConfig",
        success: function (result) {
            var ac = JSON.parse(result)
            fbgs_task_gold = ac.fbgs_task_gold
            fbgs_task_silver = ac.fbgs_task_silver
            ssx_task_gold = ac.ssx_task_gold
            ssx_task_silver = ac.ssx_task_silver
            polarTrans_task_gold = ac.polarTrans_task_gold
            polarTrans_task_silver = ac.polarTrans_task_silver
            if(ac.isPolarTransCDK===0){
                document.getElementById("isPolarTransCDK").style.display="none"
            }
            if(ac.isDFReg===0){
                document.getElementById("dfregctrl").style.display="none"
                document.getElementById("dfregctrl2").style.display="none"
            }
            if(ac.isFaBaoGongSheng===0&&ac.isShuangShuXing===0){
                document.getElementById("fbssxctrl").style.display="none"
                document.getElementById("fbssxctrl2").style.display="none"
            }
            if(ac.isPolarTrans===0){
                document.getElementById("polartransctrl").style.display="none"
                document.getElementById("polartransctrl2").style.display="none"
            }
        }
    })
    $.ajax({ //获取大飞配置来获取可注册的等级段
        type: "POST",
        url: "../Admin/Config/DaFeiConfig/do_DaFeiConfig?action=getDFConfig",
        success: function (result) {
            var ac = JSON.parse(result)
            var levelarrs = ac.level.split(" ")
            for(i=0;i<levelarrs.length;i++){
                document.getElementById("level").add(new Option(levelarrs[i],levelarrs[i]))
            }
            layui.form.render()
        }
    })
    $.ajax({ //获取充值配置来确定充值页面显示开关
        type: "POST",
        url: "../Admin/Charge/GetPayConfig",
        success: function (result) {
            var ac = JSON.parse(result)
            if(ac.website.match("none")){
                document.getElementById("pay1").style.display="none"
                document.getElementById("pay2").style.display="none"
            }
            layui.form.render()
        }
    })
}
