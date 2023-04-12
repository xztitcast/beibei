package com.game.beibei.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.common.Constant.Sys;
import com.game.beibei.common.utils.ChecksumUtil;
import com.game.beibei.entity.ddb1.Data;
import com.game.beibei.enums.Branch;
import com.game.beibei.enums.Path;
import com.game.beibei.mapper.DataMapper;
import com.game.beibei.model.MoveModel;
import com.game.beibei.model.SpecialModel;
import com.game.beibei.model.SpecialModel.Polar;
import com.game.beibei.service.DataService;
import com.game.beibei.support.FastDataConvert;
import com.game.beibei.support.FastDataConvert.DataFormatTemplate;
import com.game.beibei.support.chain.MoveRoleChainHandler;
import com.game.beibei.support.chain.RegisterChainHandler;
import com.game.beibei.support.chain.StandardChainHandler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.CharsetUtil;

@DS("ddb1")
@Service("dataService")
public class DataServiceImpl extends ServiceImpl<DataMapper, Data> implements DataService {
	
	private static final String DELIMITER = ":";
	
	private static final String COMMA = ",";
	
	public static abstract class AbstractDataServiceChainHandler implements RegisterChainHandler {
		
		@Autowired
		protected DataService dataService;
		
		protected StandardChainHandler handler;
	}

	@Component
	public static class LoginDataChainHandler extends  AbstractDataServiceChainHandler implements MoveRoleChainHandler {
		
		private static final String SEPARATOR = "#";

		private static final String CONTENT = "([\"rec_role\":\"" + SEPARATOR + "\",\"create_time\":1556371353,\"chars\":({\"" + SEPARATOR + "\",}),\"safe_status\":0,\"register_time\":0,])";

		@Override
		@Transactional
		public boolean doHandler(SpecialModel sm) {
			Data entity = new Data();
			entity.setBranch("");
			entity.setPath(Path.login.name());
			entity.setName(sm.getAccount());
			entity.setContent(CONTENT.replaceAll(SEPARATOR, sm.getChainParam()));
			entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
			entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
			boolean save = this.dataService.save(entity);
			if(save) {
				save = ((RegisterChainHandler) this.handler).doHandler(sm);
			}
			return save;
		}

		/**
		 * 3
		 */
		@Override
		@Transactional
		public boolean doHandler(MoveModel mm) {
			Data source = this.dataService.getDataByMultiId(Path.login.name(), mm.getSource(), "");
			String content = source.getContent();
			int lastIndexOf = content.indexOf(COMMA);
			String prefix = content.substring(0, lastIndexOf);
			String suffix = content.substring(lastIndexOf).replace("\"" + mm.getChainParam() + "\",", "");
			String gids = suffix.substring(suffix.indexOf("chars") + 9);
			int indexOf = gids.indexOf(",}),");
			if(indexOf == -1) {
				this.dataService.remveByMultiId(source);
			}else if(prefix.contains(mm.getChainParam())){
				gids = gids.substring(0, indexOf).replace("\"", "");
				String[] split = gids.split(COMMA);
				prefix = prefix.replace(mm.getChainParam(), split[0]);
			}else {
			}
			source.setContent(prefix + suffix);
			source.setChecksum(ChecksumUtil.checksum(source.getPath() + source.getName() + source.getContent()));
			boolean status = this.dataService.updateByMultiId(source);
			
			Data entity = this.dataService.getDataByMultiId(Path.login.name(), mm.getTarget(), "");
			if(entity == null) {
				entity = new Data();
				entity.setBranch("");
				entity.setPath(Path.login.name());
				entity.setName(mm.getTarget());
				entity.setContent(CONTENT.replaceAll(SEPARATOR, mm.getChainParam()));
				entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
				entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
				status = this.dataService.save(entity);
			}else {
				entity.setContent(entity.getContent().replace("chars\":({", "chars\":({\"" + mm.getChainParam() + "\","));
				entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
				status = this.dataService.updateByMultiId(entity);
			}
			return status;
		}
		
		@Override
		public void setNextHandler(StandardChainHandler handler) {
			super.handler = handler;
		}
	}
	
	@Component
	public static class UserLevelChainHandler extends AbstractDataServiceChainHandler implements MoveRoleChainHandler {

		@Override
		@Transactional
		public boolean doHandler(SpecialModel sm) {
			Polar polar = sm.get();
			String lifeSkill = "\"xuanhuan-shu\":60,\"qianliyan\":60,\"qiangshen-shu\":60,\"xiudao-shu\":60,\"shenti-shu\":60,";
			String yinlingfan = "\"liaodi-xianji\":115,\"yulu-huanyuan\":115,\"tianji-shenjia\":115,\"wuxing-xiangsheng\":115,\"yaowang-shending\":115,\"wuxing-xiangfu\":115,\"ruyou-shenzhu\":115,\"lingli-zengfu\":115,\"yiya-huanya\":115,\"shixue-kuangluan\":115,\"xieling-futi\":115,\"shibu-kedang\":115,\"dadao-lunhui\":115,\"fali-wubian\":115,\"tuiling-xuezhou\":115,\"tianjiang-xiafan\":115,\"houfa-zhiren\":115,\"sanyuan-guiyi\":115,\"duhua-chengkong\":115,\"youchou-bibao\":115,\"jingang-zhiqu\":115,\"nujiao-lianzhan\":115,\"kexue-qishu\":115,\"gonggong-mieshi\":115,\"jinshen-bumie\":115,\"ruhuan-simeng\":115,\"nujiao-liezhan\":115,"; //引灵幡
			String content = null;
			if(sm.getLevel() == 119) {
				content = "([\"upgrade_attrib\":([\"wood\":0,\"life\":13437,\"max_stamina\":218,\"settings\":({({\"USER\",({2,308,113,}),}),}),\"exp_to_next_level\":17310115,\"die_stat\":([\"times\":3,\"time\":1596020510,]),\"equip_page\":0,\"earth\":0,\"con\":" + sm.getLevel() + ",\"level_up_time\":1581,\"exp_ex\":0,\"polar_already\":([\"wood\":0,\"earth\":0,\"water\":0,\"total\":0,\"fire\":0,\"metal\":0,]),\"medicine_used\":([\"sanqingwan\":0,\"baohua-yuluwan\":0,]),\"total_pk\":0,\"metal\":0,\"str\":119,\"wiz\":119,\"stamina\":109,\"dex\":119,\"icon\":7009,\"def\":615,\"lock_exp\":0,\"tao_ex\":0,\"trace_task\":({}),\"phy_power\":635,\"mag_power\":635,\"medicine\":([\"life\":0,\"mana\":0,\"polar_point\":0,\"attrib_point\":0,]),\"fire\":0,\"dodge\":0,\"task\":([203:([\"state\":\"x\",\"sub_name\":\"慈以至勇\",\"snap_log\":\"当前提示：经历地劫中的第八次劫难，“勇”之真谛已悟。第九劫“以哀泣亡”将在达到#R122#n级时降临，届时可去#Z雪域冰原#Z的#P无底洞#P一探。\",\"upgrade_type\":2,]),415:([\"snap_log\":\"当前提示：你可以在#Y南极仙翁#n处领取到#R110级#n升级奖励。\",\"upgrade_type\":2,\"level\":110,]),]),\"speed\":286,\"pets_state\":([]),\"mana\":9041,\"water\":0,\"polar_point\":88,\"pause_level_up\":0,\"feedback\":([\"ti_last_apply\":0,\"apply_num\":0,]),\"accurate\":0,\"attrib_point\":472,\"level\":119,\"attrib_already\":([\"dex\":0,\"con\":0,\"total\":0,\"str\":0,\"wiz\":0,]),\"max_life\":13437,\"exp\":100,\"max_mana\":9041,\"parry\":0,\"tao\":0,\"portrait\":7009,]),\"position\":([\"room\":\"/lanxianzhenwai/lanxianzhenwai.c\",\"dir\":6,\"x\":105,\"y\":157,]),\"me\":([\"resist_lost\":0,\"durability\":100,\"life\":13437,\"type\":1,\"religion\":" + sm.getReligion() + ",\"stat_coin_cost\":([\"wcoin\":3224600,\"mcoin\":3224600,\"winfo\":({}),\"minfo\":({}),\"wuptime\":1595779200,\"muptime\":1593532800,\"lstime\":1596021986,]),\"friend_converted\":3,\"earth\":0,\"seizure_max_cash\":\"提示：你的现金数量超过了携带上限，超出的#R207,293,955#n文钱#n已被存入钱庄。\",\"exp_ex\":0,\"voucher\":" + sm.getVcash() + ",\"service\":([\"位列仙班\":([\"end_time\":1562063805,\"start_time\":1559731005,\"year_insider_et\":1627122067,]),]),\"task_finished\":([\"apprentice_task\":1,]),\"last_login_time\":1596018034,\"resisit_wood\":0,\"store_converted\":1,\"polar\":" + sm.getPolar() + ",\"lock_exp\":1,\"block_state\":0,\"polar_wood\":0,\"trace_task\":({\"【新手】1-9级新手任务,0\",}),\"has_trade_goods\":0,\"max_cash\":118375155,\"today_played_time\":4716,\"task\":([1020:([\"ver\":1,]),1000:([\"state\":\"0\",\"ti\":1601203640,\"upgrade_type\":0,]),768:([\"state\":\"0\",]),754:([\"state\":\"0\",\"et\":1753372799,]),729:([\"state\":\"S2\",\"alias\":38,\"upgrade_type\":0,]),644:([\"state\":\"0\",\"upgrade_type\":0,]),574:([\"state\":\"0\",]),415:([\"level\":110,\"upgrade_type\":0,]),339:([\"state\":\"1\",\"upgrade_type\":0,\"finish\":([]),]),243:([\"state\":\"b\",\"ver\":1,]),204:([\"owner_gid\":\"" + sm.getChainParam() + "\",\"rt\":1596018842,\"log\":\"当前提示：飞升-引路人，寻找指引飞升的引路人，凝结出自己的元婴，让真身突破125级上限。（引路人完成，获得血婴！可以在人物状态栏切换血婴。当前阶段任务已完成，请将#R血婴#n等级提升至#R130#n级。#R血婴#n等级达到#R130#n级后，切换成#R血婴#n状态前往#P南华真人#P处领取飞升任务。）#R完成所有飞升任务后的效果：#n#R可以学习第二门派攻击技能；增加仙魔属性点；激活法宝相性效果；每通过一个劫难任务（天劫、地劫任务），角色真身的可分配单项相性点上限提升1点等#n\",\"upgrade_type\":0,\"dbase\":([\"state\":\"完成任务\",\"attack_xh\":1,\"init_time\":([\"1596022680\":\"" + sm.getName() + "\",]),]),]),1105:([\"ti\":1596018127,\"bonus\":({0,1,0,}),\"times\":0,\"tao\":14880,]),1091:([\"et\":1596383999,\"total\":480,]),1074:([\"state\":\"0\",]),1076:([\"state\":\"0\",]),1079:([\"state\":\"0\",\"upgrade_type\":0,]),1061:([\"et\":1596383999,\"total\":120,]),21:([\"state\":0,\"times\":0,\"new_ver\":1,\"max_times\":1,\"bonus_time\":1595951999,\"upgrade_type\":0,]),]),\"speed\":286,\"init_basic_info\":1,\"name\":\"" + sm.getName() + "\",\"accurate\":0,\"region\":([\"flag\":1,\"province\":1,\"city\":1,]),\"double_cash\":2,\"title_basic\":\"第一代弟子\",\"protect_bonus\":([\"ti\":1596018035,\"hour\":0,]),\"year_insider\":([\"et\":1598610067,\"days\":30,]),\"double_balance\":2,\"resist_fire\":0,\"resist_frozen\":0,\"max_life\":13437,\"extra_mana\":44000000,\"max_mana\":9041,\"tao\":" + sm.getTao() * 360 + ",\"soul_cob_rate\":0,\"portrait\":6004,\"account\":\"" + sm.getAccount() + "\",\"question\":([\"answer_times\":0,]),\"last_privilege\":0,\"gender\":" + sm.getGender() + ",\"last_logout_time\":1596022750,\"polar_water\":0,\"gold_coin\":" + sm.getGoldCoin() + ",\"polar_earth\":0,\"exp_to_next_level\":1731012,\"ip_region\":([\"province\":\"北京市\",\"city\":\"东城区\",]),\"character\":([\"kindness\":0,\"harmony\":0,\"desc\":\"普普通通\",\"carefulness\":0,\"courage\":0,]),\"restriction\":([\"login_time\":1596018034,]),\"cur_pk\":0,\"resist_forgotten\":0,\"total_pk\":0,\"str\":119,\"cur_ver\":17,\"wiz\":119,\"stamina\":104,\"icon\":6004,\"polar_fire\":0,\"def\":615,\"last_level_up_time\":1596019615,\"max_durability\":100,\"tao_ex\":0,\"appellation_ids\":([7:33554432,11:4096,4:16,]),\"resist_melt\":0,\"total_played_time\":4716,\"upgrade_magic\":0,\"newbie\":1,\"top_data\":([\"speed\":50,\"def\":25,\"phy_power\":45,\"mag_power\":45,]),\"dodge\":0,\"first_login_ip\":\"127.0.0.1\",\"resist_sleep\":0,\"max_assign_polar\":38,\"family\":\"" + polar.getName() + "\",\"title\":\"\",\"polar_point\":88,\"resist_water\":0,\"resisit_metal\":0,\"wudou\":([\"last_cost_time\":1595779200,]),\"max_account_lv\":119,\"attrib_already\":([]),\"limit_per_day\":([\"94\":1,\"92\":1,\"ti\":1596018100,\"168\":1,\"928\":0,\"400\":1,\"886\":12674213,\"895\":67932,\"889\":2147483647,\"258\":7,]),\"parry\":0,\"login_times\":1,\"wood\":0,\"cash\":" + sm.getCash() + ",\"pot\":500,\"first_get_upgrade_time\":1596019524,\"resist_wood\":0,\"master\":\"" + polar.getMaster() + "\",\"con\":119,\"reputation\":0,\"level_up_time\":117,\"version_prompt\":([\"1.60\":1,]),\"first_login_time\":1596018034,\"resist_poison\":0,\"dex\":119,\"energy\":1500,\"create_time\":1596018033,\"generate_time\":1596022750,\"last_login_ip\":\"127.0.0.1\",\"resist_metal\":0,\"phy_power\":635,\"anticheater_info\":([\"interval\":4716,\"total_steps\":2297,\"last_move_time\":1596022678,]),\"recover_energy_time\":1596021806,\"balance\":207293955,\"fire\":0,\"medicine\":([]),\"resist_lock\":0,\"age\":0,\"mana\":9041,\"water\":0,\"feedback\":([]),\"attrib_point\":494,\"level\":119,\"unique_data\":([5:294928,2:8388608,0:536870912,3:5177344,1:67584,]),\"appellation\":([\"family\":\"" + polar.getName() + "第一代弟子\",]),\"act_stat\":([\"ti\":1596018035,\"mons\":({0,0,0,}),\"ytd\":([]),\"td\":([\"13\":3224600,\"5\":46,\"3\":0,\"2\":67932,\"4\":500,\"1\":2145051313,\"0\":1596022593,]),]),\"last_login_mac\":\"0000c48e8ff82596\",\"resist_earth\":0,\"max_stamina\":218,\"settings\":([\"convert\":1,]),\"die_stat\":([\"times\":1,\"time\":1596019120,]),\"equip_page\":0,\"pker\":0,\"task_round\":([\"962\":0,]),\"polar_already\":([]),\"metal\":0,\"medicine_used\":([]),\"max_balance\":231005910,\"salary\":([\"online_time\":([\"1596384000\":4715,]),]),\"upgrade\":([\"max_polar_extra\":8,\"bonus\":1,\"max_level\":119,\"attrib_point\":22,\"attrib_total\":22,\"type\":2,\"ti_modify\":1335590733,\"create_time\":1596019524,\"state\":0,\"ever_lv\":119,\"cur_ver\":6,]),\"resisit_fire\":0,\"resist_repress\":0,\"previous_login_ip\":\"127.0.0.1\",\"max_energy\":1500,\"resisit_earth\":0,\"resist_cage\":0,\"mag_power\":635,\"appellation_time_limit\":([\"年费会员\":([\"limit\":1596027347,]),]),\"limit_trade_coin\":0,\"title_type\":\"family\",\"resist_confusion\":0,\"resisit_water\":0,\"gid\":\"" + sm.getChainParam() + "\",\"user_converted\":7,\"silver_coin\":" + sm.getSilverCoin() + ",\"limit_per_month\":([\"ti\":1596018127,\"11\":1,\"8\":0,\"7\":66742,]),\"achieve\":848,\"pause_level_up\":0,\"have_coin_pwd\":0,\"first_login_mac\":\"0000c48e8ff82596\",\"total_score\":46,\"polar_metal\":0,\"extra_life\":43962838,\"logout_time\":1596022750,\"exp\":1750144631,\"newbie_gift\":1,\"scroll\":([\"time\":1596018457,]),\"insider_time\":40905,]),\"discover_world\":([\"1\":([]),]),\"settings\":({({\"USER\",({2,308,113,}),}),({\"\",({80,0,0,}),({76,0,1,}),({37,0,0,}),({64,0,0,}),({78,0,0,}),({72,0,0,}),({88,0,0,}),({50,0,0,}),({65,0,0,}),({33,0,1,}),({41,0,0,}),({57,0,0,}),({74,0,0,}),({31,0,0,}),({52,0,0,}),({66,0,0,}),}),}),\"skills_map\":([]),\"skills\":([" + polar.getHome() + lifeSkill + yinlingfan + polar.getSkill() + polar.getWufa() + polar.getFeisheng() + "]),])";  
			}else {
				content = "([\"upgrade_attrib\":([\"wood\":0,\"life\":16495,\"max_stamina\":233,\"settings\":({}),\"exp_to_next_level\":111024092,\"die_stat\":([\"times\":0,\"time\":0,]),\"equip_page\":0,\"earth\":0,\"con\":134,\"level_up_time\":5601,\"exp_ex\":5,\"polar_already\":([\"wood\":0,\"earth\":0,\"water\":0,\"total\":0,\"fire\":0,\"metal\":0,]),\"medicine_used\":([\"sanqingwan\":0,\"baohua-yuluwan\":0,]),\"total_pk\":0,\"metal\":0,\"str\":134,\"wiz\":134,\"stamina\":116,\"dex\":134,\"icon\":7009,\"def\":690,\"lock_exp\":0,\"tao_ex\":0,\"trace_task\":({}),\"phy_power\":710,\"mag_power\":710,\"medicine\":([\"life\":0,\"mana\":0,\"polar_point\":0,\"attrib_point\":0,]),\"fire\":0,\"dodge\":0,\"task\":([203:([\"state\":\"x\",\"sub_name\":\"静笃虚极\",\"snap_log\":\"\",\"upgrade_type\":2,]),415:([\"snap_log\":\"当前提示：你可以在#Y南极仙翁#n处领取到#R125级#n升级奖励。\",\"upgrade_type\":2,\"level\":125,]),]),\"speed\":316,\"pets_state\":([]),\"mana\":11135,\"water\":0,\"polar_point\":103,\"pause_level_up\":0,\"feedback\":([\"ti_last_apply\":0,\"apply_num\":0,]),\"accurate\":0,\"attrib_point\":532,\"level\":" + sm.getLevel() + ",\"attrib_already\":([\"dex\":0,\"con\":0,\"total\":0,\"str\":0,\"wiz\":0,]),\"max_life\":16495,\"exp\":1787570287,\"max_mana\":11135,\"parry\":0,\"tao\":0,\"portrait\":7009,\"exp_to_be_added\":([]),]),\"position\":([\"room\":\"/lanxianzhenwai/lanxianzhenwai.c\",\"dir\":3,\"x\":102,\"y\":154,]),\"me\":([\"resist_lost\":0,\"durability\":100,\"life\":53209,\"type\":1,\"religion\":" + sm.getReligion() + ",\"stat_coin_cost\":([\"wcoin\":0,\"mcoin\":3784,\"winfo\":({0,0,3784,0,0,}),\"minfo\":({0,0,}),\"wuptime\":1584950400,\"muptime\":1583049600,\"lstime\":1583391536,]),\"friend_converted\":3,\"earth\":0,\"exp_ex\":0,\"voucher\":" + sm.getVcash() + ",\"service\":([\"位列仙班\":([\"end_time\":1576684800,\"start_time\":1568822400,]),\"八卦神符\":([\"dur\":2960,]),]),\"task_finished\":([\"apprentice_task\":1,]),\"last_login_time\":1585282773,\"resisit_wood\":0,\"store_converted\":1,\"polar\":" + sm.getPolar() + ",\"block_state\":0,\"polar_wood\":0,\"trace_task\":({\"新手】1-9级新手任务,0\",\"天劫任务,0\",}),\"has_trade_goods\":0,\"max_cash\":320040000,\"today_played_time\":65,\"task\":([395:([\"state\":100,\"sub_name\":\"tianjie7\",\"upgrade_type\":0,\"level\":164,\"next_task_name\":\"tianjie8\",\"finished_time\":([\"tianjie7\":\"2019-06-05-21:45:07\",]),]),339:([\"state\":\"1\",\"upgrade_type\":0,\"finish\":([]),]),294:([\"owner_gid\":\"0000000005F5E150\",\"rt\":1559738965,\"log\":\"当前提示：与四大门派的#P西方教主#P、#P太上老君#P、#P通天教主#P、#P准提道人#P交谈，并选择其一做为第二门派。\",\"dbase\":([\"state\":\"学习新法术\",\"init_time\":([\"1585282773\":\"" + sm.getName() + "\",]),]),]),754:([\"state\":\"0\",\"et\":1595606399,]),243:([\"state\":\"b\",\"ver\":1,]),253:([\"change_look\":187,\"start_time\":1559738110,\"duration\":10800,]),750:([\"state\":\"0\",\"upgrade_type\":0,]),729:([\"state\":\"S2\",\"alias\":38,\"upgrade_type\":0,]),663:([\"state\":\"0\",\"upgrade_type\":0,]),644:([\"state\":\"0\",\"upgrade_type\":0,]),601:([\"state\":\"0\",\"upgrade_type\":0,]),1091:([\"et\":1585555199,\"total\":480,]),1074:([\"state\":\"0\",]),1076:([\"state\":\"0\",]),1079:([\"state\":\"0\",\"upgrade_type\":0,]),1087:([\"ti\":1585282774,\"exp\":0,\"tao\":761282,]),1061:([\"et\":1585555199,\"total\":120,]),21:([\"state\":0,\"times\":0,\"new_ver\":1,\"max_times\":1,\"bonus_time\":1585209599,\"upgrade_type\":0,]),]),\"speed\":444,\"init_basic_info\":1,\"name\":\"" + sm.getName() + "\",\"accurate\":0,\"region\":([\"flag\":1,\"province\":1,\"city\":1,]),\"double_cash\":2,\"title_basic\":\"第一代弟子\",\"protect_bonus\":([\"ti\":1585282773,\"hour\":0,]),\"double_balance\":2,\"resist_fire\":0,\"resist_frozen\":0,\"max_life\":53209,\"extra_mana\":17513642,\"max_mana\":86799,\"nice\":9,\"tao\":" + sm.getTao() * 360 + ",\"soul_cob_rate\":0,\"portrait\":7002,\"account\":\"" + sm.getAccount() + "\",\"question\":([\"answer_times\":0,]),\"last_privilege\":1000,\"gender\":" + sm.getGender() + ",\"last_logout_time\":1585282838,\"polar_water\":0,\"gold_coin\":" + sm.getGoldCoin() + ",\"polar_earth\":0,\"exp_to_next_level\":2100000000,\"ip_region\":([\"province\":\"北京市\",\"city\":\"东城区\",]),\"character\":([\"kindness\":0,\"harmony\":0,\"desc\":\"普普通通\",\"carefulness\":0,\"courage\":0,]),\"restriction\":([\"login_time\":1585282773,]),\"cur_pk\":0,\"resist_forgotten\":0,\"total_pk\":0,\"str\":165,\"cur_ver\":17,\"wiz\":1201,\"stamina\":175,\"wudao\":([\"cur_stage\":1,\"cur_stage_info\":([\"3\":([\"rep\":2,\"max_rep\":203,]),]),\"ver\":2,]),\"icon\":7002,\"polar_fire\":0,\"def\":1208,\"last_level_up_time\":1559742314,\"max_durability\":100,\"tao_ex\":0,\"appellation_ids\":([7:33554432,1:16777216,3:134219776,]),\"resist_melt\":0,\"total_played_time\":19263,\"upgrade_magic\":130,\"newbie\":1,\"top_data\":([\"speed\":444,\"def\":1208,\"phy_power\":865,\"mag_power\":6045,]),\"dodge\":0,\"first_login_ip\":\"171.221.225.8\",\"resist_sleep\":0,\"max_assign_polar\":47,\"title_effect\":\"\",\"title\":\"\",\"family\":\"" + polar.getName() + "\",\"polar_point\":229,\"resist_water\":0,\"resisit_metal\":0,\"upgrade_immortal\":130,\"wudou\":([\"last_cost_time\":1585209600,]),\"max_account_lv\":260,\"attrib_already\":([]),\"limit_per_day\":([\"ti\":1585282774,\"777\":1,\"783\":1,]),\"auth_protect_prompt\":1,\"parry\":0,\"login_times\":7,\"wood\":0,\"cash\":" + sm.getCash() + ",\"pot\":0,\"first_get_upgrade_time\":1559735283,\"resist_wood\":0,\"master\":\"" + polar.getMaster() + "\",\"con\":165,\"reputation\":0,\"level_up_time\":11828,\"version_prompt\":([\"1.60\":3,]),\"first_login_time\":1559729748,\"resist_poison\":0,\"dex\":" + sm.getLevel() + ",\"energy\":1500,\"easy_task\":([\"任务卷轴��炼丹\":1,\"任务卷轴��副本任务\":1,\"任务卷轴��降妖任务\":1,\"任务卷轴��仙人指路\":1,\"任务卷轴��天降大任\":1,\"任务卷轴��绑定\":1,\"任务卷轴��试道大会\":1,\"任务卷轴��切磋\":1,\"任务卷轴��妖魔道\":1,\"任务卷轴��加锁\":1,\"任务卷轴��日常任务\":1,\"任务卷轴��摆摊\":1,\"任务卷轴��结拜\":1,\"任务卷轴��钱庄\":1,\"任务卷轴��门派比武\":1,\"任务卷轴��押镖\":1,\"任务卷轴��拜师\":1,\"任务卷轴��答题\":1,\"任务卷轴��除暴任务\":1,\"任务卷轴��捉宠\":1,\"任务卷轴��结婚\":1,\"任务卷轴��帮派日常挑战\":1,\"任务卷轴��加入帮派\":1,\"任务卷轴��天罡地煞\":1,\"任务卷轴��仙魔录\":1,\"任务卷轴��宠物强化\":1,]),\"create_time\":1559729508,\"generate_time\":1585282838,\"last_login_ip\":\"192.168.8.225\",\"resist_metal\":0,\"phy_power\":865,\"anticheater_info\":([\"interval\":19263,\"total_steps\":1458,\"last_move_time\":1585282838,]),\"recover_energy_time\":1585282736,\"balance\":0,\"fire\":0,\"medicine\":([]),\"title_type_effect\":\"无显示\",\"resist_lock\":0,\"age\":0,\"mana\":86799,\"water\":0,\"feedback\":([]),\"trigger_guide\":([\"203\":1,]),\"attrib_point\":0,\"level\":" + sm.getLevel() + ",\"unique_data\":([5:32792,2:8388673,0:536870912,3:5177344,1:-190117886,]),\"appellation\":([\"family\":\"" + polar.getName() + "第一代弟子\",\"tianjie\":\"七劫散仙\",\"upgrade\":\"飞升\",\"无显示\":\"\",]),\"act_stat\":([\"ti\":1585282774,\"mons\":({0,0,0,}),\"ytd\":([]),\"td\":([]),]),\"last_login_mac\":\"000038d547b4a411\",\"resist_earth\":0,\"vip_info\":([\"score\":3784,\"duptime\":1583398854,\"mdays\":1,\"level\":0,\"next_vip_score\":5000,\"state\":0,\"uptime\":1583049600,\"lmdays\":7,]),\"max_stamina\":359,\"settings\":([\"convert\":1,]),\"die_stat\":([]),\"equip_page\":0,\"pker\":0,\"task_round\":([\"962\":0,]),\"polar_already\":([]),\"metal\":0,\"medicine_used\":([]),\"max_balance\":944040000,\"salary\":([\"online_time\":([\"1585555200\":65,]),]),\"upgrade\":([\"max_polar_extra\":17,\"bonus\":1,\"max_level\":165,\"attrib_point\":26,\"attrib_total\":26,\"type\":" + sm.getType() + ",\"ti_modify\":1335590733,\"create_time\":1559735283,\"state\":0,\"con\":0,\"ever_lv\":134,\"total\":35,\"cur_ver\":6,]),\"resisit_fire\":0,\"resist_repress\":0,\"previous_login_ip\":\"192.168.8.225\",\"max_energy\":1500,\"resisit_earth\":0,\"resist_cage\":0,\"mag_power\":6045,\"xieling\":1,\"limit_trade_coin\":0,\"title_type\":\"tianjie\",\"resist_confusion\":0,\"resisit_water\":0,\"gid\":\"" + sm.getChainParam() + "\",\"user_converted\":7,\"silver_coin\":" + sm.getSilverCoin() + ",\"limit_per_month\":([\"ti\":1583391206,\"11\":1,]),\"achieve\":1631,\"pause_level_up\":0,\"have_coin_pwd\":0,\"first_login_mac\":\"0000e0d55ec94456\",\"total_score\":0,\"has_upgraded\":1,\"polar_metal\":0,\"extra_life\":6807022,\"logout_time\":1585282838,\"exp\":0,\"newbie_gift\":1,\"scroll\":([\"time\":1585282837,]),\"insider_time\":53938,]),\"discover_world\":([\"1\":([]),]),\"settings\":({({\"USER\",({2,308,123,}),}),({\"\",({80,0,0,}),({76,0,1,}),({37,0,0,}),({78,0,0,}),({72,0,0,}),({64,0,0,}),({88,0,0,}),({50,0,0,}),({57,0,0,}),({41,0,0,}),({65,0,0,}),({33,0,1,}),({74,0,0,}),({31,0,0,}),({52,0,0,}),({66,0,0,}),}),}),\"skills_map\":([]),\"skills\":([" + polar.getHome() + lifeSkill + yinlingfan + polar.getSkill() + polar.getWufa() + polar.getFeisheng() + "]),])";
			}
			Data entity = new Data();
			entity.setBranch("");
			entity.setContent(content);
			entity.setPath(Path.user.name());
			entity.setName(sm.getChainParam());
			entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
			entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
			boolean save = this.dataService.save(entity);
			if(save) {
				save = ((RegisterChainHandler) this.handler).doHandler(sm);
			}
			return save;
		}

		/**
		 * 2
		 */
		@Override
		@Transactional
		public boolean doHandler(MoveModel sm) {
			Data entity = this.dataService.getDataByMultiId(Path.user.name(), sm.getChainParam(), "");
			String content = CharsetUtil.convert(entity.getContent(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
			entity.setContent(content.replace("account\":\"" + sm.getSource(), "account\":\"" + sm.getTarget()));
			entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
			boolean update = this.dataService.updateByMultiId(entity);
			if(update) {
				update = ((MoveRoleChainHandler)this.handler).doHandler(sm);
			}
			return update;
		}
		
		@Override
		public void setNextHandler(StandardChainHandler handler) {
			super.handler = handler;
		}
	}
	
	@Component
	public static class AchieveAndCarryChainHandler extends AbstractDataServiceChainHandler {

		@Override
		@Transactional
		public boolean doHandler(SpecialModel sm) {
			String achieve = "([90102:([5:294,4:1557680413,]),60413:([5:0,4:1557680404,]),60414:([5:0,4:1557680404,]),60415:([5:0,4:1557680404,]),70113:([4:1557596605,3:1,]),\"update_ti\":1557677100,10201:([1:1,]),70110:([4:1557596608,3:1,]),\"ver\":2,30169:([1:1,]),10703:([5:0,4:1557596587,]),10702:([5:0,4:1557596587,]),10701:([5:0,4:1557596587,]),30134:([1:1,]),20406:([1:1,]),20407:([5:3,4:1557608768,7:([\"ti\":3486,]),]),30122:([5:1,4:1557611645,]),30121:([1:1,]),51101:([5:5,4:1557677789,7:([\"ti\":216,]),]),51103:([5:0,4:1557596587,]),51102:([5:0,4:1557596587,]),30101:([1:1,]),30107:([1:1,]),\"total\":155,90401:([1:1,]),10505:([5:0,4:1557680404,]),10506:([5:0,4:1557680404,]),40206:([5:0,4:1557680404,7:([]),]),90336:([1:1,]),50403:([1:1,]),50910:([1:1,4:1557596688,]),50909:([1:1,]),90331:([1:1,]),90308:([5:47,4:1557675755,]),90307:([1:1,]),60102:([5:1,4:1557675613,]),60101:([1:1,]),90316:([5:1,4:1557606398,]),90315:([1:1,]),90313:([1:1,]),90314:([5:9,4:1557607101,]),90301:([1:1,]),40105:([5:0,4:1557680404,]),40106:([5:0,4:1557680404,]),\"traces\":({}),20113:([5:0,4:1557610503,]),20114:([5:0,4:1557610503,]),20110:([5:0,4:1557609711,]),20106:([5:0,4:1557610503,]),20102:([5:0,4:1557610503,]),20103:([5:0,4:1557610503,]),20104:([5:0,4:1557610503,]),20101:([1:1,]),90208:([5:1,4:1557605766,]),90215:([1:1,]),90217:([1:1,]),90216:([1:1,]),90218:([1:1,]),90207:([1:1,]),\"lastest\":({90307,10201,60101,}),80404:([5:0,4:1557680404,]),80405:([5:0,4:1557680404,]),80406:([5:0,4:1557680404,]),])";
			Data entity = new Data();
			entity.setContent(achieve);
			entity.setPath(Path.user.name());
			entity.setName(sm.getChainParam());
			entity.setBranch(Branch.achieve.name());
			entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
			entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getBranch() + entity.getContent()));
			boolean save = this.dataService.save(entity);
			if(save) {
				entity.setBranch(Branch.carry.name());
				entity.setContent("([\"carry\":([]),])");
				entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getBranch() + entity.getContent()));
				save = this.dataService.save(entity);
				if(save) {
					save = ((RegisterChainHandler) this.handler).doHandler(sm);
				}
			}
			return save;
		}

		@Override
		public void setNextHandler(StandardChainHandler handler) {
			super.handler = handler;
		}
		
	}
	
	@Component
	public static class ChildAndGuardChainHandler extends AbstractDataServiceChainHandler {

		@Override
		@Transactional
		public boolean doHandler(SpecialModel sm) {
			int intGid = Integer.parseInt(sm.getChainParam(), 16);
			//String guradId = "5D77E13A0002" + (String.format("%8s", HexUtil.toHex(intGid)).replace(" ", "0").toUpperCase());
			//String childId = "5D77E13A0002" + (String.format("%8s", HexUtil.toHex(intGid + 10101)).replace(" ", "0").toUpperCase());
			String guradId = FastDataConvert.toPath(DataFormatTemplate.FORMAT_8, intGid);
			String childId = FastDataConvert.toPath(DataFormatTemplate.FORMAT_8, intGid + 10101);
			String chidData = "";
			if(sm.getIsChild()) {
				chidData = "1:\"娃娃:1:0:0::" + childId + ":\",";
			}
			String guardData = "0:\"0:([\\\"attrib\\\":([106:2,107:12409,108:91954,104:2,76:5,72:5004,68:1302716,67:" + sm.getGuardIntimacy() + ",66:6171,64:0,75:34,71:20,69:6100,53:0,52:110,51:34277,49:495,48:236555,63:165,62:4,61:5004,60:0,58:0,57:0,56:0,55:2,47:0,44:165,37:34277,36:2171,35:5,33::" + guradId + ":,22:30868,21:59039,16:18921,31:190,5:420,3:360,2:236555,]),])\",";
			String content_patch = "([\"pets\":([]),\"guards\":([" + guardData + "]),\"friends\":([\"5\":([]),\"4\":([]),\"3\":([]),\"2\":([]),\"1\":([]),\"6\":([]),]),\"children\":([" + chidData + "]),\"practice_children\":([]),\"practice_pets\":([]),])";
			Data entity = new Data();
			entity.setContent(content_patch);
			entity.setPath(Path.user.name());
			entity.setName(sm.getChainParam());
			entity.setBranch(Branch.patch.name());
			entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
			entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getBranch() + entity.getContent()));
			boolean save = this.dataService.save(entity);
			if(save) {
				String capacity = String.valueOf((sm.getLevel() * 4 - 4));
				String birthday = String.valueOf(System.currentTimeMillis());
				String content_child = "([\"carry\":([]),\"attrib\":([\"food\":10000,\"mood\":10000,\"refresh_stamina_time\":1568464012,\"gender\":2,\"status\":0,\"life\":23962,\"attack_speed\":4,\"combat_mode\":7,\"max_stamina\":200,\"pot\":0,\"max_mood\":10000,\"phy_effect\":100,\"exp_to_next_level\":0,\"level_up_time\":1560364099,\"str\":" + sm.getLevel() + ",\"max_food\":10000,\"stamina\":200,\"dex\":" + sm.getLevel() + ",\"def\":2604,\"icon\":7015,\"max_limit_level\":165,\"lock_exp\":0,\"repair_ver\":6,\"intimacy\":" + sm.getChildIntimacy() + ",\"phy_power\":7326,\"capacity\":" + capacity + ",\"mag_power\":7328,\"train_process\":0,\"str_effect\":100,\"birthday\":" + birthday + ",\"dodge\":11,\"iid\"::" + childId + ":,\"rank\":6,\"physique\":" + sm.getLevel() + ",\"wisdom\":" + sm.getLevel() + ",\"wit_effect\":100,\"mana\":1000,\"use_skill\":([]),\"name\":\"娃娃\",\"parents\":({\"" + sm.getChainParam() + "\",}),\"level\":" + sm.getLevel() + ",\"max_life\":23962,\"stamina_effect\":100,\"dex_effect\":100,\"exp\":0,\"max_mana\":0,\"health\":0,\"portrait\":7015,]),\"skills\":([]),])";
				entity.setBranch("");
				entity.setPath(Path.child.name());
				entity.setName(":" + childId + ":");
				entity.setContent(content_child);
				entity.setTime(DatePattern.PURE_DATETIME_FORMAT.format(new Date()));
				entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
				save = this.dataService.save(entity);
			}
			return save;
		}

		@Override
		public void setNextHandler(StandardChainHandler handler) {
			super.handler = handler;
		}
		
	}

	@Override
	@Transactional
	public boolean setContentCash(String gid, int cash) {
		return setContent(gid, "", cash, "\"cash\"");
	}
	
	@Override
	@Transactional
	public boolean setContentVcash(String gid, int vcash) {
		return setContent(gid, "", vcash, "\"voucher\"");
	}

	@Override
	public List<Long> getGidList(String account) {
		Data entity = this.baseMapper.selectByMultiId(Path.login.name(), account, "");
		if(entity == null) {
			return null;
		}
		String content = entity.getContent();
        String gids = content.substring(content.indexOf("chars") + 9);
        gids = gids.substring(0, gids.indexOf(",}),")).replace("\"", "");
		return Arrays.stream(gids.split(COMMA)).map(gid -> Long.valueOf(gid, 16)).collect(Collectors.toList());
	}

	@Override
	public Data getDataEntity(String account) {
		return getDataByMultiId(Path.runtime.name(), account, "");
	}
	
	@Override
	@Transactional
	public boolean updateByMultiId(Data entity) {
		return this.baseMapper.updateByMultiId(entity) == 1;
	}
	
	@Override
	@Transactional
	public boolean remveByMultiId(Data entity) {
		return this.baseMapper.removeByMultiId(entity) == 1;
	}
	
	@Override
	public Data getDataByMultiId(String path, String name, String branch) {
		return this.baseMapper.selectByMultiId(path, name, branch);
	}

	@Override
	@Transactional
	public boolean setContentTao(String gid, int tao) {
		return setContent(gid, "", tao * 360, "\"tao\"");
	}
	
	private boolean setContent(String gid, String branch, int num, String type) {
		StringBuilder builder = new StringBuilder();
		Data entity = getDataByMultiId(Path.user.name(), gid, branch);
		String content = CharsetUtil.convert(entity.getContent(), CharsetUtil.ISO_8859_1, CharsetUtil.GBK);
		int indexOf = content.lastIndexOf(type);
		if(indexOf == -1) { //普通账号注册代金券字段没有需要特殊处理
			int lastIndexOf = content.lastIndexOf(COMMA);
			String prefix = content.substring(0, lastIndexOf);
			String suffix = content.substring(lastIndexOf);
			builder.append(prefix).append(type).append(DELIMITER).append(num).append(suffix);
		}else {
			String prefix = content.substring(0, indexOf);//前缀
			String subContent = content.substring(indexOf);
			int idx =  subContent.indexOf(DELIMITER) + 1;
			int last = subContent.indexOf(COMMA);
			String suffix = subContent.substring(last); //后缀
			
			String value = subContent.substring(idx, last);
			int add = Integer.valueOf(value) + num;
			if(add > Sys.GAME_NUMBER) {
				add = Sys.GAME_NUMBER;
			}
			builder.append(prefix).append(type).append(DELIMITER).append(add).append(suffix);
		}
		entity.setContent(builder.toString());
		entity.setChecksum(ChecksumUtil.checksum(entity.getPath() + entity.getName() + entity.getContent()));
		return this.baseMapper.updateByMultiId(entity) == 1;
	}

	@Override
	public List<Data> getDataList(String path) {
		return this.baseMapper.selectByPath(path);
	}

}
