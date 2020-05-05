package com.biz.bbs.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.bbs.domain.AuthorityVO;
import com.biz.bbs.domain.UserDetailsVO;
import com.biz.bbs.persistence.AuthoritiesDao;
import com.biz.bbs.persistence.UserDao;

/**
 * 회원정보 CRUD 서비스
 * 
 * 
 * @since 2020-05-03
 * @author jminban
 *
 */
@Service
//@RequiredArgsConstructor
public class UserService {

	private final PasswordEncoder passwordEncoder;
	private final UserDao userDao;
	private final AuthoritiesDao authDao;
	
	
	@Autowired
	public UserService(PasswordEncoder passwordEncoder, UserDao userDao, AuthoritiesDao authDao) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userDao = userDao;
		this.authDao = authDao;
		
		String create_user_table = 
						" CREATE TABLE IF NOT EXISTS tbl_users( " +
						"        id bigint PRIMARY KEY AUTO_INCREMENT, " +
						"        user_id varchar(50) UNIQUE, " +
						"        user_pass varchar(125), " +
						"        enabled boolean default true, " +
						"        email varchar(50)," +
						"        phone varchar(20)," +
						"        address varchar(125)" +
						" ) ";
		
		String create_auth_table = 
						" CREATE TABLE IF NOT EXISTS authorities(" +
						"        id bigint PRIMARY KEY AUTO_INCREMENT," +
						"        username varchar(50)," +
						"        authority varchar(50)" +
						" ) ";
		
		userDao.create_table(create_user_table);
		userDao.create_table(create_auth_table);
	}

	
	
	/*

	 */
	
	/**
	 * @since 2020-05-03
	 * @author jminban
	 * 
	 * @param username
	 * @param password
	 * @return
	 * 
	 * 회원가입을 신청하면 비밀번호는 암호화,
	 * 아이디와 비밀번호를 DB에 insert를 수행
	 */
	
	@Transactional
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
		if(userVO != null && userVO.getUsername().equalsIgnoreCase(username))
			return true;
		
		return false;
	}


	/**
	 * @since 2020-05-04
	 * @author jminban
	 * 
	 * @param userVO
	 * @param auth
	 * @return
	 * 
	 * 마이페이지에서 유저정보 업데이트 수행
	 */
	@Transactional
	public int update(UserDetailsVO userVO, String[] authList) {
		// TODO 유저 정보 업데이트
		
		Authentication oldAuth = SecurityContextHolder.getContext().getAuthentication();
		
		UserDetailsVO oldUserVO = (UserDetailsVO) oldAuth.getPrincipal();
		
		oldUserVO.setEmail(userVO.getEmail());
		oldUserVO.setPhone(userVO.getPhone());
		oldUserVO.setAddress(userVO.getAddress());
		
		int ret = userDao.update(userVO);
		
		if(ret > 0) {
			List<AuthorityVO> authCollection = new ArrayList<AuthorityVO>();
			for(String auth : authList) {
				// auth가 빈 값인 경우가 생기지 않게 검사하기
				if(!auth.isEmpty()) {
					AuthorityVO authVO = AuthorityVO.builder()
								.username(userVO.getUsername())
								.authority(auth).build();
					authCollection.add(authVO);
				}
			}
			
			// 기존에 있는 권한들 지우기
			authDao.delete(userVO.getUsername());
			// 새로운 권한들 생성
			authDao.insert(authCollection);
			
			// Dao 만들어주기
			
			// 새로운 session 정보를 만들때는 oldUserVO로 세팅하기
			Authentication newAuth = new UsernamePasswordAuthenticationToken(oldUserVO, //변경된 사용자 정보
					oldAuth.getCredentials(),
					this.getAuthorities(authList)//변경된 ROLE 정보
					);
			// get으로 뽑아낸 것중에 credential만 new Auth로 바꾸고 context로 세팅해주기
			SecurityContextHolder.getContext().setAuthentication(newAuth);
			
		}
		
		return ret;
	}
	
	private Collection<GrantedAuthority> getAuthorities(String[] authList) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		for(String auth : authList) {
			// auth가 빈칸이 아닐 경우만 추가하기
			if(!auth.isEmpty()) {
				SimpleGrantedAuthority sAuth = new SimpleGrantedAuthority(auth);
				authorities.add(sAuth);
			}
		}
		return authorities;
	}


	/**
	 * @since 2020-05-05
	 * @param password
	 * @return
	 */
	public boolean check_password(String password) {
		// TODO 비밀번호 체크 메서드
		
		// context - authentication - principal 순으로 뽑아오기
		// 이 방법은 본인 것만 수정 가능하고
		// 관리자는 비밀번호를 변경 못한다.
		UserDetailsVO userVO = (UserDetailsVO) SecurityContextHolder
										.getContext()
										.getAuthentication()
										.getPrincipal();
		
		return passwordEncoder.matches(password, userVO.getPassword());
	}
	
	
	
	
	
	
}
