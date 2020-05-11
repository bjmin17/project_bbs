package com.biz.bbs.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.domain.PageVO;
import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.service.BBsService;
import com.biz.bbs.service.CommentService;
import com.biz.bbs.service.PageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 컨트롤러
 * 게시판 CRUD 수행
 * 
 * @since 2020-05-07
 * @author jminban
 *
 */
@RequiredArgsConstructor
@RequestMapping(value="/board")
@Controller
@Slf4j
public class BBsController {

	private final BBsService bService;
	private final PageService pService;
	private final CommentService cService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String boardList(
			@RequestParam(value = "currentPageNo",required = false, 
				defaultValue = "1") int currentPageNo,
			String kategorie, String search,
			Model model) {
		
		
		List<BBsVO> bbsList1 = new ArrayList<BBsVO>();
		PageVO pageVO = new PageVO();
		long totalCount = 0;
		totalCount = bService.totalCount();
		
		log.debug("컨트롤러 카테고리 : "+kategorie);
		log.debug("컨트롤러 검색어 : " +search);
		
		if(kategorie.equalsIgnoreCase("allList")) {
			// 전체(allList)일 때 작동할거 만들어주기
			totalCount = bService.searchAllListCount(search);
			log.debug("토탈카운트 : " + totalCount);
			pageVO = pService.getPagination(totalCount,currentPageNo);
			bbsList1 = bService.selectAllListPagination(pageVO, search);
			
		} else if(kategorie.equalsIgnoreCase("title")) {
			// 제목으로만 검색했을 때
			totalCount = bService.searchSubjectCount(search);
			pageVO = pService.getPagination(totalCount,1);
			
			bbsList1 = bService.selectTitle(pageVO, search);
//			return bbsSearchList;
		} else if(kategorie.equalsIgnoreCase("content")) {
			// 내용으로 검색했을 때
			totalCount = bService.searchAllListCount(search);
			pageVO = pService.getPagination(totalCount,1);
			
			bbsList1 = bService.selectContent(pageVO, search);
//			return bbsSearchList;
		} else if(search.trim() == "" || search.isEmpty()) {
			
			bbsList1 = bService.selectAllPagination(pageVO);
//			return bbsSearchList;
		} else {
			bbsList1 = bService.selectAllPagination(pageVO);
		}
//		PageVO pageVO = pService.getPagination(totalCount,currentPageNo);
		log.debug("페이지 VO : " + pageVO.toString());
		
		// 세이브포인트
//		List<BBsVO> bbsList1 = bService.selectSearchPagination(pageVO, kategorie, search, currentPageNo);
		log.debug("리스트 개수 : "+bbsList1.size());
//		pageVO = pService.getPagination(bbsList1.size(), currentPageNo);
//		log.debug("검색 후 페이징 : " + bbsList1.toString());
		model.addAttribute("BBS_LIST", bbsList1);
		
//		List<BBsVO> bbsList = bService.selectAllPagination(pageVO);
//		model.addAttribute("BBS_LIST", bbsList);
//		List<BBsVO> bbsList = bService.selectAll();
		
		
		// 검색을 위해 값 넘겨주기
		model.addAttribute("kategorie", kategorie);
		model.addAttribute("search",search);
		
		model.addAttribute("pageVO",pageVO);
		
		
		return "bbs/bbs_main";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public String insert() {
		return "bbs/insert";
	}
	
	@RequestMapping(value = "/insert",method=RequestMethod.POST)
	public String insert(Principal principal, BBsVO bbsVO) {
		
		UserDetailsVO userVO = this.loginUserInfo(principal);
		
		log.debug("유저VO : " + userVO.toString());
		
		String loginUsername = userVO.getUsername();
		
		int ret = bService.insert(bbsVO,loginUsername);
		return "redirect:/board?currentPageNo=&search=&kategorie=";
	}
	
	@RequestMapping(value = "/detail",method=RequestMethod.GET)
	public String detail(Principal principal, @RequestParam("b_id") String b_id, Model model) {
		
//		log.debug("로그 principal 값 : " + principal.toString());
		UserDetailsVO userVO = this.loginUserInfo(principal);
		
		String loginUsername = userVO.getUsername();
		model.addAttribute("loginUsername",loginUsername);		
		
		long c_b_id = Long.valueOf(b_id);
		this.commentList(c_b_id + "", model);
		
		BBsVO bbsVO = bService.findById(b_id);
		
		model.addAttribute("bbsVO",bbsVO);
		
		return "bbs/view";
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.GET)
	public String update(@RequestParam("b_id") String b_id, Model model) {
		
		BBsVO bbsVO = bService.findById(b_id);
		
		model.addAttribute("bbsVO", bbsVO);
		
		return "bbs/insert";
	}
	
	@RequestMapping(value = "/update",method=RequestMethod.POST)
	public String update(BBsVO bbsVO) {
		
		int ret = bService.update(bbsVO);
		
		return "redirect:/board?currentPageNo=&search=&kategorie=";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	public String delete(Principal principal, @RequestParam("b_id") String b_id, Model model) {
		
		log.debug("컨트롤러 delete id 값 : "+ b_id);
		
		UserDetailsVO userVO = this.loginUserInfo(principal);
		String loginUsername = userVO.getUsername();
		
		int ret = bService.delete(b_id,userVO);
		
//		model.addAttribute("b_id",b_id);
		
		if(ret > 0) {
			return "redirect:/board?currentPageNo=&search=&kategorie=";
		} else {
//			return "redirect:/board/detail";
			return null;
		}
		
	}
	
	/*
	 * 게시판의 id값을 받아서
	 * 댓글 리스트를 보여주는 메서드
	 */
	private List<CommentVO> commentList(String b_id, Model model){

		List<CommentVO> commentList = cService.findByBId(Long.valueOf(b_id));
		model.addAttribute("COMMENT", commentList);
		
		return commentList;
	}

	/*
	 * 로그인 중인 유저 정보 얻기
	 */
	private UserDetailsVO loginUserInfo(Principal principal) {
		// 사용자정보 뽑아오는 방법
		// principal을 가져와서 토큰으로 가져온 후, getPrincipal로 정보 가져오기
		
		log.debug("비로그인 시 principal : " + principal.toString());
		UsernamePasswordAuthenticationToken upa = (UsernamePasswordAuthenticationToken) principal;
		
		
		UserDetailsVO userVO = (UserDetailsVO) upa.getPrincipal();
		
		return userVO;
	}
}

