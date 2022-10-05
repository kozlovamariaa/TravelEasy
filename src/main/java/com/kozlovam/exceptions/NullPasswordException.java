package com.kozlovam.exceptions;

public class NullPasswordException extends Exception{

    public NullPasswordException(String message) {
        super(message);
    }
    @Override
    public String toString() {
        return "Error. Unable to identify the user:"
                +", message: " + getMessage();
    }

}
