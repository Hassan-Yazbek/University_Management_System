package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAssignmentsResponse {
    private String status;
    @SerializedName("assignments")
    private List<Assignment> assignments;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;
    }

    public static class Assignment {
        @SerializedName("CourseId")
        private String CourseName;
        @SerializedName("description")
        private String description;
        @SerializedName("title")
        private String title;
        @SerializedName("YearID")
        private String YearID;
        @SerializedName("assignment_url")
        private String assignment_path;
        @SerializedName("extension")
        private String extension;

        // Getters and setters...
        public String getCourseName() {
            return CourseName;
        }

        public void setCourseName(String courseName) {
            CourseName = courseName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getYearId() {
            return YearID;
        }

        public void setYearId(String yearId) {
            YearID = yearId;
        }

        public void setAssignment_path(String assignment_path) {
            this.assignment_path = assignment_path;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }

        public String getAssignment_path() {
            return assignment_path;
        }
    }
}

