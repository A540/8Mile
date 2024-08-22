package com.team8.teamproject.login.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@NoArgsConstructor
@Entity
public class Member {
    // PK 지정
    @Id
    // 데이터베이스에 따라 자동으로 ID가 지정됩니다. - 기본 세팅
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    private String name;

    private String email;

    private String password;

    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date modifiedAt;


    public Member(long userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    //생성 메서드
    public Member createMember(long userId, String name, String email, String password) {
        Member newMember = new Member(userId, name, email, password);
        return newMember;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public long getuserId() {
        return userId;
    }

    public void setuserId(long id) {
        this.userId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}