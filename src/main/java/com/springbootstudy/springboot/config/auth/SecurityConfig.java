package com.springbootstudy.springboot.config.auth;

import com.springbootstudy.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 */

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final CustomOAuth2UserService customOAuth2UserService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().frameOptions().disable() // h2-console 화면을 사용하기 위해 해당 옵션을 disable
				.and()
					.authorizeRequests() // URL별 권한 관리를 설정하는 옵션의 시작점. 이게 있어야만 antMatchers 옵션 사용 가능.
					.antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 전체 열람 권한을 줬다.
					.antMatchers("/api/v1/**").hasRole(Role.USER.name()) // antMatchers: 권한 관리 대상을 지정하는 옵션, URL/HTTP메소드 별로 관리가능. USER권한을 가진 사람만 권한.
					.anyRequest().authenticated() // 설정된 값들 이외 나머지 URL. 여기서는 authenticated()로 인증된 사용자들에게만 허용하게 함. 즉 로그인한 사용자.
				.and()
					.logout().logoutSuccessUrl("/") // 로그아웃 기능에 대한 여러 설정의 인입점. 로그아웃 성공시 해당 주소로 이동.
				.and()
					.oauth2Login() // 로그인 기능에 대한 여러 설정의 진입점.
						.userInfoEndpoint() // 로그인 성공 이후 사용자 정보를 가져올 때 설정을 담당.
							.userService(customOAuth2UserService); // 소셜 로그인 성공 시 후속조치를 진행할 UserService 인터페이스의 구현체를 등록. 리소스 서버(소셜)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자하는 기능을 명시 가능.
	}
}
