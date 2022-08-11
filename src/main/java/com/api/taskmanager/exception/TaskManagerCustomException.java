package com.api.taskmanager.exception;

public class TaskManagerCustomException extends RuntimeException {

    public static final String ID_NOT_FOUND = "Resource id not found";
    public static final String FORBIDDEN = "The authenticated user can't access this resource";
    public static final String USERNAME_NOT_AVAILABLE = "The username is not available";
    public static final String USER_ALREADY_USED_EMAIL = "The email already has registered";

    public TaskManagerCustomException(String message) {
        super(message);
    }

    TaskManagerCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
