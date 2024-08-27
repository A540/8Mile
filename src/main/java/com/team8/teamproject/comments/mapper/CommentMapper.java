package com.team8.teamproject.comments.mapper;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.dto.ReadCommentResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    ReadCommentResponse commentsToResponse(Comments comments);
    List<ReadCommentResponse> commentsToResponses(List<Comments> byPost);

}
