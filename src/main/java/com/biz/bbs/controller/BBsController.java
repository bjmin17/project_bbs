package com.biz.bbs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.service.BBsService;

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
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public String board(Model model) {
		
		List<BBsVO> bbsList = bService.selectAll();
		model.addAttribute("BBS_LIST", bbsList);
		
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
}
