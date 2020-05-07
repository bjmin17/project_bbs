package com.biz.bbs.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.BBsVO;

public interface BBsDao {

	@Select(" SELECT * FROM tbl_bbs ")
	public List<BBsVO> selectAll();

	public int insert(BBsVO bbsVO);

	@Select(" SELECT * FROM tbl_bbs WHERE b_id = #{b_id}")
	public BBsVO findById(String b_id);

	@Delete(" DELETE FROM tbl_bbs WHERE b_id = #{b_id}")
	public int delete(String b_id);

	public int update(BBsVO bbsVO);

}
