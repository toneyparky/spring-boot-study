package com.springbootstudy.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // 스프링 부트의 설정 등이 자동으로 된다. 이는 프로젝트 최상단에 있어야 함.
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}