package com.team8.teamproject.board.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBoardForm {

    private Long id;
    private String name;
    private String description;


}
