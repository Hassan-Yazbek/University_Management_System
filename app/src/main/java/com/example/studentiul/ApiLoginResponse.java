package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

public class ApiLoginResponse {
    private String status;
    private String message;

    @SerializedName("student")
    private Login student;

    // Getters and setters for status, message, and student
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Login getStudent() {
        return student;
    }

    public void setStudent(Login student) {
        this.student = student;
    }

    public static class Login {
        private String name;
        @SerializedName("photo_url")
        private String profile;
        @SerializedName("students_id")
        private String studentId;

        // Getters and setters for name
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public String getProfile() {
            return profile;
        }

        public void setStudentId(String studentId) {
            this.studentId = studentId;
        }

        public String getStudentId() {
            return studentId;
        }
    }
}
