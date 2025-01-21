package com.team8.teamproject.login.controller.dto;

import com.team8.teamproject.login.entity.Member;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberDtoSerializationTest {

    @Test
    public void testMemberDtoSerialization() throws IOException, ClassNotFoundException {
        // Given: Create a MemberDto instance
        Member member = new Member();
        member.setId(1L);
        member.setUserName("testUser");
        member.setEmail("test@example.com");

        MemberDto originalDto = new MemberDto(member);

        // When: Serialize the MemberDto instance to a byte array
        byte[] serializedDto;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(originalDto);
            serializedDto = bos.toByteArray();
        }

        // Then: Deserialize the byte array back to a MemberDto instance
        MemberDto deserializedDto;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedDto);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            deserializedDto = (MemberDto) ois.readObject();
        }

        // Assert: Verify that the original and deserialized objects are equal
        assertEquals(originalDto.getId(), deserializedDto.getId());
        assertEquals(originalDto.getUserName(), deserializedDto.getUserName());
        assertEquals(originalDto.getEmail(), deserializedDto.getEmail());
    }
}
