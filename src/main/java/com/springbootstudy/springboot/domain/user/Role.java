package com.springbootstudy.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 *
 */
@Getter
@RequiredArgsConstructor
public enum Role {
	GUEST("ROLE_GUEST", "손님"), // 항상 ROLE로 시작해야한다. spring security에서는.
	USER("ROLE_USER", "일반사용자");

	private final String key;
	private final String title;
}
