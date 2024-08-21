package com.team8.teamproject.board.controller.dto;

import com.team8.teamproject.board.domain.Board;
import lombok.Data;

@Data
public class BoardViewDto {

    private Long id;
    private String name;
    private String description;

    public BoardViewDto(Board board) {
        this.id = board.getId();
        this.name = board.getName();
        this.description = board.getDescription();
    }
}
