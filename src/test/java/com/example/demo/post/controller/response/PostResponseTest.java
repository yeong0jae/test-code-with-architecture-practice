package com.example.demo.post.controller.response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

public class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("hello world")
                .writer(User.builder()
                        .email("kyj91032@naver.com")
                        .nickname("kyj")
                        .address("seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("hello world");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("kyj91032@naver.com");
    }
}
