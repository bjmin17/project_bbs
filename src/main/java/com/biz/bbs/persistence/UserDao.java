package com.biz.bbs.persistence;

import java.util.List;
import java.util.Map;

import com.biz.bbs.domain.UserDetailsVO;

public interface UserDao {
	
	public List<UserDetailsVO> selectAll();
	
	public void create_table(String create_table);
	
	public UserDetailsVO findByUserName(String username);
	
	public int insert(Map<String, String> userMap);
	
	public UserDetailsVO findById(Long id);
	
	public int update(UserDetailsVO userVO);
}
