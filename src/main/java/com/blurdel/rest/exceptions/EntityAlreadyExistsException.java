package com.blurdel.rest.exceptions;

public class EntityAlreadyExistsException extends Exception {

    public EntityAlreadyExistsException() {
    }

    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }

}
