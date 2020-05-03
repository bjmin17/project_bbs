package com.biz.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.bbs.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security 기반
 * 로그인 프로젝트
 * 메인 컨트롤러
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */
@RequestMapping(value="/user")
@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService uService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login() {
		return "auth/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idcheck",method=RequestMethod.GET)
	public String idcheck(String username) {
		boolean ret = uService.isExistsId(username);
		if(ret) {
			return "EXISTS";
		}
		return "NO";
	}
	
	
}
