package org.sandcastle.apps.service;

public class UserServiceException extends Exception {

    private static final long serialVersionUID = 2048438783813622198L;

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserServiceException(Throwable cause) {
        super(cause);
    }
}
