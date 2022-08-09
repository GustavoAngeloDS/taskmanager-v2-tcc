package com.api.taskmanager.exception;

public class TaskManagerCustomException extends RuntimeException {

    public static final String ID_NOT_FOUND = "Resource id not found";
    public static final String FORBIDDEN = "The authenticated user can't access this resource";

    public TaskManagerCustomException(String message) {
        super(message);
    }

    TaskManagerCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
