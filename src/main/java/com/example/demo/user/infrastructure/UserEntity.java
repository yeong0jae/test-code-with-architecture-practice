package com.example.demo.user.infrastructure;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "address")
    private String address;

    @Column(name = "certification_code")
    private String certificationCode;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "last_login_at")
    private Long lastLoginAt;

    // infra가 domain을 알고, domain은 infra를 모르는 방향이 옳다.
    // domain은 어디에도 의존하지 않도록 설계해야 한다.
    // 테스트하기 쉽고, 유지보수하기 쉬운 코드를 작성하기 위해선 domain이 infra에 의존하면 안된다.

    public static UserEntity from(User user) { // fromModel : User(domain) -> UserEntity(infra)
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.email = user.getEmail();
        userEntity.nickname = user.getNickname();
        userEntity.address = user.getAddress();
        userEntity.certificationCode = user.getCertificationCode();
        userEntity.status = user.getStatus();
        userEntity.lastLoginAt = user.getLastLoginAt();
        return userEntity;
    }

    public User toModel() { // toModel : UserEntity(infra) -> User(Domain)
        return User.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .address(address)
                .certificationCode(certificationCode)
                .status(status)
                .lastLoginAt(lastLoginAt)
                .build();
    }
}