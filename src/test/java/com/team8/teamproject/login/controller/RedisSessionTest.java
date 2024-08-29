package com.team8.teamproject.login.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.test.context.support.WithMockUser;

import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class RedisSessionTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private HttpSession session;

    @Test
    @WithMockUser(username = "testuser", roles = "USER")
    public void testSessionSavedInRedis() {
        // 세션 데이터 설정
        session.setAttribute("testAttribute", "testValue");

        // Redis에서 세션 데이터 확인
        String sessionId = session.getId();
        Object sessionData = redisTemplate.opsForValue().get("spring:session:sessions:" + sessionId);

        // 세션 데이터가 Redis에 저장되었는지 확인
        assertNotNull(sessionData, "Session data should be saved in Redis");
    }
}
