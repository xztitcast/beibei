package com.game.beibei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.game.beibei.entity.Black;
import com.game.beibei.entity.adball.Account;
import com.game.beibei.mapper.BlackMapper;
import com.game.beibei.service.AreaService;
import com.game.beibei.service.BlackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("blackService")
public class BlackServiceImpl extends ServiceImpl<BlackMapper, Black> implements BlackService {
	
	@Autowired
	private AreaService areaService;

	@Override
	@Transactional
	public void save(Account a) {
		Black entity = new Black();
		entity.setAccount(a.getAccount());
		entity.setIp(a.getLastLoginIp());
		entity.setMac(a.getLastLoginId());
		boolean save = this.save(entity);
		/*if(save) {
			String cmd = null;
			String reloadCmd = null;
			Area area = areaService.getById(1);
			if(area.getSystem() == 6) {
				cmd = "iptables -I INPUT -s " + a.getLastLoginIp() + " -j DROP";
			}else {
				cmd = "firewall-cmd --permanent --add-rich-rule='rule family=ipv4 source address=\"123.56.0.0/16\" drop'";
				reloadCmd = "firewall-cmd --reload";
			}
			try {
				Runtime.getRuntime().exec(cmd);
				if(reloadCmd != null) {
					Runtime.getRuntime().exec(reloadCmd);
				}
			} catch (Exception e) {
				log.error("防火墙命令执行失败", e);
			}
			
		}*/
	}

}
