package com.springbootstudy.springboot.web;

import com.springbootstudy.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController // restful하다는거 아닐까 -> 컨트롤러가 JSON을 반환하도록 만든다.
public class HelloController {
	@GetMapping("/hello") // get요청을 받을 수 있는 api를 만든다.
	public String hello() {
		return "hello";
	}

	@GetMapping("/hello/dto")
	public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
		// @RequestParam = 외부에서 API로 넘긴 파람을 가져오는 어노테이션, @RequestParam("name")으로 넘긴 파람을 name(String name)에 저장
		return new HelloResponseDto(name, amount);
	}
}
