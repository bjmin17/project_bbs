package com.biz.bbs.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.service.CommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시글에 댓글 생성
 * 
 * @since 2020-05-08
 * @author User
 *
 */
@RequiredArgsConstructor
@RequestMapping(value = "/comment")
@Slf4j
@Controller
public class CommentController {

	private final CommentService cmtService;
	
	@RequestMapping(value = "/list",method=RequestMethod.GET)
	public String list(@RequestParam("b_id") String b_id, Model model) {
		
		long c_b_id = 0;
		try {
			c_b_id = Long.valueOf(b_id);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		List<CommentVO> cmtList = cmtService.findByBId(c_b_id);
		model.addAttribute("COMMENT",cmtList);
		
		return "comment/comment_list";
	}
	
	/*
	 * 코멘트 입력 값을 매개변수로 받아서
	 * db insert를 수행할 메서드
	 */
	@RequestMapping(value="/insert",method=RequestMethod.POST)
	public String insert(CommentVO commentVO, Model model) {
		
		log.debug("컨트롤러 댓글 : " + commentVO);
		cmtService.insert(commentVO);
		
		/*
		 * model에 attribute로 해당 값을 add 수행한 후
		 * 	redirect를 수행하면
		 * 	쿼리 문자열을 자동으로 만들어서 전달한다.
		 */
		
		model.addAttribute("b_id",commentVO.getC_b_id());
		
		return "redirect:/comment/list";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(String c_id, String b_id, Model model) {
		int ret = cmtService.delete(Long.valueOf(c_id));
		
		model.addAttribute("b_id",b_id);
		return "redirect:/comment/list";
	}
	
	
	
}
