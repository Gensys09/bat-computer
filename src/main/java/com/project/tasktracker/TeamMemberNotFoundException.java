package com.project.tasktracker;

class TeamMemberNotFoundException extends RuntimeException{
    TeamMemberNotFoundException(Long id) {
        //calls the constructor of the RuntimeException class
        super("Could not find team member " + id + ".");

    }
}
