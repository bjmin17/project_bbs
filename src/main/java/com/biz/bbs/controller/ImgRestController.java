package com.biz.bbs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.biz.bbs.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@RequiredArgsConstructor
@RequestMapping(value = "/rest")
@RestController
public class ImgRestController {

	@Autowired
	FileService fService;
	
	@RequestMapping(value="/file_up", method=RequestMethod.POST, produces = "text/html;charset=UTF-8")
	public String file_up(@RequestParam("file") MultipartFile upFile) {
		
		log.debug("로그"+upFile.toString());
		String upLoadFileName = fService.file_up(upFile);
		
		log.debug("레스트 컨트롤러 : " + upLoadFileName);
		
		if(upLoadFileName == null) return "FAIL";
		else return upLoadFileName;
		
	}
}
