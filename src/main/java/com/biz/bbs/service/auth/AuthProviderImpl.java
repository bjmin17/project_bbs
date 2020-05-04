package com.biz.bbs.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.bbs.domain.UserDetailsVO;

/**
 * 로그인 구현.
 * provider 사용
 * 
 * @since 2020-05-04
 * @author jminban
 *
 */
@Service
public class AuthProviderImpl implements AuthenticationProvider {

	@Autowired
	@Qualifier("userDetailsService")
	private UserDetailsServiceImpl userDService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 로그인을 하면 로그인 정보가 authentication에 담겨서 온다.
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO authenticate 메서드
		
		// 로그인 화면에서 가져온 정보
		// authentication으로부터 로그인 폼에서 보낸 username과 password를 추출
		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();
		
		// DB에 있는 사용자정보를 가져오기
		UserDetailsVO userVO = (UserDetailsVO) userDService.loadUserByUsername(username);
		if(!passwordEncoder.matches(password.trim(), userVO.getPassword())) {
			throw new BadCredentialsException("비밀번호 오류");
		}
		
		// enabled가 false이거나 사용금지된 user인 경우
		if(!userVO.isEnabled()) {
			throw new BadCredentialsException(username + " : 접근 권한 없음");
		}
		
		// 위에서 비교를 하고
		// 비교한 DB 정보를 Controller로 보내기
		// ** 권한들도 같이 보내줘야!!
		return new UsernamePasswordAuthenticationToken(userVO, null, userVO.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
