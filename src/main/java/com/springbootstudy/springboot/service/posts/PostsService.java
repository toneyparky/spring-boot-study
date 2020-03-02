package com.springbootstudy.springboot.service.posts;

import com.springbootstudy.springboot.domain.posts.Posts;
import com.springbootstudy.springboot.domain.posts.PostsRepository;
import com.springbootstudy.springboot.web.dto.PostsListResponseDto;
import com.springbootstudy.springboot.web.dto.PostsResponseDto;
import com.springbootstudy.springboot.web.dto.PostsSaveRequestDto;
import com.springbootstudy.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


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

	@Transactional
	public void delete (Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		postsRepository.delete(posts); // JpaRepository에서 이미 메소드를 지원하고 있으니 이를 활용. 엔틴티를 파라미터로 삭제할 수도 있고, deleteById메소드로 id로 삭제도 가능. 지금은 존재하는 것인지 파악하고 삭제.
	}

	public PostsResponseDto findById(Long id) {
		Posts entity = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
		return new PostsResponseDto(entity);
	}

	@Transactional(readOnly = true) // 이 옵션을 주면 트랜잭션 범위는 유지하되 조회 기능만 남겨두어 조회 속도가 개선. 그래서 등록, 수정, 삭제 기능이 없는 서비스 메소드에서 사용하길 추천.
	public List<PostsListResponseDto> findAllDesc() {
		return postsRepository.findAllDesc().stream()
				.map(PostsListResponseDto::new)
				.collect(Collectors.toList());
	}
}
