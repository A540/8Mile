package com.team8.teamproject.comments.mapper;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.ReadCommentResponse;
import com.team8.teamproject.login.entity.Member;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-27T12:07:14+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class CommentMapperImpl implements CommentMapper {

    @Override
    public ReadCommentResponse commentsToResponse(Comments comments) {
        if ( comments == null ) {
            return null;
        }

        Long id = null;
        String content = null;
        int likeCount = 0;
        LocalDateTime createdAt = null;
        Member member = null;

        id = comments.getId();
        content = comments.getContent();
        likeCount = comments.getLikeCount();
        createdAt = comments.getCreatedAt();
        member = comments.getMember();

        ReadCommentResponse readCommentResponse = new ReadCommentResponse( id, content, likeCount, createdAt, member );

        return readCommentResponse;
    }

    @Override
    public List<ReadCommentResponse> commentsToResponses(List<Comments> byPost) {
        if ( byPost == null ) {
            return null;
        }

        List<ReadCommentResponse> list = new ArrayList<ReadCommentResponse>( byPost.size() );
        for ( Comments comments : byPost ) {
            list.add( commentsToResponse( comments ) );
        }

        return list;
    }
}
