package com.biz.bbs.service.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.biz.bbs.domain.AuthorityVO;
import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.persistence.AuthoritiesDao;
import com.biz.bbs.persistence.UserDao;

import lombok.RequiredArgsConstructor;

/**
 * 
 * 사용자의 상세정보를 DB로부터 가져와서
 * spring security 여러 곳에서 사용할 수 있도록 VO에 연동하는 method
 * 
 * UserDetailsService 인터페이스를 가져와서 Customizing
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */

@RequiredArgsConstructor
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	
	private final AuthoritiesDao authDao;
	private final UserDao userDao;

	/*
	 * DB로부터 데이터를 불러와서 UserDetailsVO에
	 * 데이터를 복사하여 연동하는 코드가 작성될 곳
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// DB로부터 사용자 정보 가져오기
		UserDetailsVO userVO = userDao.findByUserName(username);
		
		// spring security가 사용할 DetailVO 선언
		UserDetailsVO userDetailsVO = new UserDetailsVO();
		userDetailsVO.setUsername(userVO.getUsername());
		userDetailsVO.setPassword(userVO.getPassword());
		userDetailsVO.setEnabled(true);
		
		// 사용자 정보를 사용할 수 있는가 아니가를 세밀하게
		// 제어하기 위한 칼럼
		userDetailsVO.setAccountNonExpired(true);
		userDetailsVO.setAccountNonLocked(true);
		userDetailsVO.setCredentialsNonExpired(true);
		
		userDetailsVO.setEmail("aaa@abc.com");
		userDetailsVO.setPhone("010-1221-3432");
		userDetailsVO.setAddress("광주광역시");
		
		
		userDetailsVO.setAuthorities(this.getAuthorities(username));
		
		
		return userDetailsVO;
	}
	
	private Collection<GrantedAuthority> getAuthorities(String username) {
		List<AuthorityVO> authList = authDao.findByUserName(username);
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(AuthorityVO vo : authList) {
			SimpleGrantedAuthority sAuth = new SimpleGrantedAuthority(vo.getAuthority());
			authorities.add(sAuth);
		}
		
		return authorities;
		
	}

}
