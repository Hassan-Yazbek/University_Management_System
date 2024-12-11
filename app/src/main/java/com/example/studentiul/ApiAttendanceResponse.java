package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiAttendanceResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("attendance")
    private List<Attendance> attendance;

    // Getter and setter methods for status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Getter and setter methods for attendance list
    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    // Inner class for representing individual attendance records
    public static class Attendance {
        @SerializedName("YearID")
        private String YearID;
        @SerializedName("CourseID")
        private String CourseID;
        @SerializedName("TeacherID")
        private String TeacherID;
        @SerializedName("AttendanceDate")
        private String AttendanceDate;
        @SerializedName("status")
        private String status;

        // Getter and setter methods for YearID
        public String getYearID() {
            return YearID;
        }

        public void setYearID(String yearID) {
            YearID = yearID;
        }

        // Getter and setter methods for CourseID
        public String getCourseID() {
            return CourseID;
        }

        public void setCourseID(String courseID) {
            this.CourseID = courseID;
        }

        // Getter and setter methods for TeacherID
        public String getTeacherID() {
            return TeacherID;
        }

        public void setTeacherID(String teacherID) {
            this.TeacherID = teacherID;
        }

        // Getter and setter methods for AttendanceDate
        public String getAttendanceDate() {
            return AttendanceDate;
        }

        public void setAttendanceDate(String attendanceDate) {
            AttendanceDate = attendanceDate;
        }

        // Getter and setter methods for status
        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
