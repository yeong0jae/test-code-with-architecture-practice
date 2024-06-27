package com.example.demo.user.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.mock.TestContainer;
import com.example.demo.user.controller.response.UserResponse;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class UserCreateControllerTest {

    @Test
    void 사용자는_회원_가입을_할_수있고_회원가입된_사용자는_PENDING_상태이다() throws Exception {
        // given
        TestContainer testContainer = TestContainer.builder()
                .uuidHolder(() -> "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        UserCreate userCreate = UserCreate.builder()
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Seoul")
                .build();

        // when
        ResponseEntity<UserResponse> result = testContainer.userCreateController.create(userCreate);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(result.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(result.getBody()).getEmail()).isEqualTo("kyj91032@naver.com");
        assertThat(result.getBody().getNickname()).isEqualTo("kyj");
        assertThat(result.getBody().getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getBody().getLastLoginAt()).isNull();
        assertThat(testContainer.userRepository.getById(0L).getCertificationCode()).isEqualTo(
                "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }

}