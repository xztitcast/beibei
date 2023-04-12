document.getElementById("CleanAll").addEventListener("mouseover", function () {
    layer.tips('一键清空帐号和角色信息，慎用', '#CleanAll');
});
document.getElementById("CleanAcc").addEventListener("mouseover", function () {
    layer.tips('这个操作会清空所有帐号和元宝数据，但角色还在', '#CleanAcc');
});
document.getElementById("CleanDdb").addEventListener("mouseover", function () {
    layer.tips('这个操作会清空所有角色数据，但帐号和元宝还在', '#CleanDdb');
});
document.getElementById("CleanAdb_log").addEventListener("mouseover", function () {
    layer.tips('清理Log日志，数据量大的话，请耐心等待提示', '#CleanAdb_log');
});
document.getElementById("CleanLdb_log").addEventListener("mouseover", function () {
    layer.tips('清理Log日志，数据量大的话，请耐心等待提示', '#CleanLdb_log');
});

document.getElementById("CleanAll").addEventListener("click", confirmDoAllClean);
document.getElementById("CleanAcc").addEventListener("click", confirmDoAccClean);
document.getElementById("CleanDdb").addEventListener("click", confirmDoDdbClean);
document.getElementById("CleanAdb_log").addEventListener("click", doCleanAdb_log);
document.getElementById("CleanLdb_log").addEventListener("click", doCleanLdb_log);
document.getElementById("CleanLimit").addEventListener("click", function () {
    layer.confirm('确定要清空登录限制吗？？？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        layer.msg("清理中，请耐心等待。。。", {
            time: 10000
        })
        $.ajax({
            type: "POST",
            url: "do_CleanDB?action=cleanLimit",
            success: function (result) {
                layer.msg(result)
            }
        })
    }, function () {

    });
})

function confirmDoAllClean() {
    layer.confirm('确定要清空所有信息吗？？？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        doCleanAcc();
        doCleanDdb();
    }, function () {
        layer.msg("好的", {icon: 1})
    });
}

function confirmDoAccClean() {
    layer.confirm('确定要清空所有帐号吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        doCleanAcc();
    }, function () {
        layer.msg("好的", {icon: 1})
    });
}


function confirmDoDdbClean() {
    layer.confirm('确定要清空所有角色吗？', {
        btn: ['确定', '再想想'] //按钮
    }, function () {
        doCleanDdb();
    }, function () {
        layer.msg("好的", {icon: 1})
    });
}

function doCleanAcc() {
    layer.msg("清理中，请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_CleanDB?action=cleanAccount",
        success: function (result) {
            layer.msg(result)
        }
    })
}

function doCleanDdb() {
    layer.msg("清理中，请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_CleanDB?action=cleanDdbData",
        success: function (result) {
            layer.msg(result)
        }
    })
}

function doCleanAdb_log() {
    layer.msg("清理中，请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_CleanDB?action=cleanAdb_log",
        success: function (result) {
            layer.msg(result)
        }
    })
}

function doCleanLdb_log() {
    layer.msg("清理中，请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "do_CleanDB?action=cleanLdb_log",
        success: function (result) {
            layer.msg(result)
        }
    })
}