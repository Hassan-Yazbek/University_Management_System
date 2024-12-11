package com.example.studentiul;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiScheduleResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("schedule")
    private List<ScheduleItem> schedule;

    public List<ScheduleItem> getSchedule() {
        return schedule;
    }

    public static class ScheduleItem {
        @SerializedName("start_time")
        private String startTime;
        @SerializedName("CourseID")
        private String courseID;
        @SerializedName("class_id")
        private String classID;
        @SerializedName("teacherID")
        private String teacherID;
        @SerializedName("YearID")
        private String yearID;
        @SerializedName("day_of_week")
        private String day;

        // Getters and Setters
        public String getStartTime() { return startTime; }
        public void setStartTime(String startTime) { this.startTime = startTime; }

        public String getCourseID() { return courseID; }
        public void setCourseID(String courseID) { this.courseID = courseID; }

        public String getClassID() { return classID; }
        public void setClassID(String classID) { this.classID = classID; }

        public String getDay() { return day; }
        public void setDay(String day) { this.day = day; }

        public String getTeacherID() { return teacherID; }
        public void setTeacherID(String teacherID) { this.teacherID = teacherID; }

        public String getYearID() { return yearID; }
        public void setYearID(String yearID) { this.yearID = yearID; }

    }
}
