package com.springbootstudy.springboot.web.dto;

import com.springbootstudy.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 */

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model) { // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장 가능, 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달.
		model.addAttribute("posts", postsService.findAllDesc());
		return "index"; // 머스테치 스타터 덕분에 컨트롤러에서 문자열을 반환시 앞의 경로와 뒤의 파일 확장자는 자동으로 지정. 즉 src/main/resources/templates/index.mustache로 전환되어 뷰에서 처리함.
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
