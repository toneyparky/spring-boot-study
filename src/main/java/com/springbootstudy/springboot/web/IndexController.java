package com.springbootstudy.springboot.web;

import com.springbootstudy.springboot.config.auth.LoginUser;
import com.springbootstudy.springboot.config.auth.dto.SessionUser;
import com.springbootstudy.springboot.service.posts.PostsService;
import com.springbootstudy.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model, @LoginUser SessionUser user) { // 두번째 파라미터는 어노테이션 사용하며 추가 됨. 이제 어느 컨트롤러든지 @LoginUser만 사용하면 세션 정보를 가져올 수 있다.
		model.addAttribute("posts", postsService.findAllDesc());
//	어노테이션 사용하며 주석처리	SessionUser user = (SessionUser) httpSession.getAttribute("user"); // CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser 를 저장하도록 구성된 것을 사용.
		if (user != null) { // 세션에 저장된 값이 있을 때만 모델에 userName으로 등록. 세션에 저장된 값이 없으면 model엔 아무런 값이 없는 상태라 로그인 버튼이 보인다.
			model.addAttribute("userName", user.getName());
		}
		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);

		return "posts-update";
	}
}
