package com.game.beibei.controller;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.game.beibei.common.Constant.Router;
import com.game.beibei.common.Constant;
import com.game.beibei.common.R;
import com.game.beibei.model.LoginModel;

import cn.hutool.http.server.HttpServerRequest;
import cn.hutool.http.server.HttpServerResponse;

@Validated
@Controller
public class MainController {
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@ResponseBody
	@PostMapping("/login")
	public R login(@RequestBody LoginModel lm) {
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(token, lm.getUsername(), 12, TimeUnit.HOURS);
		return R.of(token);
	}
	
	@RequestMapping(path = {"/", "/qingsi"})
	public String qingsi() {
		return Router.LOGIN;
	}
	
	@GetMapping("/index")
	public void index(HttpServletResponse response) throws IOException {
		response.sendRedirect(Router.RIGSTER);
	}
	
	@GetMapping("/home")
	public String home() {
		return Router.HOME;
	}
	
	@GetMapping("/main")
	public String main(@CookieValue("token") String token) {
		if(StringUtils.isBlank(token) || token.equalsIgnoreCase(Constant.Sys.UNDEFINED)) {
			return Router.LOGIN;
		}
		return Router.MAIN;
	}
	
	@GetMapping("/{prefix}/{html}")
	public String router(@PathVariable String prefix, @PathVariable String html, @CookieValue("token") String token) {
		
		return prefix.concat("/").concat(html);
	}
	
}
