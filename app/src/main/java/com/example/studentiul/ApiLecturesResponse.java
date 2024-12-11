package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiLecturesResponse {
    @SerializedName("lectures")
    private List<Lecture> lectures;

    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }
    // Getters and setters

    public static class Lecture {
        @SerializedName("Lecture_name")
        private String Lecture_name;
        @SerializedName("File_url")
        private String File_url;
        @SerializedName("extension")
        private String extension;

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
        }

        public void setFile_url(String file_url) {
            File_url = file_url;
        }

        public String getFile_url() {
            return File_url;
        }

        public void setLecture_name(String lecture_name) {
            Lecture_name = lecture_name;
        }

        public String getLecture_name() {
            return Lecture_name;
        }
        // Getters and setters
    }
}
