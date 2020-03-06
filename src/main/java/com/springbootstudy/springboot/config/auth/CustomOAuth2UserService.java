package com.springbootstudy.springboot.config.auth;

import com.springbootstudy.springboot.config.auth.dto.OAuthAttributes;
import com.springbootstudy.springboot.config.auth.dto.SessionUser;
import com.springbootstudy.springboot.domain.user.User;
import com.springbootstudy.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

/**
 *
 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String registerationId = userRequest.getClientRegistration().getRegistrationId();
		// 현재 로그인 진행중인 서비스를 구분하는 코드, 지금은 구글만 사용하는 불필요한 값이지만 이후에 추가되면 구분할 때 사용.

		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		// OAuth2 로그인 진행 시 키가 되는 필드값을 이야기. PK라고 생각하면 됨. 구글의 경우 기본적으로 코드를 지원하지만 네이버, 카카오 등은 안한다. 구글 기본은 "sub". 이후 네이버로그인과 구글을 동시에 지원할 때 사용.

		OAuthAttributes attributes = OAuthAttributes.of(registerationId, userNameAttributeName, oAuth2User.getAttributes());
		// OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스. 다른 소셜로그인도 이 클래스를 사용한다.

		User user = saveOrUpdate(attributes);

		httpSession.setAttribute("user", new SessionUser(user)); // SessionUser : 세션에 사용자 정보를 저장하기 위한 DTO. 왜 User클래스를 쓰지 않고 새로 만드는지는 뒤에서 설명.

		return new DefaultOAuth2User(
				Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
				attributes.getAttributes(),
				attributes.getNameAttributeKey()
		);
	}

	private User saveOrUpdate(OAuthAttributes attributes) {
		User user = userRepository.findByEmail(attributes.getEmail())
				.map(entity -> entity.update(
						attributes.getName(),
						attributes.getPicture()
				)).orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}

