package com.biz.bbs.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Select;

import com.biz.bbs.domain.UserDetailsVO;

/**
 * 
 * 회원정보 CRUD 수행할 Dao 생성
 * 
 * @since 2020-05-03
 * @author User
 *
 */
public interface UserDao {
	
	public List<UserDetailsVO> selectAll();
	
	public void create_table(String create_table);
	
	@Select(" SELECT user_id AS username, "
			+ " user_pass AS password, "
			+ " enabled "
			+ " FROM tbl_users WHERE user_id = #{username}"
			)
	public UserDetailsVO findByUserName(String username);
	
	public int insert(Map<String, String> userMap);
	
	public UserDetailsVO findById(Long id);
	
	public int update(UserDetailsVO userVO);
}
