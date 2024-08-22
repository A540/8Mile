package com.team8.teamproject.board.exception;

public class BoardNameDuplicateException extends RuntimeException {

    public BoardNameDuplicateException() {
        super();
    }

    public BoardNameDuplicateException(String message) {
        super(message);
    }

    public BoardNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public BoardNameDuplicateException(Throwable cause) {
        super(cause);
    }

    protected BoardNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
