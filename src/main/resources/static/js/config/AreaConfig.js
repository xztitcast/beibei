layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
document.getElementById("UpdConfigButton").addEventListener("click", function () {
    layer.msg("请耐心等待。。。", {
        time: 10000
    })
    $.ajax({
        type: "POST",
        url: "updconfig",
        data: $('#updAreaForm').serialize(),
        success: function (result) {
            layer.msg(result)
            form.render('RegStatus', 'button');
        }
    })
});


var btns = document.getElementById('copykey');
btns.addEventListener("click", function () {
    var clipboard = new ClipboardJS(btns);
    clipboard.on('success', function (e) {
        layer.msg("复制成功")
    });
})
url = "c"+window.btoa("http://"+window.location.host+"/Admin/Config/AreaConfig/GetAreaConfig")
document.getElementById('copykey').setAttribute("data-clipboard-text", url)
function getAreaConfig() {
    $("#copykey").click()
    $.ajax({
        type: "POST",
        url: "GetAreaConfig_GM",
        success: function (result) {
            var ac = JSON.parse(result)
            document.getElementById("area_name").value = ac.area_name
            document.getElementById("area_showname").value = ac.area_showname
            document.getElementById("game_ip").value = ac.game_ip
            document.getElementById("game_port").value = ac.game_port
            document.getElementById("gold_coin").value = ac.gold_coin
            document.getElementById("silver_coin").value = ac.silver_coin
            document.getElementById("sqlip").value = ac.sqlip
            document.getElementById("sqlport").value = ac.sqlport
            document.getElementById("sqluser").value = ac.sqluser
            document.getElementById("sqlpwd").value = ac.sqlpwd
            document.getElementById("public_inf").value = ac.public_inf
            document.getElementById("fbgs_task_gold").value = ac.fbgs_task_gold
            document.getElementById("fbgs_task_silver").value = ac.fbgs_task_silver
            document.getElementById("ssx_task_gold").value = ac.ssx_task_gold
            document.getElementById("ssx_task_silver").value = ac.ssx_task_silver
            document.getElementById("polarTrans_task_gold").value = ac.polarTrans_task_gold
            document.getElementById("polarTrans_task_silver").value = ac.polarTrans_task_silver
            document.getElementById("limitNum").value = ac.limitNum
            document.getElementById("chargeLink").value = ac.chargeLink
            document.getElementById("cdkLink").value = ac.cdkLink
            if (ac.isReg === 1) {
                $("#isReg").attr('checked', '');
            }
            if (ac.isDFReg === 1) {
                $("#isDFReg").attr('checked', '');
            }
            if (ac.isFaBaoGongSheng === 1) {
                $("#isFaBaoGongSheng").attr('checked', '');
            }
            if (ac.isShuangShuXing === 1) {
                $("#isShuangShuXing").attr('checked', '');
            }
            if (ac.isPolarTrans === 1) {
                $("#isPolarTrans").attr('checked', '');
            }
            if (ac.isFaBaoGongShengCDK === 1) {
                $("#isFaBaoGongShengCDK").attr('checked', '');
            }
            if (ac.isShuangShuXingCDK === 1) {
                $("#isShuangShuXingCDK").attr('checked', '');
            }
            if (ac.isPolarTransCDK === 1) {
                $("#isPolarTransCDK").attr('checked', '');
            }
            layui.form.render();
        }
    })
}





