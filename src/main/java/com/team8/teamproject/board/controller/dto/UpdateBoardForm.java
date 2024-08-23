package com.team8.teamproject.board.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBoardForm {

    private Long id;

    @NotBlank
    @Size(min = 1, max = 50)
    @Pattern(regexp =  "^[a-zA-Z0-9가-힣 ]*$", message = "게시판명에 특수문자는 사용불가합니다.")
    private String name;
    private String description;


}
