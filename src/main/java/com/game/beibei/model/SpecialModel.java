package com.game.beibei.model;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialModel extends GeneralModel implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "角色名不能为空")
	private String name;
	
	@NotNull(message = "等级不能为空!")
	@Min(value = 119, message = "大飞等级不能小于119级")
	@Max(value = 179, message = "大飞等级不能大于179级")
	private Integer level;
	
	@NotNull(message = "金币数量不能空!")
	@Min(value = 0, message = "金币数量不能小于0")
	@Max(value = 2000000000, message = "金币数量不能大于20亿")
	private Integer cash;
	
	@NotNull(message = "代金券数量不能为空!")
	@Min(value = 0, message = "代金券数量不能小于0")
	@Max(value = 2000000000, message = "代金券数量不能大于20亿")
	private Integer vcash;
	
	@NotNull(message = "新旧角色不能为空!")
	@Min(value = 1, message = "新旧角色参数错误!")
	@Max(value = 2, message = "新旧角色参数错误!")
	private Integer religion;
	
	private Integer type;
	
	private Integer tao;
	
	@JsonFormat(shape = Shape.NUMBER)
	private Boolean isGuard;
	
	@JsonFormat(shape = Shape.NUMBER)
	private Boolean isChild;
	
	@JsonFormat(shape = Shape.NUMBER)
	private Boolean isSkill;
	
	@JsonFormat(shape = Shape.NUMBER)
	private Boolean isSkillDouble;
	
	private Integer guardIntimacy;
	
	private Integer childIntimacy;
	
	@NotNull(message = "门派不能为空!")
	@Min(value = 1, message = "门派参数错误!")
	@Max(value = 5, message = "门派参数错误!")
	private Integer polar;
	
	private Integer gender;
	
	private String chainParam;
	
	@Getter
	public static class Polar {
		
		private String name;
		
		private String master;
		
		private String home;
		
		private String skill;
		
		private String wufa;
		
		private String feisheng;

		public Polar() {
			super();
		}

		public Polar(int polar, int religion, String master, String home) {
			super();
			init(polar, religion, master, home);
		}
		
		public void init(int polar, int religion, String skill_level, String skill_level_base) {
			switch(polar) {
			case 1:
				this.name = "五龙山云霄洞";
				this.master = "元始天尊";
				this.home = "\"jindun-shu\":1,";
				if(religion == 1) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"ruchi-ruzui\":" + skill_level + ",\"jinguang-zhaxian\":" + skill_level + ",\"jinhong-guanri\":" + skill_level + ",\"ruhu-tianyi\":" + skill_level + ",\"jiuniu-erhu\":" + skill_level + ",\"deyi-wangxing\":" + skill_level + ",\"qichong-douniu\":" + skill_level + ", \"rumeng-chuxing\":" + skill_level + ", \"liuguang-yicai\":" + skill_level + ", \"liulian-wangfan\":" + skill_level + ",\"tiansheng-shenli\":" + skill_level + ",\"daoguang-jianying\":" + skill_level + ",";
		            this.wufa = "\"nitian-canren\":" + skill_level + ",\"huangruo-geshi\":" + skill_level + ",\"liwan-kuanglan\":" + skill_level + ",";
		            this.feisheng = "\"jinbi-huihuang\":" + skill_level_base + ",\"zhidao-huanglong\":" + skill_level_base + ",\"jincheng-tangchi\":" + skill_level_base + ",\"jinfa-fenghun\":" + skill_level_base + ", ";
				}
				if(religion == 2) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"dandao-zhiru\":" + skill_level + ",\"ruibu-kedang\":" + skill_level + ",\"qiandao-wanren\":" + skill_level + ",\"fengmang-bilou\":" + skill_level + ",\"buzhi-suocuo\":" + skill_level + ",\"danzhan-xinjing\":" + skill_level + ",\"jinghun-weiding\":" + skill_level + ",\"zhenhun-suoxin\":" + skill_level + ",\"quanli-yifu\":" + skill_level + ",\"qiguan-changhong\":" + skill_level + ",\"jianba-nuzhang\":" + skill_level + ",\"shiru-pozhu\":" + skill_level + ",";
					this.wufa = "\"wandao-jinguang\":" + skill_level + ",\"duoshen-shepo\":" + skill_level + ",\"lipi-xuanhuang\":" + skill_level + ",";
					this.feisheng = "\"jinbi-huihuang\":" + skill_level_base + ",\"zhidao-huanglong\":" + skill_level_base + ",\"jincheng-tangchi\":" + skill_level_base + ",\"jinfa-fenghun\":" + skill_level_base + ", ";
				}
				break;
			case 2:
				this.name = "终南山玉柱洞";
				this.master = "准提道人";
				this.home = "\"mudun-shu\":1,";
				if(religion == 1) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"honghua-lvye\":" + skill_level + ",\"pangen-cuojie\":" + skill_level + ",\"zhaiye-feihua\":" + skill_level + ",\"feiliu-xianshi\":" + skill_level + ",\"heding-hongfen\":" + skill_level + ",\"luoying-binfen\":" + skill_level + ",\"xiewei-shexian\":" + skill_level + ",\"bamiao-zhuzhang\":" + skill_level + ",\"jianxie-fenghou\":" + skill_level + ",\"shekou-fengzhen\":" + skill_level + ",\"huoshang-jiaoyou\":" + skill_level + ",\"shuizhang-chuangao\":" + skill_level + ",";
					this.wufa = "\"guiwu-kuteng\":" + skill_level + ",\"wanyi-shixin\":" + skill_level + ",\"jinshang-tianhua\":" + skill_level + ",";
					this.feisheng = "\"luoye-xiaoxiao\":" + skill_level_base + ",\"manwu-feitian\":" + skill_level_base + ",\"baidu-buqin\":" + skill_level_base + ",\"judu-gongxin\":" + skill_level_base + ",";
				}
				if(religion == 2) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"luoye-xiaoxiao\":" + skill_level_base + ",\"manwu-feitian\":" + skill_level_base + ",\"baidu-buqin\":" + skill_level_base + ",\"judu-gongxin\":" + skill_level_base + ",\"huawu-yefei\":" + skill_level + ",\"yanghua-feiliu\":" + skill_level + ",\"qiufeng-saoye\":" + skill_level + ",\"yiye-puti\":" + skill_level + ",\"mangci-zaibei\":" + skill_level + ",\"wufu-chongsheng\":" + skill_level + ",\"duru-gusui\":" + skill_level + ",\"jiusi-yisheng\":" + skill_level + ",\"ganzhi-ruyi\":" + skill_level + ",\"chunfeng-huayu\":" + skill_level + ",\"runwu-wusheng\":" + skill_level + ",\"tihu-guanding\":" + skill_level + ",";
					this.wufa = "\"tiannv-sanhua\":" + skill_level + ",\"zhetian-biri\":" + skill_level + ",\"miaoshou-huichun\":" + skill_level + ",";
					this.feisheng = "\"luoye-xiaoxiao\":" + skill_level_base + ",\"manwu-feitian\":" + skill_level_base + ",\"baidu-buqin\":" + skill_level_base + ",\"judu-gongxin\":" + skill_level_base + ",";
				}
				break;
			case 3:
				this.name = "凤凰山斗阙宫";
				this.master = "西方教主";
				this.home = "\"shuidun-shu\":1,";
				if(religion == 1) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"jidi-binghan\":" + skill_level + ",\"bingdong-sanchi\":" + skill_level + ",\"nubo-kuangtao\":" + skill_level + ",\"sanjiu-yanhan\":" + skill_level + ",\"yuhen-yunchou\":" + skill_level + ",\"fangwei-dujian\":" + skill_level + ",\"tianhan-didong\":" + skill_level + ",\"xuanhe-xieshui\":" + skill_level + ",\"dishui-chuanshi\":" + skill_level + ",\"tongqiang-tiebi\":" + skill_level + ",\"tiegu-zhengzheng\":" + skill_level + ",\"binglai-jiangdang\":" + skill_level + ",";
					this.wufa = "\"jiaohai-fanjiang\":" + skill_level + ",\"baoluo-wanxiang\":" + skill_level + ",\"tiandi-hunyuan\":" + skill_level + ",";
					this.feisheng = "\"shuitian-yise\":" + skill_level_base + ",\"tiema-binghe\":" + skill_level_base + ",\"shuangjia-bingdun\":" + skill_level_base + ",\"xuepiao-wanli\":" + skill_level_base + ",";
				}
				if(religion == 2) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"shuitian-yise\":" + skill_level_base + ",\"tiema-binghe\":" + skill_level_base + ",\"shuangjia-bingdun\":" + skill_level_base + ",\"xuepiao-wanli\":" + skill_level_base + ",\"shuiliu-huaxie\":" + skill_level + ",\"jishui-chengyuan\":" + skill_level + ",\"fengqi-shuiyong\":" + skill_level + ",\"xueyao-bingtian\":" + skill_level + ",\"dishui-bulou\":" + skill_level + ",\"shengou-bilei\":" + skill_level + ",\"jixue-fengshuang\":" + skill_level + ",\"riyue-hebi\":" + skill_level + ",\"tugu-naxin\":" + skill_level + ",\"fanghuan-weiran\":" + skill_level + ",\"hunran-yiti\":" + skill_level + ",\"yuxiao-yunsan\":" + skill_level + ",";
					this.wufa = "\"jiaolong-deshui\":" + skill_level + ",\"jinghua-shuiyue\":" + skill_level + ",\"shuihuo-buqin\":" + skill_level + ",";
					this.feisheng = "\"shuitian-yise\":" + skill_level_base + ",\"tiema-binghe\":" + skill_level_base + ",\"shuangjia-bingdun\":" + skill_level_base + ",\"xuepiao-wanli\":" + skill_level_base + ",";
				}
				break;
			case 4:
				this.name = "乾元山金光洞";
				this.master = "太上老君";
				this.home = "\"huodun-shu\":1,";
				if(religion == 1) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"hunbu-futi\":" + skill_level + ",\"shiwan-huoji\":" + skill_level + ",\"jiaojin-lishi\":" + skill_level + ",\"juhuo-fentian\":" + skill_level + ",\"jifeng-xunlei\":" + skill_level + ",\"xinzui-shenmi\":" + skill_level + ",\"yantian-huoyu\":" + skill_level + ",\"fengchi-dianche\":" + skill_level + ",\"shenhun-diandao\":" + skill_level + ",\"hunqian-mengying\":" + skill_level + ",\"xiansheng-duoren\":" + skill_level + ",\"xinghuo-liaoyuan\":" + skill_level + ",";
		            this.wufa = "\"lianyu-huohai\":" + skill_level + ",\"hunbu-shoushe\":" + skill_level + ",\"binggui-shensu\":" + skill_level + ",";
		            this.feisheng = "\"huoshu-yinhua\":" + skill_level_base + ",\"huifei-yanmie\":" + skill_level_base + ",\"sanmei-lianxin\":" + skill_level_base + ",\"lihuo-duopo\":" + skill_level_base + ",";
				}
				if(religion == 2) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"huoshu-yinhua\":" + skill_level_base + ",\"huifei-yanmie\":" + skill_level_base + ",\"sanmei-lianxin\":" + skill_level_base + ",\"lihuo-duopo\":" + skill_level_base + ",\"nujian-lixian\":" + skill_level + ",\"yijian-shuangdiao\":" + skill_level + ",\"jianbu-xufa\":" + skill_level + ",\"xingfei-yunsan\":" + skill_level + ",\"xinsuo-shenfeng\":" + skill_level + ",\"chongyuan-diesuo\":" + skill_level + ",\"rufeng-sibi\":" + skill_level + ",\"kunling-suoxin\":" + skill_level + ",\"jiru-xinghuo\":" + skill_level + ",\"xingchi-dianzou\":" + skill_level + ",\"dianguang-shihuo\":" + skill_level + ",\"feiyun-zhidian\":" + skill_level + ",";
		            this.wufa = "\"wanjian-chuanxin\":" + skill_level + ",\"yunmi-wusuo\":" + skill_level + ",\"huxiao-fengchi\":" + skill_level + ",";
		            this.feisheng = "\"huoshu-yinhua\":" + skill_level_base + ",\"huifei-yanmie\":" + skill_level_base + ",\"sanmei-lianxin\":" + skill_level_base + ",\"lihuo-duopo\":" + skill_level_base + ",";
				}
				break;
			case 5:
				this.name = "骷髅山白骨洞";
				this.master = "通天教主";
				this.home = "\"tudun-shu\":1,";
				if(religion == 1) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"dishu-qipo\":" + skill_level + ",\"tumo-chenmai\":" + skill_level + ",\"guci-shibi\":" + skill_level + ",\"bishi-jiuxu\":" + skill_level + ",\"youxin-wuli\":" + skill_level + ",\"luotu-feiyan\":" + skill_level + ",\"huaxian-weiyi\":" + skill_level + ",\"liushen-wuzhu\":" + skill_level + ",\"tianta-dixian\":" + skill_level + ",\"bianchang-moji\":" + skill_level + ",\"shanbeng-dilie\":" + skill_level + ",\"wangfeng-puying\":" + skill_level + ",";
		            this.wufa = "\"shipo-tianjing\":" + skill_level + ",\"tianding-sanhun\":" + skill_level + ",\"yixing-huanying\":" + skill_level + ",";
		            this.feisheng = "\"feisha-zoushi\":" + skill_level_base + ",\"kaibei-lieshi\":" + skill_level_base + ",\"xinru-panshi\":" + skill_level_base + ",\"diwo-nanfen\":" + skill_level_base + ",";
				}
				if(religion == 2) {
					this.skill = "\"lipo-qianjun\":" + skill_level + ",\"zhulian-bihe\":" + skill_level_base + ",\"leiting-qianjun\":" + skill_level_base + ",\"feisha-zoushi\":" + skill_level_base + ",\"kaibei-lieshi\":" + skill_level_base + ",\"xinru-panshi\":" + skill_level_base + ",\"diwo-nanfen\":" + skill_level_base + ",\"tubeng-wajie\":" + skill_level + ",\"chentu-feiyang\":" + skill_level + ",\"yangli-feisha\":" + skill_level + ",\"didong-shanyao\":" + skill_level + ",\"jinghuang-shicuo\":" + skill_level + ",\"ranshen-luanzhi\":" + skill_level + ",\"shenhun-piaodang\":" + skill_level + ",\"shenyao-yiduo\":" + skill_level + ",\"xuxu-shishi\":" + skill_level + ",\"gunong-xuanxu\":" + skill_level + ",\"konghuan-xushi\":" + skill_level + ",\"xukong-huanying\":" + skill_level + ",";
		            this.wufa = "\"qianyan-wanhe\":" + skill_level + ",\"jingshen-podan\":" + skill_level + ",\"xuwu-piaomiao\":" + skill_level + ",";
		            this.feisheng = "\"feisha-zoushi\":" + skill_level_base + ",\"kaibei-lieshi\":" + skill_level_base + ",\"xinru-panshi\":" + skill_level_base + ",\"diwo-nanfen\":" + skill_level_base + ",";
				}
			}
		}
		
	}
	
	public Polar get() {
		String skill_level = null;
		String skill_level_base = String.valueOf((int)(this.level * 1.6D));
		if(this.isSkillDouble) {
			skill_level = String.valueOf((int)(this.level * 2.0D));
		}else {
			skill_level = skill_level_base;
		}
		return new Polar(this.polar, this.religion, skill_level, skill_level_base);
	}

}
