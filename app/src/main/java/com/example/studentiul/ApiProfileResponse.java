package com.example.studentiul;

import com.google.gson.annotations.SerializedName;

public class ApiProfileResponse {

    @SerializedName("status")
    private String status;

    @SerializedName("profile")
    private NewProfile profile;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public NewProfile getProfile() {
        return profile;
    }

    public void setProfile(NewProfile profile) {
        this.profile = profile;
    }

    public static class NewProfile {

        @SerializedName("name")
        private String name;

        @SerializedName("students_id")
        private int studentsId;

        @SerializedName("major")
        private String major;

        @SerializedName("email")
        private String email;

        @SerializedName("YearID")
        private String yearID;

        @SerializedName("photo_url")
        private String profilePicture;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStudentsId() {
            return studentsId;
        }

        public void setStudentsId(int studentsId) {
            this.studentsId = studentsId;
        }

        public String getMajor() {
            return major;
        }

        public void setMajor(String major) {
            this.major = major;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getYearID() {
            return yearID;
        }

        public void setYearID(String yearID) {
            this.yearID = yearID;
        }

        public String getProfilePicture() {
            return profilePicture;
        }

        public void setProfilePicture(String profilePicture) {
            this.profilePicture = profilePicture;
        }
    }
}
