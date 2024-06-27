package com.example.demo.post.domain;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

public class PostTest {

    @Test
    public void PostCreate으로_게시물을_만들_수_있다() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("helloworld")
                .build();
        User writer = User.builder() // UserCreate를 왜 사용하지 않는지 의문: UserCreate는 User를 생성할 때 사용하는데, Post를 생성할 때 User를 생성하는 것이 아니라 이미 생성된 User를 사용하는 것이기 때문
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();

        // when
        Post post = Post.from(writer, postCreate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("helloworld"); // PostCreate의 content가 Post의 content로 잘 들어갔는지 확인
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L); // TestClockHolder를 사용하여 생성 시간이 잘 들어갔는지 확인
        assertThat(post.getWriter().getEmail()).isEqualTo("kyj91032@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("kyj");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }

    @Test
    public void PostUpdate로_게시물을_수정할_수_있다() {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();
        User writer = User.builder() // UserCreate를 왜 사용하지 않는지 의문: UserCreate는 User를 생성할 때 사용하는데, Post를 생성할 때 User를 생성하는 것이 아니라 이미 생성된 User를 사용하는 것이기 때문
                .id(1L)
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        Post post = Post.builder()
                .id(1L)
                .content("helloworld")
                .createdAt(1678530673958L)
                .modifiedAt(0L)
                .writer(writer)
                .build();

        // when
        post = post.update(postUpdate, new TestClockHolder(1678530673958L));

        // then
        assertThat(post.getContent()).isEqualTo("foobar"); // PostCreate의 content가 Post의 content로 잘 들어갔는지 확인
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }


}
