package com.biz.bbs.persistence;

import java.util.List;

import com.biz.bbs.domain.UserDetailsVO;

public interface UserDao {
	
	public List<UserDetailsVO> selectAll();
	
	public void create_table(String create_table);
	
	public UserDetailsVO findByUserName(String username);
	
	public int insert(UserDetailsVO userVO);
	
	public UserDetailsVO findById(Long id);
	
	public int update(UserDetailsVO userVO);
}
