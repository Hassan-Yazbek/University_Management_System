package com.example.studentiul;

public class ApiResponse {
    // Define fields according to your server response
    private boolean success;
    private String message;

    // Getters and setters for the fields
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

