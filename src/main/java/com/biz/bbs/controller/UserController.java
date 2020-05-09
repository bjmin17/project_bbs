package com.biz.bbs.controller;

import java.security.Principal;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biz.bbs.domain.UserDetailsVO;
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
	
	@RequestMapping(value = "/join",method = RequestMethod.GET)
	public String join() {
		return "auth/join";
	}
	
//	@ResponseBody
	@RequestMapping(value = "/join",method=RequestMethod.POST,
						produces = "text/html;charset=UTF-8")
	public String join(String username, String password) {
		
		log.debug("아이디 {}, 비번 {}", username, password);
		uService.insert(username, password);
		
//		return String.format("아이디 : <b>%s</b>, 비밀번호 : <b>%s</b>", username, password);
		return "redirect:/";
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
	
	/**
	 * 마이페이지에서 입력한 비밀번호가
	 * 로그인 한 비밀번호와 일치하는지 검사
	 * 
	 * @since 2020-05-05
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/password",method = RequestMethod.POST)
	public String passwordCheck(String password) {
		
		boolean ret = uService.check_password(password);
		
		if(ret) return "PASS_OK";
		
		return "PASS_FAIL";
	}
	
	@RequestMapping(value="/mypage",method=RequestMethod.GET)
	public String mypage(Principal principal, Model model) {
		// 사용자정보 뽑아오는 방법
		// principal을 가져와서 토큰으로 가져온 후, getPrincipal로 정보 가져오기
		UsernamePasswordAuthenticationToken upa = (UsernamePasswordAuthenticationToken) principal;
		
		UserDetailsVO userVO = (UserDetailsVO) upa.getPrincipal();
		
		userVO.setAuthorities(upa.getAuthorities());
		
		model.addAttribute("userVO",userVO);
		return "auth/mypage";
	}
	
	@RequestMapping(value="/mypage",method=RequestMethod.POST)
	public String mypage(UserDetailsVO userVO, String[] auth, Model model) {
		
		/*
		 * Security Session 정보가 저장된 메모리에 직접 접근하여
		 * session user 정보를 수집하는 방법은
		 * UsernamePasswordAuthenticationToken upa = (UsernamePasswordAuthenticationToken) principal;
		
			UserDetailsVO oldUserVO = (UserDetailsVO) upa.getPrincipal();
			oldUserVO.setEmail(userVO.getEmail());
		 * 코드는 쉬워지나 보안에 치명적인 문제를 일으킬 수 있다.
		 * 
		 */
		int ret = uService.update(userVO, auth);
		
		return "redirect:/user/mypage";
	}
	
	
}
