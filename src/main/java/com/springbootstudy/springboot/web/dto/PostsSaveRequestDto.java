package com.springbootstudy.springboot.web.dto;

import com.springbootstudy.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Entity 클래스와 거의 유사한 형태임에도 Dto클래스를 추가로 생성했다.
 * 하지만 절대로 Entity 클래스를 Request/Response 클래스로 사용해서는 안 된다.
 * Entity클래스는 데이터베이스와 맞닿은 핵심 클래스이다. 뷰단에 변화를 주기 위해서 이런 Entity에 변화를 주는 것은 너무나 큰 변경이다.
 * Request/Response를 위한 Dto는 뷰를 위한 클래스라서 오히려 자주 변경이 필요하다.
 */

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
	private String title;
	private String content;
	private String author;
	@Builder
	public PostsSaveRequestDto(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}
