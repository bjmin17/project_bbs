package com.biz.bbs.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.persistence.UserDao;

import lombok.RequiredArgsConstructor;

/**
 * 회원정보 CRUD 서비스
 * 
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */
@Service
@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserDao userDao;
	
	/*
	 * 회원가입을 신청하면 비밀번호는 암호화,
	 * 아이디와 비밀번호를 DB에 insert를 수행
	 */
	
	public int insert(String username, String password) {
		
		String encPassword = passwordEncoder.encode(password);
		Map<String,String> userVO = new HashMap<String,String>();
		
		userVO.put("username", username);
		userVO.put("password", encPassword);
		
		int ret = userDao.insert(userVO);
		return ret;
	}

	// 기존에 ID가 있는지 검사
	public boolean isExistsId(String username) {
		
		UserDetailsVO userVO = userDao.findByUserName(username);
		String user_name = userVO.getUsername();
		if(user_name != null && user_name.equalsIgnoreCase(username))
			return true;
		
		return false;
	}
	
	
	
	
	
	
}
