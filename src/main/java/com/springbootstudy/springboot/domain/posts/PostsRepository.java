package com.springbootstudy.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MyBatis에서 Dao라고 불리는 DB Layer 접근자. JPA에서는 레포지토리라고 부르며 인터페이스로 생성.
 * JpaRepository<Entity class, PK type>을 상속하면 자동으로 기본적인 CRUD 메서드가 만들어진다.
 * Entity와 함께 위치해야한다.
 */
public interface PostsRepository extends JpaRepository<Posts, Long> {
}
