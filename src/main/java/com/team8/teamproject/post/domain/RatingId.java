package com.team8.teamproject.post.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@Data
public class RatingId implements Serializable {
    private Long postId;
    private long memberId;

    public RatingId(Long postId, long memberId){
        this.postId = postId;
        this.memberId = memberId;
    }
}
