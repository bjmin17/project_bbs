package com.biz.bbs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.domain.PageVO;
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
			Model model) {
		
		long totalCount = 0;
		totalCount = bService.totalCount();
		
		PageVO pageVO = pService.getPagination(totalCount,currentPageNo);
		log.debug("페이지 VO : " + pageVO.toString());
		
//		List<BBsVO> bbsList = bService.selectAllPagination(pageVO);
		List<BBsVO> bbsList = bService.selectAll();
		
		
		model.addAttribute("BBS_LIST", bbsList);
		model.addAttribute("pageVO",pageVO);
		
		
		return "bbs/bbs_main";
	}
	
	@RequestMapping(value="/insert",method=RequestMethod.GET)
	public String insert() {
		
		return "bbs/insert";
	}
	
	@RequestMapping(value = "/insert",method=RequestMethod.POST)
	public String insert(BBsVO bbsVO) {
		int ret = bService.insert(bbsVO);
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/detail",method=RequestMethod.GET)
	public String detail(@RequestParam("b_id") String b_id, Model model) {
		
		long c_b_id = Long.valueOf(b_id);
		this.commentList(c_b_id+"", model);
		
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
		
		return "redirect:/board";
	}
	
	@RequestMapping(value = "/delete",method=RequestMethod.GET)
	public String delete(@RequestParam("b_id") String b_id) {
		
		int ret = bService.delete(b_id);
		
		return "redirect:/board";
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
	
}

