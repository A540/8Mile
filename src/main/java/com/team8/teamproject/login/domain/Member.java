package com.team8.teamproject.login.domain;

import jakarta.persistence.*;

@Entity
public class Member {
    // PK 지정
    @Id
    // 데이터베이스에 따라 자동으로 ID가 지정됩니다. - 기본 세팅
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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