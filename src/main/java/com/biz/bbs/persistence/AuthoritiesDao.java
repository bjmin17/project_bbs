package com.biz.bbs.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.AuthorityVO;

/**
 * 
 * 권한 정보 있는 테이블과 연동
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */
public interface AuthoritiesDao {

	// 사용자 이름으로 권한 테이블에서 권한 리스트를 SELECT
	@Select("SELECT * FROM authorities WHERE username = #{username}")
	public List<AuthorityVO> findByUserName(String username);

	public void insert(List<AuthorityVO> authCollection);
	
	public void delete(String username);

}
