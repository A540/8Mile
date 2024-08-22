package com.team8.teamproject.comments.mapper;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-21T15:34:23+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public Comments toEntity(AddCommentRequest addCommentRequest) {
        if ( addCommentRequest == null ) {
            return null;
        }

        String content = null;

        content = addCommentRequest.getContent();

        Long id = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;
        Long postId = null;

        Comments comments = new Comments( id, content, createdAt, updatedAt, postId );

        return comments;
    }
}
