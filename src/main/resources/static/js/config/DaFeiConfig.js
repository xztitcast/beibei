layui.use('form', function () {
    var form = layui.form;
    //监听提交
});
document.getElementById("UpdDFConfigButton").addEventListener("click", function () {
	layer.msg("请耐心等待。。。", {
            time: 10000
        })
    $.ajax({
        type: "POST",
        url: "do_DaFeiConfig?action=updconfig",
        data: $('#updDaFeiForm').serialize(),
        success: function (result) {
            layer.msg(result)
        }
    })
});
document.getElementById("level").addEventListener("mouseover", function () {
    layer.tips('输入自定义等级，以空格分离，且不能低于119', '#level');
})
function getDFConfig() {
    $.ajax({
        type: "POST",
        url: "do_DaFeiConfig?action=getDFConfig",
        success: function (result) {
            var j=JSON.parse(result)
            document.getElementById("tao").value=j.daFei_tao
            document.getElementById("cash").value=j.daFei_Cash
            document.getElementById("vcash").value=j.daFei_vCash
            document.getElementById("level").value=j.level
            document.getElementById("guardIntimacy").value=j.guardIntimacy
            document.getElementById("childIntimacy").value=j.childIntimacy
            document.getElementById("chengwei").value=j.chengwei
            document.getElementById("yinLingFan_level").value=j.yinLingFan_level
            document.getElementById("gift").value = j.gift
            if(j.isSkill===1){
                $("#isSkill").attr('checked','');
            }
            if(j.isSkilldouble===1){
                $("#isSkilldouble").attr('checked','');
            }
            if(j.isChild===1){
                $("#isChild").attr('checked','');
            }
            if(j.isGuard===1){
                $("#isGuard").attr('checked','');
            }
            if(j.isFaBaoGongSheng===1){
                $("#isFaBaoGongSheng").attr('checked','');
            }
            if(j.isShuangShuXing===1){
                $("#isShuangShuXing").attr('checked','');
            }
            if(j.isYinLingFan===1){
                $("#isYinLingFan").attr('checked','');
            }
            layui.form.render();
        }
    })
}


