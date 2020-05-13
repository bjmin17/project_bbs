package com.biz.bbs.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FileService {

	private final String filePath;
	
	public String file_up(MultipartFile mFile) {
		if(mFile == null) {
			return null;
		}	
		// 폴더 객체 생성
		File dir = new File(filePath);
		
		// 폴더가 없다면
		if(!dir.exists()) {
			
			// c:/developer/files
			// developer 폴더가 있고, files 폴더만 없을 때
			dir.mkdir();
			
			// developer 폴더도 없고, files 폴더를 찾을 수 없을 경우
			// 모든 경로를 생성
			dir.mkdirs();
		}
		
		String strUUID = UUID.randomUUID().toString();
		
		String originalName = mFile.getOriginalFilename();
		
		String upLoadFileName = strUUID + originalName;
		
		// 업로드할 파일 객체 생성
		File upLoadFile = new File(filePath,upLoadFileName);
		
		try {
			mFile.transferTo(upLoadFile);
			return upLoadFileName;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
		
	}
}
