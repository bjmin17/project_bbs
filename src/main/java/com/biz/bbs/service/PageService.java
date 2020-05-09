package com.biz.bbs.service;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.PageVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PageService {

	/*
	 * pagination을 시작할 때
	 * 한 페이지 보여질 기본값을 설정
	 */
	int listPerPage = 10;
	int pageCount = 5;
	
	/*
	 * pagination의 기본값을 변경하고자 할 때
	 * 사용하는 setter method
	 */
	
	public void setListPerPage(int listPerPage) {
		this.listPerPage = listPerPage;
	}
	
	public void setPagecount(int pageCount) {
		this.pageCount = pageCount;
	}
	
	public PageVO getPagination(long totalCount, int currentPageNo) {
		
		if(totalCount < 1) {
			return null;
		}

		int finalPageNo = ((int)totalCount + (listPerPage - 1)) / listPerPage;
		
		if(currentPageNo > finalPageNo) currentPageNo = finalPageNo;
		if(currentPageNo < 1) currentPageNo = 1;
		
		int startPageNo = ((currentPageNo - 1) / pageCount) * pageCount + 1;
		
		int endPageNo = startPageNo + pageCount - 1;
		
		if(endPageNo > finalPageNo) endPageNo = finalPageNo;
		
		int prePageNo = 1;
		if(currentPageNo > 1) prePageNo = currentPageNo - 1;
		
		int nextPageNo = finalPageNo;
		if(currentPageNo < finalPageNo) nextPageNo = currentPageNo + 1;
		
		// DB에서 데이터 가져오기 값 설정
		// 1, 2, 3 페이지를 선택했을 때
		// 시작번호를 1, 11, 21로 설정하기
		int offset = (currentPageNo - 1) * listPerPage + 1;
		
		// 10, 20, 30으로 설정하기
		int limit = offset + listPerPage - 1;
		
		PageVO pageVO = PageVO.builder()
								.totalCount(totalCount)
								.pageCount(pageCount)
								
								.listPerPage(listPerPage)
								.offset(offset)
								.limit(limit)
								
								.firstPageNo(1)
								.finalPageNo(finalPageNo)
								
								.startPageNo(startPageNo)
								.endPageNo(endPageNo)
								
								.prePageNo(prePageNo)
								.nextPageNo(nextPageNo)
								
								.currentPageNo(currentPageNo)
								.build();
		
		return pageVO;
		
	}
}
