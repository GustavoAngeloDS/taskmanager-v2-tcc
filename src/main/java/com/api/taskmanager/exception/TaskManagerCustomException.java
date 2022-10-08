package com.api.taskmanager.exception;

public class TaskManagerCustomException extends RuntimeException {

    public static final String ID_NOT_FOUND = "Resource id not found";
    public static final String FORBIDDEN = "The authenticated user can't access this resource";
    public static final String USERNAME_NOT_AVAILABLE = "The username is not available";
    public static final String USER_ALREADY_USED_EMAIL = "The email already has registered";
    public static final String USER_ALREADY_IS_MEMBER = "The user already is a task member";
    public static final String USER_IS_NOT_MEMBER = "The user isn't a task member";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String POSITION_IS_NOT_AVAILABLE = "The position is not available";

    public TaskManagerCustomException(String message) {
        super(message);
    }

    TaskManagerCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
