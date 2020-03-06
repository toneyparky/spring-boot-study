package com.springbootstudy.springboot.config.auth.dto;

import com.springbootstudy.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

/**
 * 유저클래스를 안쓰고 이걸 사용하는 이유 :
 * 에러가 뜬다 User를 그냥 세션에 저장하려하면. 왜냐면 직렬화를 구현하지 않았다고 한다.
 * User 클래스가 엔티티기 때문에 다른 엔티티와 관계가 언제 형성될 지 모른다.
 * 예를들어 OneToMany, ManyToMany 같은 자식 엔티티를 가진다면 직렬화 대상에 자식들까지 포함되니 성능과 부스 이슈가 발생한다.
 * 그래서 직렬화 기능을 가진 세션 Dto를 하나 만드는 것이 도움 된다.
 */
@Getter
public class SessionUser implements Serializable {
	private String name;
	private String email;
	private String picture;

	public SessionUser(User user) { // 여기엔 인증된 사용자 정보만 필요하기에 이 세개 빼고는 필요 없다.
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
}
