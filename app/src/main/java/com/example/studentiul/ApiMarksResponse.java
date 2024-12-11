package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiMarksResponse {
    @SerializedName("marks")
    private List<Mark> marks;

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public static class Mark {
        @SerializedName("CourseID")
        private String CourseID;
        @SerializedName("YearID")
        private String YearID;
        @SerializedName("submitted_at")
        private String time;
        @SerializedName("final_exams_score")
        private String mark;
        @SerializedName("name")
        private String name;

        // Getters and setters for all fields

        public String getCourseID() {
            return CourseID;
        }

        public void setCourseID(String courseID) {
            CourseID = courseID;
        }

        public String getYearID() {
            return YearID;
        }

        public void setYearID(String yearID) {
            YearID = yearID;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
