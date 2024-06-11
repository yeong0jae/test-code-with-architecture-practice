package com.example.demo.repository;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.example.demo.model.UserStatus;

@DataJpaTest // DataJpaTest 어노테이션을 사용하면 JPA 관련된 설정만 로드하고, H2 데이터베이스를 사용하게 된다.
@Sql("/sql/user-repository-test-data.sql") // @Sql 어노테이션을 사용하면 테스트 전에 SQL 파일을 실행할 수 있다.
public class UserRepositoryTest {

	@Autowired
	private UserRepository userRepository;

	@Test
	void findByIdAndStatus_로_유저를_찾을_수_있다() {
		// given
		// when

		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();

	}

	@Test
	void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();

	}

	@Test
	void findByEmailAndStatus_로_유저를_찾을_수_있다() {
		// given
		// when

		Optional<UserEntity> result = userRepository.findByEmailAndStatus("kyj91032@naver.com", UserStatus.ACTIVE);

		// then
		assertThat(result.isPresent()).isTrue();

	}

	@Test
	void findByEmailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
		// given
		// when
		Optional<UserEntity> result = userRepository.findByEmailAndStatus("kyj91032@naver.com", UserStatus.PENDING);

		// then
		assertThat(result.isEmpty()).isTrue();

	}

}

