package com.batman.batcomputer;

class BatMemberNotFoundException extends RuntimeException{
    BatMemberNotFoundException(Long id) {
        //calls the constructor of the RuntimeException class
        super("Could not find bat member " + id + ".");

    }
}
