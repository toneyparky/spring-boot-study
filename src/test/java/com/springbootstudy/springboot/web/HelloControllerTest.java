package com.springbootstudy.springboot.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class) // 테스트시 Junit 외에 다른 실행자도 실행. 여기서는 SpringRunner. 스프링부트테스트와 Junit사이의 연결자
@WebMvcTest(controllers = HelloController.class) // Web에 집중할 수 있는 어노테이션. 컨트롤러에만 사용
public class HelloControllerTest {
	@Autowired // 스프링이 관리하는 빈을 주입받는다.
	private MockMvc mvc; // 웹 API를 테스트할 때 사용. 스프링 MVC 테스트의 시작점. 이 클래스를 통해 겟/포스트 등 테스트.

	@Test
	public void hello가_리턴된다() throws Exception {
		String hello = "hello";

		mvc.perform(get("/hello")) // MockMvc를 통해 get요청을 해당 주소로 보낸다. 체이닝이 지원되어 아래에 이을 수 있다.
				.andExpect(status().isOk()) // 상태를 검증한다. 헤더를 검증한다.
				.andExpect(content().string(hello)); // 본문을 검증한다. 내용을 검증한다.

	}

	@Test
	public void helloDto가_리턴된다() throws Exception {
		String name = "hello";
		int amount = 1000;

		mvc.perform(
				get("/hello/dto")
						.param("name", name) // API 테스트시 사용될 요청 파라미터를 설정, String만 허용, 그래서 문자열로 변경하자.
						.param("amount", String.valueOf(amount)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is(name))) // jsonPath: JSON응답 값을 필드별로 검증할 수 있는 메소드. $를 기준으로 필드명 명시
				.andExpect(jsonPath("$.amount", is(amount)));
	}
}
