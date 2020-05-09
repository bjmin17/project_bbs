package com.biz.bbs.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.PageVO;
import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.persistence.BBsDao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 게시판 CRUD 기능을 담당할 서비스
 * 
 * @since 2020-05-07
 * @author jminban
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class BBsService {

	private final BBsDao bDao;
	
	// 게시글 모두 가져오기
	public List<BBsVO> selectAll() {
		// TODO selectAll
		return bDao.selectAll();
	}

	public int insert(BBsVO bbsVO, String loginUsername) {
		// TODO insert

		bbsVO.setB_writer(loginUsername);
		
		LocalDate ld = LocalDate.now();  // 날짜 구하기 2020-05-07
		bbsVO.setB_date(ld.toString());
		
		LocalTime lt = LocalTime.now();  // 시간 구하기 23:23:31.071
		String slt = lt.toString();
		String sublt = slt.substring(0,8);
		bbsVO.setB_time(sublt);
		
		return bDao.insert(bbsVO);
	}

	public BBsVO findById(String b_id) {
		// TODO findById
		return bDao.findById(b_id);
	}

	public int delete(String b_id, UserDetailsVO userVO) {
		// TODO delete
		
		
		BBsVO bbsVO = bDao.findById(b_id);
		
		log.debug("서비스 delete 로그인 유저 : " + userVO.getUsername());
		log.debug("서비스 delete 작성자 이름 : " + bbsVO.getB_writer());
		
		if(userVO.getUsername().equalsIgnoreCase("admin")) {
			return bDao.delete(b_id);
		}
		else if(userVO.getUsername().equals(bbsVO.getB_writer())) {
			return bDao.delete(b_id);
		}
		else {
			return 0;
		}
		
		
	}

	public int update(BBsVO bbsVO) {
		// TODO update
		return bDao.update(bbsVO);
	}

	public long totalCount() {
		// TODO totalCount
		return bDao.totalCount();
	}

	public List<BBsVO> selectAllPagination(PageVO pageVO) {
		// TODO selectAllPagination
		return bDao.selectAllPagination(pageVO);
	}

	
}
