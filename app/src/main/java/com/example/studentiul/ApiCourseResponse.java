package com.example.studentiul;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiCourseResponse {
    private String status;

    @SerializedName("courses")
    private List<Course> courses; // Ensure this matches the JSON response structure

    // Getters and setters

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    // Course class representing individual course details
    public static class Course {
        @SerializedName("name")
        private String courseName;

        @SerializedName("Code")
        private String courseCode;

        @SerializedName("picture_url")
        private String picture_url;

        // Getters and setters

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public void setPicture_url(String picture_url) {
            this.picture_url = picture_url;
        }

        public String getPicture_url() {
            return picture_url;
        }
    }
}
