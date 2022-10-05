package com.kozlovam.exceptions;

public class NullUserException extends Exception{
    private String userlogin;

    public NullUserException(String message, String userlogin) {
        super(message);
        this.userlogin = userlogin;
    }
    @Override
    public String toString() {
        return "Error. Unable to identify the user: userlogin = " + userlogin
                +", message: " + getMessage();
    }
}

