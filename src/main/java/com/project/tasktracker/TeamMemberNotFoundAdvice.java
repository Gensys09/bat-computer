package com.project.tasktracker;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TeamMemberNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(TeamMemberNotFoundException.class)

    //return 404 status
    @ResponseStatus(HttpStatus.NOT_FOUND)

    String teamMemberNotFoundHandler(TeamMemberNotFoundException ex) {
        return ex.getMessage();
    }
}
