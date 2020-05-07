package com.biz.bbs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

	public int insert(BBsVO bbsVO) {
		// TODO insert
		
		LocalDate ld = LocalDate.now();  // 날짜 구하기 2020-05-07
		bbsVO.setB_date(ld.toString());
		
		LocalTime lt = LocalTime.now();  // 시간 구하기 23:23:31.071
		String slt = lt.toString();
		String sublt = slt.substring(0,8);
		bbsVO.setB_time(sublt);

		bbsVO.setB_writer("jminban");
		
		return bDao.insert(bbsVO);
	}

	
}
