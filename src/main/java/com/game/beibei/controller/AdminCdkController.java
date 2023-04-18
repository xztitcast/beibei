package com.game.beibei.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.game.beibei.common.P;
import com.game.beibei.common.R;
import com.game.beibei.entity.Cdk;
import com.game.beibei.model.CdkModel;
import com.game.beibei.service.CdkService;

import io.netty.util.internal.StringUtil;

@RestController()
@RequestMapping("/admin/cdk")
public class AdminCdkController {
	
	@Autowired
	private CdkService ckdService;

	@GetMapping("/list")
	public R list(@RequestParam(value="pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
			@RequestParam(required = false) Integer type,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer status) {
		P<Cdk> p = ckdService.getCdkList(pageNum, pageSize, type, name, status);
		return R.of(p);
	}
	
	@PostMapping("/save")
	public R save(@Valid @RequestBody CdkModel cm) {
		System.out.println(cm);
		String content = cm.getContent();
		if(StringUtils.isBlank(content)) {
			return R.error("必选项不能为空!");
		}
		String[] split = content.split("\\n");
		Arrays.stream(split).forEach(number -> {
			Cdk entity = new Cdk();
			entity.setStatus(0);
			entity.setType(cm.getType());
			entity.setName(cm.getName());
			entity.setNumber(number);
			ckdService.save(entity);
		});
		return R.ok();
	}
	
	@PostMapping("/delete")
	public R delete(@RequestBody Long[] ids) {
		System.out.println(Arrays.toString(ids));
		return R.ok();
	}
}
