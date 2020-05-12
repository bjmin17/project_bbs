package com.biz.bbs.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.CRecommendVO;
import com.biz.bbs.domain.CommentVO;

public interface CommentDao {

	@Select(" SELECT * FROM tbl_comment")
	public List<CommentVO> selectAll();
	
	@Select(" SELECT * FROM tbl_comment WHERE c_id = #{c_id}")
	public CommentVO findById(String c_id);

	/*
	 * 게시판 원글에 달린 코멘트들만 추출하기
	 */
	@Select("SELECT * FROM tbl_comment "
			+ " WHERE c_b_id = #{c_b_id} " 
			+ " AND c_p_id = 0 "
			+ " ORDER BY c_date_time ASC "
			 )
	public List<CommentVO> findByBId(long c_b_id);

	@Select("SELECT * FROM tbl_comment "
			+ " WHERE c_p_id = #{c_p_id} "
			+ " ORDER BY c_date_time ASC "
			 )
	public List<CommentVO> findByPId(long c_id);

	public int update(CommentVO commentVO);

	public int insert(CommentVO commentVO);

	@Delete("DELETE FROm tbl_comment WHERE c_id = #{c_id}")
	public int delete(long c_id);

	// 댓글 추천수 중복 조회 위한 메서드
	@Select("SELECT * FROM tbl_c_recommend WHERE c_r_username = #{loginUsername}")
	public CRecommendVO findRecommendById(String loginUsername);

	public void insertRecommend(@Param("c_id")String c_id, @Param("loginUsername") String loginUsername);

}
