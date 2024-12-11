package com.example.studentiul;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public final class ApiExamResponse {

    @SerializedName("exams")
    private List<Exam> exams;

    public List<Exam> getExams() {
        return exams;
    }

    public static class Exam {

        @SerializedName("date")
        private String date;

        @SerializedName("CourseID")
        private String courseId;

        @SerializedName("name")
        private String name;

        @SerializedName("YearID")
        private String academicYear;

        @SerializedName("room")
        private String room;

        @SerializedName("grade")
        private String grade;

        public Exam() {
            // Default constructor for serialization/deserialization
        }

        public Exam(String date, String courseId, String name, String academicYear, String room, String grade) {
            this.date = date;
            this.courseId = courseId;
            this.name = name;
            this.academicYear = academicYear;
            this.room = room;
            this.grade = grade;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAcademicYear() {
            return academicYear;
        }

        public void setAcademicYear(String academicYear) {
            this.academicYear = academicYear;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }
    }
}
