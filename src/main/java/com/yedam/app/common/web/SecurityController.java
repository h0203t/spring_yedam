package com.yedam.app.common.web;

import com.yedam.app.security.service.LoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SecurityController {

	@GetMapping("all")
	public void all() {}
	
	@GetMapping("user")
	public void user(@AuthenticationPrincipal UserDetails userDetails) {
		LoginVO loginVO = (LoginVO) userDetails;
		log.error(loginVO.getUserVO().toString());

	}
	
	@GetMapping("admin")
	public void admin() {

	}
}
