package com.biz.bbs.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.service.UserService;

import lombok.RequiredArgsConstructor;

/**
 * 관리자페이지
 * 회원 리스트 보기
 * 회원정보 상세보기
 * 
 * @since 2020-05-06
 * @author User
 *
 */
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

	private final UserService uService;
	
	// 관리자 메인페이지
	@RequestMapping(value = "",method = RequestMethod.GET)
	public String admin(Principal principal) {
		return "admin/admin_main";
	}
	
	/**
	 * 회원 리스트보기
	 * 
	 * @since 2020-05-06
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_list",method=RequestMethod.GET)
	public String user_list(Model model) {
		List<UserDetailsVO> userList = uService.selectAll();
		model.addAttribute("userList", userList);
		
		return "admin/user_list";
	}
	
	/**
	 * 회원정보 상세보기
	 * 
	 * @since 2020-05-06
	 * @param username
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_detail_view/{username}",method=RequestMethod.GET)
	public String user_detail_view(@PathVariable("username") String username, Model model) {
		
		UserDetailsVO userVO = uService.findByUserName(username);
		model.addAttribute("userVO",userVO);
		
		return "admin/user_detail_view";
	}
	
	/**
	 * 관리자페이지에서 회원정보 수정하기
	 * 
	 * @since 2020-05-06
	 * @param username
	 * @param userVO
	 * @param auth
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user_detail_view",method=RequestMethod.POST)
	public String user_detail_view(String username, UserDetailsVO userVO, String[] auth, Model model) {
		
		int ret = uService.update(userVO, auth);
		return "redirect:/admin/user_detail_view/" + userVO.getUsername();
	}
	
}
