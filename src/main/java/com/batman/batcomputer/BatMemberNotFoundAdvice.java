package com.batman.batcomputer;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class BatMemberNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(BatMemberNotFoundException.class)

    //return 404 status
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String batMemberNotFoundHandler(BatMemberNotFoundException ex) {
        return ex.getMessage();
    }
}
