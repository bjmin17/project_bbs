package com.biz.bbs.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.BRecommendVO;
import com.biz.bbs.domain.CRecommendVO;
import com.biz.bbs.domain.CommentVO;
import com.biz.bbs.persistence.CommentDao;

import lombok.RequiredArgsConstructor;

/**
 * 댓글 CRUD
 * 
 * @since 2020-05-08
 * @author User
 *
 */
@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentDao cmtDao;
	
	public List<CommentVO> selectAll(){
		return null;
	}
	
	public CommentVO findById(String c_id) {
		CommentVO cmtVO = cmtDao.findById(c_id);
		return cmtVO;
	}
	
	/*
	 * 게시판 원글에 대한 코멘트 리스트 가져오기
	 */
	public List<CommentVO> findByBId(long c_b_id){
		
		List<CommentVO> cmtList = cmtDao.findByBId(c_b_id);
		List<CommentVO> retList = new ArrayList<CommentVO>();
		
		for(CommentVO vo : cmtList) {
			retList.addAll(this.findByBIdRepl(vo,0));
		}
		
		return retList;
	}
	
	/*
	 * 게시판 코멘트 답변 리스트 가져오기
	 */
	private List<CommentVO> findByBIdRepl(CommentVO cmtVO, int depth) {
		List<CommentVO> retList = new ArrayList<CommentVO>();
		
		if(depth > 0) {
			String c_subject = "&nbsp";
			for(int i = 0 ; i < depth ; i ++) {
				c_subject += "re: ";
			}
			c_subject += cmtVO.getC_subject();
			cmtVO.setC_subject(c_subject);
		}
		
		retList.add(cmtVO);
		
		List<CommentVO> tempList = cmtDao.findByPId(cmtVO.getC_id());
		if(tempList.size() < 1) return retList;
		
		for(CommentVO vo : tempList) {
			retList.addAll(this.findByBIdRepl(vo, depth+1));
		}
		
		return retList;
	}

	public List<CommentVO> findByPId(long c_p_id) {
		return cmtDao.findByPId(c_p_id);
	}
	
	public int insert(CommentVO commentVO) {
		
//		commentVO.setC_writer(loginUsername);
		
		if(commentVO.getC_id() > 0) {
			int ret = cmtDao.update(commentVO);
			return ret;
		} else {
			// 작성일자를 현재 날짜로
			LocalDateTime ldt = LocalDateTime.now();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			commentVO.setC_date_time(ldt.format(dtf).toString());
			return cmtDao.insert(commentVO);
		}
	}
	
	public int delete(long c_id) {
		return cmtDao.delete(c_id);
		
	}
	
	// 추천 업
	public int recommend_up(String c_id, String loginUsername) {
		// TODO Auto-generated method stub
		
		CommentVO cmtVO = cmtDao.findById(c_id);
		cmtVO.setC_recommend(cmtVO.getC_recommend()+1);

		int ret = 0;
		boolean booleanRet = this.recommend_id_check(loginUsername, c_id);
		if(booleanRet) {
			cmtDao.insertRecommend(c_id, loginUsername);
			
			ret = cmtDao.update(cmtVO);
		}
		
		return ret;
		
	}
	
	// 비추천 메서드
	public int recommend_down(String c_id, String loginUsername) {
		// TODO Auto-generated method stub
		
		CommentVO cmtVO = cmtDao.findById(c_id);
		cmtVO.setC_recommend(cmtVO.getC_recommend()-1);
		
		int ret = 0;
		boolean booleanRet = this.recommend_id_check(loginUsername, c_id);
		if(booleanRet) {
			cmtDao.insertRecommend(c_id, loginUsername);
			
			ret = cmtDao.update(cmtVO);
		}
		
		return ret;
	}
	
	// 추천 중복 조회 메서드
	private boolean recommend_id_check(String loginUsername, String c_id) {
		// TODO Auto-generated method stub
		
		CommentVO cmtVO = cmtDao.findById(c_id);
		cmtVO.setC_recommend(cmtVO.getC_recommend()+1);
		
//		CRecommendVO c_recommend  = new CRecommendVO();
		CRecommendVO c_recommend = cmtDao.findRecommendById(loginUsername);
		// 추천을 하려면 추천 테이블
		// 값이 하나도 없으면 (null 이면) true;
		// 값이 있는데(!= null)
		//		이전에 username과 b_id가 똑같은게 있다면 return false;
		//		두개 다 똑같은게 없다면 return true;
		if(c_recommend != null) {
			if(c_recommend.getC_r_username().equals(loginUsername) && 
					c_recommend.getC_r_board_id().equals(c_id)) return false;
			else {
				return true;
			}
		} else {
			return true;
		}
	}

	
}
