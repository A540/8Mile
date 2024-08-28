package com.team8.teamproject.login.controller.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MemberDtoTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSerialization() throws Exception {
        MemberDto memberDto = new MemberDto("John Doe", "john.doe@example.com");

        // MemberDto 객체를 JSON 문자열로 직렬화
        String json = objectMapper.writeValueAsString(memberDto);

        // JSON 문자열이 null이 아님을 확인
        assertNotNull(json);

        // JSON 문자열이 예상한 형태를 가지는지 검증
        String expectedJson = "{\"userName\":\"John Doe\",\"email\":\"john.doe@example.com\"}";
        assertEquals(expectedJson, json);
    }

    @Test
    void testDeserialization() throws Exception {
        // JSON 문자열로부터 MemberDto 객체를 역직렬화
        String json = "{\"userName\":\"John Doe\",\"email\":\"john.doe@example.com\"}";

        // JSON 문자열을 MemberDto 객체로 역직렬화
        MemberDto memberDto = objectMapper.readValue(json, MemberDto.class);

        // MemberDto 객체가 null이 아님을 확인
        assertNotNull(memberDto);

        // MemberDto 객체의 필드 값이 예상한 값과 일치하는지 검증
        assertEquals("John Doe", memberDto.getUserName());
        assertEquals("john.doe@example.com", memberDto.getEmail());
    }
}
