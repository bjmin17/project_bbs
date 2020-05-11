package com.biz.bbs.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
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
	private final PageService pService;
	
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

	// allList 페이징 카운트
	public long searchAllListCount(String search) {
		return bDao.searchAllListCount(search);
	}
	// 제목 검색 페이징 카운트
	public long searchSubjectCount(String search) {
		return bDao.searchSubjectCount(search);
	}
	// 내용 검색 페이징 카운트
	public long searchTextCount(String search) {
		return bDao.searchTextCount(search);
	}
	
	
	public List<BBsVO> selectAllPagination(PageVO pageVO) {
		// TODO selectAllPagination
		return bDao.selectAllPagination(pageVO);
	}

	// 컨트롤러에서 시작한 검색 페이징
	public List<BBsVO> selectAllListPagination(PageVO pageVO, String search) {
		// TODO Auto-generated method stub
		
		return bDao.selectAllListPagination(pageVO, search);
	}
	// 컨트롤러에서 시작한 검색 페이징
	public List<BBsVO> selectTitle(PageVO pageVO, String search) {
		// TODO Auto-generated method stub
		return bDao.selectTitle(pageVO,search);
	}
	// 컨트롤러에서 시작한 검색 페이징
	public List<BBsVO> selectContent(PageVO pageVO, String search) {
		// TODO Auto-generated method stub
		return bDao.selectContent(pageVO, search);
	}
	
	// 추천
	public int recommend_up(String b_id, String loginUsername) {
		// TODO 추천 메서드 recommend_up
		
		BBsVO bbsVO = bDao.findById(b_id);
		bbsVO.setB_recommend(bbsVO.getB_recommend()+1);
		
		bDao.insertRecommend(b_id, loginUsername);
		int ret = bDao.update(bbsVO);
		
		return 0;
	}
}
