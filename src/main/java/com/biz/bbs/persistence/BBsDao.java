package com.biz.bbs.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.BBsVO;
import com.biz.bbs.domain.BRecommendVO;
import com.biz.bbs.domain.PageVO;

public interface BBsDao {

	@Select(" SELECT * FROM tbl_bbs ORDER BY b_date DESC ")
	public List<BBsVO> selectAll();

	public int insert(BBsVO bbsVO);

	@Select(" SELECT * FROM tbl_bbs WHERE b_id = #{b_id}")
	public BBsVO findById(String b_id);

	@Delete(" DELETE FROM tbl_bbs WHERE b_id = #{b_id}")
	public int delete(String b_id);

	public int update(BBsVO bbsVO);

	@Select(" SELECT count(*) FROM tbl_bbs")
	public long totalCount();

	public List<BBsVO> selectAllPagination(@Param("pageVO") PageVO pageVO);

	// 컨트롤러에서 시작한 검색 페이징
	@Select("SELECT * FROM tbl_bbs WHERE b_subject LIKE CONCAT('%', #{search}, '%') or b_text LIKE CONCAT('%', #{search}, '%') ORDER BY b_date DESC LIMIT #{pageVO.offset}, 10")
	public List<BBsVO> selectAllListPagination(@Param("pageVO") PageVO pageVO, @Param("search") String search);
	
	@Select("SELECT count(*) FROM tbl_bbs WHERE b_subject LIKE CONCAT('%', #{search}, '%') or b_text LIKE CONCAT('%', #{search}, '%') ")
	public long searchAllListCount(String search);

	// 컨트롤러에서 시작한 검색 페이징
	@Select("SELECT * FROM tbl_bbs WHERE b_subject LIKE CONCAT('%', #{search}, '%') ORDER BY b_date DESC LIMIT #{pageVO.offset}, 10")
	public List<BBsVO> selectTitle(@Param("pageVO") PageVO pageVO, @Param("search") String search);
	
	@Select("SELECT count(*) FROM tbl_bbs WHERE b_subject LIKE CONCAT('%', #{search}, '%') ")
	public long searchSubjectCount(String search);
	
	// 컨트롤러에서 시작한 검색 페이징
	@Select("SELECT * FROM tbl_bbs WHERE b_text LIKE CONCAT('%', #{search}, '%') ORDER BY b_date DESC LIMIT #{pageVO.offset}, 10")
	public List<BBsVO> selectContent(@Param("pageVO") PageVO pageVO, @Param("search") String search);

	@Select("SELECT count(*) FROM tbl_bbs WHERE b_text LIKE CONCAT('%', #{search}, '%') ")
	public long searchTextCount(String search);

	/*
	 * 추천 중복 방지 테이블 DB 조회용
	 */
	public void insertRecommend(@Param("b_id")String b_id, @Param("loginUsername")String loginUsername);

	@Select("SELECT * FROM tbl_b_recommend WHERE b_r_board_id = #{b_id}")
	public BRecommendVO findRecommendById(String b_id);



}
