package com.biz.bbs.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.persistence.BBsDao;

import lombok.RequiredArgsConstructor;

/**
 * 게시판 CRUD 기능을 담당할 서비스
 * 
 * @since 2020-05-07
 * @author jminban
 *
 */
@RequiredArgsConstructor
@Service
public class BBsService {

	private final BBsDao bDao;
	
	// 게시글 모두 가져오기
	public List<BBsVO> selectAll() {
		// TODO selectAll
		return bDao.selectAll();
	}

	
}
