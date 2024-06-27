package com.example.demo.user.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void User는_UserCreate_객체로_생성할_수_있다() {

        // given
        UserCreate userCreate = UserCreate.builder()
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Pangyo")
                .build();

        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));

        // then
        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("kyj91032@naver.com");
        assertThat(user.getNickname()).isEqualTo("kyj");
        assertThat(user.getAddress()).isEqualTo("Pangyo");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }

    @Test
    public void UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("kyj2")
                .address("Seoul")
                .build();
        // when
        user = user.update(userUpdate);
        // then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getEmail()).isEqualTo("kyj91032@naver.com");
        assertThat(user.getNickname()).isEqualTo("kyj2");
        assertThat(user.getAddress()).isEqualTo("Seoul");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(user.getLastLoginAt()).isEqualTo(100L);
    }

    @Test
    public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    public void User는_유효한_인증코드로_계정을_활성화할수있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Pangyo")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    public void User는_잘못된_인증코드로_계정을_활성화_하려면_에러를_던진다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("kyj91032@naver.com")
                .nickname("kyj")
                .address("Pangyo")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        // then
        assertThatThrownBy(() -> {
            user.certificate("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}
