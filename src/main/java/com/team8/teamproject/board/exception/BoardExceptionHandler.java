package com.team8.teamproject.board.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BoardExceptionHandler {

    //TODO 예외처리 화면에 던지기(API)

    @ExceptionHandler(BoardNameDuplicateException.class)
    public ResponseEntity<ErrorResponse> BoardDuplicateExceptionHandler(BoardNameDuplicateException e) {
        log.error("[BoardNameDuplicateException] ex", e);
        ErrorResponse response = new ErrorResponse("BAD", e.getMessage());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @Data
    @AllArgsConstructor
    static class ErrorResponse {
        private String Code;
        private String message;
    }
}
