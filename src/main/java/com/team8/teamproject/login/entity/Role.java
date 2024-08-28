package com.team8.teamproject.login.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN");

    private final String key;

    Role(String key) {
        this.key = key;
    }

    @JsonValue
    public String getKey() {
        return key;
    }

    @JsonCreator
    public static Role fromKey(String key) {
        for (Role role : values()) {
            if (role.getKey().equals(key)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown key: " + key);
    }
}
