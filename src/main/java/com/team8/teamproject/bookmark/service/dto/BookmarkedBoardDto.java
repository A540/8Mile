package com.team8.teamproject.bookmark.service.dto;

import com.team8.teamproject.board.domain.Board;
import lombok.Getter;

@Getter
public class BookmarkedBoardDto {

    private Long id;
    private String name;
    private String description;
    private long viewCount;
    private boolean isBookMarked;

    public BookmarkedBoardDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
        this.viewCount = board.getViewCount();
    }

    public void changeIsBookMarked(boolean bookmarked) {
            isBookMarked = bookmarked;
    }
}

