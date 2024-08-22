package com.team8.teamproject.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Date createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt;


    public Member(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    //생성 메서드
    public Member createMember(String userName, String email, String password) {
        Member newMember = new Member(userName, email, password);
        return newMember;
    }


}