package com.springbootstudy.springboot.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter // 선언된 모든 필드의 get메서드를 생성하는 롬복!!
@RequiredArgsConstructor // 선언된 모든 final 필드가 포함된 생성자를 생성. final 필수..!!
public class HelloResponseDto {

	private final String name;
	private final int amount;
}
