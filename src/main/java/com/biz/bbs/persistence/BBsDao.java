package com.biz.bbs.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.BBsVO;

public interface BBsDao {

	@Select(" SELECT * FROM tbl_bbs ")
	public List<BBsVO> selectAll();

}
