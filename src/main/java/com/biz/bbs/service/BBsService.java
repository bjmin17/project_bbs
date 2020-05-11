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
/*
	// 카테고리, 검색어를 받아서 해당하는 리스트를 페이징하며 보여주기
	public List<BBsVO> selectSearchPagination(PageVO pageVO, String kategorie, String search, int currentPageNo) {
		// TODO 검색어 받아서 리스트 페이징처리
		
		log.debug("서비스 카테고리 : "+kategorie);
		log.debug("서비스 검색어 : " +search);
		
		
		List<BBsVO> bbsSearchList ; 
		
		String kategorieTemp = "";
		kategorieTemp = kategorie;
		
		long totalCount = 0;
		
		if(kategorieTemp.equalsIgnoreCase("allList")) {
			// 전체(allList)일 때 작동할거 만들어주기
			totalCount = this.searchAllListCount(search);
			log.debug("토탈카운트 : " + totalCount);
			PageVO pageSearchVO = pService.getPagination(totalCount,currentPageNo);
			log.debug("서비스 PageVO. offset 값 : " + pageVO.getOffset());
//			log.debug("서버ㅣ스 페이지VO : "+pageVO.toString());
			bbsSearchList = bDao.selectAllSearch(pageSearchVO, search);
			log.debug("서비스 검색 후 페이징 : " + bbsSearchList);
//			return bbsSearchList;
		} else if(kategorieTemp.equalsIgnoreCase("title")) {
			// 제목으로만 검색했을 때
			totalCount = this.searchAllListCount(search);
			PageVO pageSearchVO = pService.getPagination(totalCount,1);
			
			bbsSearchList = bDao.selectTitle(pageSearchVO, search);
//			return bbsSearchList;
		} else if(kategorieTemp.equalsIgnoreCase("content")) {
			// 내용으로 검색했을 때
			totalCount = this.searchAllListCount(search);
			PageVO pageSearchVO = pService.getPagination(totalCount,1);
			
			bbsSearchList = bDao.selectContent(pageSearchVO, search);
//			return bbsSearchList;
		} else if(search.trim() == "" || search.isEmpty()) {
			
			bbsSearchList = this.selectAllPagination(pageVO);
//			return bbsSearchList;
		} else {
			bbsSearchList = this.selectAllPagination(pageVO);
//			return bbsSearchList;
		}
		return bbsSearchList;
		
	}
*/
	
}
