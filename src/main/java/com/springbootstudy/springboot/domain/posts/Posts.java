package com.springbootstudy.springboot.domain.posts;

import com.springbootstudy.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
/*
 * 추가 주석
 * Entity 클래스에는 절대 Setter 메소드를 만들지 않는다. -> 대신 해당 필드의 값 변경이 필요하면 명확히 그 목적과 의도를 나타낼 수 있는 메소드를 추가한다.
 * 즉 객체지향적으로 객체에게 메시지를 보낸다.
 * 여기서 질문 : Setter가 없는데 어떻게 DB를 insert하는가?
 * 답변 : 기본적으로는 생성자를 통해 최종값을 채운 후 DB에 삽입. 변경을 요하면 public 메서드 호출하여 변경.
 * 		추가적으로 빌더클래스를 사용해서 만들어주는 방법이 있는데 이 책에서는 이 방법으로 할거임. 이 방법도 결국은 생성자와 같은 시기에 채운다.
 * 		추가로 생성자 사용시 파라미터 형태가 같은걸 순서 바꾸면 이런 오류는 찾기 힘들다는 단점도 있다.
 */

// 이동욱님께서는 주요 어노테이션을 클래스에 가깝게 두신다. 즉 JPA를 가깝게. 롬복같은 경우는 나중에 코틀린으로 리팩토링시 쉽게 제거가능하도록..!!!
@Getter // 롬복 어노테이션 : 클래스 내 모든 필드의 Getter 메소드를 자동생성
@NoArgsConstructor // 롬복 어노테이션 : 기본 생성자 자동 추가 -> 여기서는 public Posts() {}
@Entity // JPA 어노테이션 : 테이블과 링크될 클래스임을 나타냄, 기본값으로 클래스의 카멜케이스 이름을 언더스코어 네이밍으로 테이블 이름을 매칭. 즉 SalesManager.java -> sales_manager table
public class Posts extends BaseTimeEntity { // 실제 디비의 테이블과 매칭될 클래스 : 보통 Entity 클래스라고한다. JPA 사용시 DB 데이터에 작업할 경우 실제 쿼리를 날리기보다는, 이 Entity클래스의 수정을 통해 작업.
	@Id // 해당 테이블의 PK 필드
	@GeneratedValue(strategy = GenerationType.IDENTITY) // PK의 생성 규칙. GenerationType.IDENTITY를 추가해야만 auto_increment가 된다.
	private Long id; // 추천 : PK는 Long으로 하고 auto_increment로 하자. 즉 Long타입의 1~이런 정수로.

	@Column(length = 500, nullable = false) // 테이블 칼럼을 나타내며 굳이 선언하지 않더라도 해당 클래스의 필드는 모두 칼럼이 된다. (아래 주석으로)
	private String title;

	@Column(columnDefinition = "Text", nullable = false) // 사용하는 이유로는 기본값 이외 추가 변경 옵션 사용하고플 경우. 예를들어 문자열이 VARCHAR(255)가 기본인데 500으로/ 혹은 타입을 변경하고플 경우.
	private String content;

	private String author;

	@Builder // 해당 클래스의 빌더 패턴 클래스 생성, 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함.
	public Posts(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public void update(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
