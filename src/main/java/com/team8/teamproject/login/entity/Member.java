package com.team8.teamproject.login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userName;
    private String email;
    private String password;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonCreator
    @Builder
    public Member(
            @JsonProperty("userName") String userName,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("role") Role role
    ) {
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

@JsonIgnore
    public String getRoleKey() {
        return this.role.getKey();
    }
}