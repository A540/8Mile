package com.team8.teamproject.post.domain;

import com.team8.teamproject.login.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Rating {
    @EmbeddedId
    private RatingId id;
    private double rating;

    public Rating(RatingId ratingId, double rating){
        this.id = ratingId;
        this.rating = rating;
    }
}
