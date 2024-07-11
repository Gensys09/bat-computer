package com.project.tasktracker;

public class ObjectiveNotFoundException extends RuntimeException {
    ObjectiveNotFoundException(Long id) {
        super("Could not find objective " + id + ".");
    }
}
