package com.example.studentiul;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiAllAttendancesResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("attendance")
    private List<Attendance> attendance;

    public String getStatus() {
        return status;
    }

    public List<Attendance> getAttendance() {
        return attendance;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setAttendance(List<Attendance> attendance) {
        this.attendance = attendance;
    }

    public static class Attendance {
        @SerializedName("teacherID")
        private String teacherID;

        @SerializedName("YearID")
        private String yearID;
        @SerializedName("id")
        private String courseId;

        @SerializedName("CourseID")
        private String courseName;

        @SerializedName("presence")
        private int presence;

        @SerializedName("absence")
        private int absence;

        public String getTeacherID() {
            return teacherID;
        }

        public String getYearID() {
            return yearID;
        }

        public String getCourseName() {
            return courseName;
        }

        public int getPresence() {
            return presence;
        }

        public int getAbsence() {
            return absence;
        }

        public void setTeacherID(String teacherID) {
            this.teacherID = teacherID;
        }

        public void setYearID(String yearID) {
            this.yearID = yearID;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public void setPresence(int presence) {
            this.presence = presence;
        }

        public void setAbsence(int absence) {
            this.absence = absence;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getCourseId() {
            return courseId;
        }
    }
}
