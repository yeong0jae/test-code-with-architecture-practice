package com.example.demo.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
	@Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
	@Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class UserServiceTest {

	@Autowired
	private UserService userService;

	@Test
	void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		String email = "kyj91032@naver.com";

		// when
		UserEntity result = userService.getByEmail(email);

		// then
		assertThat(result.getNickname()).isEqualTo("kyj");

	}

	@Test
	void getByEmail은_PENDING_상태인_유저를_찾아올_수_없다() {
		// given
		String email = "kyj91033@naver.com";

		// when
		// then
		assertThatThrownBy(() -> {
			UserEntity result = userService.getByEmail(email);
		}).isInstanceOf(ResourceNotFoundException.class);

	}

	@Test
	void getById은_ACTIVE_상태인_유저를_찾아올_수_있다() {
		// given
		// when
		UserEntity result = userService.getById(1);

		// then
		assertThat(result.getNickname()).isEqualTo("kyj");

	}

	@Test
	void getById은_PENDING_상태인_유저를_찾아올_수_없다() {
		// given
		// when
		// then
		assertThatThrownBy(() -> {
			UserEntity result = userService.getById(2);
		}).isInstanceOf(ResourceNotFoundException.class);

	}
}
