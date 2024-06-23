package com.example.demo.user.service;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import org.junit.jupiter.api.Test;

import com.example.demo.mock.FakeMailSender;

public class CertificationServiceTest {

	@Test
	public void 이메일과컨텐츠가제대로만들어져보내지는지테스트한다() {
		// given
		FakeMailSender fakeMailSender = new FakeMailSender(); // 명세인 MailSender를 구현해서 Test를 한다.
		CertificationService certificationService = new CertificationService(fakeMailSender);

		// when
		certificationService.send("kyj91032@naver.com", 2, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");

		// then
		assertThat(fakeMailSender.email).isEqualTo("kyj91032@naver.com");
		assertThat(fakeMailSender.title).isEqualTo("Please certify your email address");
		assertThat(fakeMailSender.content).isEqualTo(
			"Please click the following link to certify your email address: http://localhost:8080/api/users/2/verify?certificationCode=aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"
		);

	}
}
