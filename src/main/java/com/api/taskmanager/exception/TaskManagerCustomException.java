package com.api.taskmanager.exception;

public class TaskManagerCustomException extends RuntimeException {

    public static final String ID_NOT_FOUND = "Resource ID not found";

    public TaskManagerCustomException(String message) {
        super(message);
    }

    TaskManagerCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
