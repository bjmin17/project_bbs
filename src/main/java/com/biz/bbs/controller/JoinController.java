package com.biz.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Spring Security 기반
 * 회원가입 프로젝트
 * 메인 컨트롤러
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */
@RequestMapping(value = "/join")
@Slf4j
@RequiredArgsConstructor
@Controller
public class JoinController {

	private final UserService uService;
	
	@RequestMapping(value="",method = RequestMethod.GET)
	public String join(@ModelAttribute("userVO") UserDetailsVO userVO, Model model) {
		return "auth/join";
	}
	
	@ResponseBody
	@RequestMapping(value="/join_next",method = RequestMethod.POST
			,produces = "text/html;charset=UTF-8")
	public String join_next(@ModelAttribute("userVO") UserDetailsVO userVO, Model model) {
//		return "auth/join";
//		return "redirect:/";
		log.debug("아이디 {}, 비번{}", userVO.getUsername(), userVO.getPassword());
		
		int ret = uService.insert(userVO.getUsername(), userVO.getPassword());
		return String.format("아이디 : <b>%s</b>, 비번 : <b>%S</b>", userVO.getUsername(), userVO.getPassword());
	}
	
	public String join_next() {
		return "join/join_email";
	}
	
}
