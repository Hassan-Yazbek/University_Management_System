package com.example.studentiul;

public class CourseModel {
    private String courseName;
    private String courseId;
    private String path;

    public CourseModel(String courseID,String courseId, String path) {
        this.courseName = courseID;
        this.path = path;
        this.courseId=courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }
}
