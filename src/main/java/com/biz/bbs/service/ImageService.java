package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.ImageFilesVO;
import com.biz.bbs.persistence.BBsDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final BBsDao bDao;
//	private final ImageFileDao iDao;
	
	public int insert(BBsVO bbsVO) {
		
		bbsVO = this.updateImageFiles(bbsVO);
		
		int ret = bDao.insert(bbsVO);
		
		
		
		return ret;
	}
	
	public BBsVO updateImageFiles(BBsVO bbsVO) {
		List<ImageFilesVO> imgFiles = bbsVO.getImg_up_files();
		
		if(imgFiles != null && imgFiles.size() > 0) {
			bbsVO.setB_file(imgFiles.get(0).getImg_file_upload_name());
			for(ImageFilesVO ifVO : imgFiles) {
				ifVO.setImg_file_origin_name(ifVO.getImg_file_upload_name().substring(36));
			}
		}
		return bbsVO;
	}
	
}
