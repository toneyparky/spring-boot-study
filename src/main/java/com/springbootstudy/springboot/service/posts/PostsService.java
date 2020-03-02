package com.springbootstudy.springboot.service.posts;

import com.springbootstudy.springboot.domain.posts.Posts;
import com.springbootstudy.springboot.domain.posts.PostsRepository;
import com.springbootstudy.springboot.web.dto.PostsResponseDto;
import com.springbootstudy.springboot.web.dto.PostsSaveRequestDto;
import com.springbootstudy.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 *
 */

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, PostsUpdateRequestDto requestDto) { // 보면 DB에 쿼리날리는 부분이 없다. 그 이유는 JPA의 영속성 컨텍스트 때문. 즉 엔티티를 영구저장하는 환경.
		Posts posts = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		posts.update(requestDto.getTitle(), requestDto.getContent());
		return id;
	}

	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new PostsResponseDto(entity);
	}
}
