package com.team8.teamproject.login.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    // PK 지정
    @Setter
    @Getter
    @Id
    // 데이터베이스에 따라 자동으로 ID가 지정됩니다. - 기본 세팅
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Setter
    @Getter
    private String userName;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String password;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING) // Enum 타입은 문자열 형태로 저장해야 함
    //@NotNull
    private Role role;


    @Builder
    public Member(String userName, String email, String password, Role role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }


    public Member update(String userName, String password, Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}