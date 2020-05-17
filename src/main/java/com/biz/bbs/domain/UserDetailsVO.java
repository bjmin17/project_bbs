package com.biz.bbs.domain;

import java.util.Collection;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Spring Security와 연동하여 회원정보를 관리하기 위해
 * UserDetails라는 인터페이스를 implements하여 작성
 * 
 * 2020-05-03
 * 
 * method를 Override하지 않고 필드변수들을 선언하고 lombok으로
 * getter, setter 선언.
 * 
 * 여기서 만든 UserDetailsVO는 Spring Security와 연동하여
 * 사용자 정보를 관리할 클래스가 된다.
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder

public class UserDetailsVO implements UserDetails{

	private Long id;
	
	@NotEmpty(message = "아이디는 입력해야 합니다.")
	@Length(min=4, max = 16, message = "아이디는 4 ~ 16자 입력해야 합니다.")
	private String username;
	
	@NotEmpty(message = "비밀번호를 입력해야 합니다.")
	@Length(min=4, max = 16, message = "비밀번호는 4 ~ 16자 입력해야 합니다.")
	private String password;
	private boolean enabled;
	
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	private String email;
	private String phone;
	private String address;
}
