package com.springbootstudy.springboot.web;

import com.springbootstudy.springboot.service.posts.PostsService;
import com.springbootstudy.springboot.web.dto.PostsResponseDto;
import com.springbootstudy.springboot.web.dto.PostsSaveRequestDto;
import com.springbootstudy.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *
 */

@RequiredArgsConstructor // 골뱅이Autowired를 스프링에서 빈 주입할 때에 사용할 수 있는데 추천안한다. 생성자로 주입받는 방식을 추천하고 이 어노테이션이 그 방식. final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 이 어노테이션이 생성.
@RestController
public class PostsApiController {

	private final PostsService postsService;

	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequestDto requestDto) {
		return postsService.save(requestDto);
	}

	@PutMapping("/api/v1/posts/{id}")
	public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
		return postsService.update(id, requestDto);
	}

	@GetMapping("/api/v1/posts/{id}")
	public PostsResponseDto findById (@PathVariable Long id) {
		return postsService.findById(id);
	}

	@DeleteMapping("api/v1/posts/{id}")
	public Long delete(@PathVariable Long id) {
		postsService.delete(id);
		return id;
	}
}
