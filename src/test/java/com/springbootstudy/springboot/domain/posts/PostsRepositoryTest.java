package com.springbootstudy.springboot.domain.posts;

import org.apache.tomcat.jni.Local;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest // 이거 쓰면 H2를 자동으로 쓴다.
public class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@After // Junit 단위테스트 끝날때 진행되는 메서드, 배포 전 전체 테스트를 수행시 테스트간 데이터 침범을 막기위해 사용, 여러 테스트가 동시에 수행되면 H2에 데이터가 그대로 남아서 다음 테스트에 악영향.
	public void cleanup() {
		postsRepository.deleteAll();
	}

	@Test
	public void 게시글저장_불러오기() {
		//given
		String title = "테스트 게시글";
		String content = "테스트 본문";

		postsRepository.save(Posts.builder() // ~.save : 테이블 posts에 insert/update 쿼리를 실행, id값이 있다면 update/ 없다면 insert 쿼리가 실행.
				.title(title)
				.content(content)
				.author("toneyparky@naver.com")
				.build());

		//when
		List<Posts> postsList = postsRepository.findAll(); // 테이블 posts에 있는 모든 데이터를 조회해오는 메서드.

		//then
		Posts posts = postsList.get(0);
		assertThat(posts.getTitle()).isEqualTo(title);
		assertThat(posts.getContent()).isEqualTo(content);
	}

	@Test
	public void BaseTimeEntity_등록() {
		//given
		LocalDateTime now = LocalDateTime.of(2019,6,4,0,0,0);
		postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());

		//when
		List<Posts> postsList = postsRepository.findAll();

		//then
		Posts posts = postsList.get(0);

		System.out.println(">>>>>>>>>> createDate=" + posts.getCreatedDate() + ", modifiedDate=" + posts.getModifiedDate());

		assertThat(posts.getCreatedDate()).isAfter(now);
		assertThat(posts.getModifiedDate()).isAfter(now);
	}
}
