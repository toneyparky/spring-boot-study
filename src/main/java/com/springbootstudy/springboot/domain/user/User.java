package com.springbootstudy.springboot.domain.user;

import com.springbootstudy.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 */

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column
	private String picture;

	@Enumerated(EnumType.STRING) // JPA로 DBddp wjwkdtl Enum값을 어떤 현태로 저장할지 결정. 기본적으로는 int형 숫자가 저장, 그런데 그러면 그 값이 무슨 코드를 의미하는지 알 수가 없다. 그래서 String으로 저장!
	@Column(nullable = false)
	private Role role;

	@Builder
	public User(String name, String email, String picture, Role role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}

	public User update(String name, String picture) {
		this.name = name;
		this.picture = picture;

		return this;
	}

	public String getRoleKey() {
		return this.role.getKey();
	}

}
