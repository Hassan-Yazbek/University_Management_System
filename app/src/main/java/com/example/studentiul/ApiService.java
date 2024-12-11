package com.example.studentiul;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<ApiLoginResponse> checkStudentCredentials(@Field("email") String userId, @Field("password") String password);

    @GET("get_news.php")
    Call<List<ApiNewsResponse.NewsItem>> getNews();

    @FormUrlEncoded
    @POST("get_profile.php")
    Call<ApiProfileResponse> getProfile(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_enrollments.php")
    Call<ApiCourseResponse> getEnrollment(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_marks.php")
    Call<ApiMarksResponse> getMark(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_assignments.php")
    Call<ApiAssignmentsResponse> getAssignments(@Field("students_id") String studentId);

    @FormUrlEncoded
    @POST("get_assignments.php")
    Call<ResponseBody> uploadSolution(@Field("students_id") String studentId, @Field("courseName") String course, @Field("solution") String file, @Field("file_path") String extension);

    @FormUrlEncoded
    @POST("get_all_attendance.php")
    Call<ApiAllAttendancesResponse> getAllAttendance(@Field("students_id") String studentId);

    @FormUrlEncoded
    @POST("get_attendance_details.php")
    Call<ApiAttendanceResponse> getAttendance(@Field("students_id") String studentId,@Field("CourseID") String courseID);

    @FormUrlEncoded
    @POST("get_payments.php")
    Call<ApiPaymentResponse> getPayment(@Field("students_id") String userId,@Field("method") String method);

    @FormUrlEncoded
    @POST("get_exams.php")
    Call<ApiExamResponse> getExams(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_schedule.php")
    Call<ApiScheduleResponse> getSchedule(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_lectures.php")
    Call<ApiCourseResponse> getCourseName(@Field("students_id") String userId);

    @FormUrlEncoded
    @POST("get_lectures.php")
    Call<ApiLecturesResponse> getLectures(@Field("course_id") String courseId);

    @FormUrlEncoded
    @POST("forget_password.php")
    Call<ApiResponse> addRequest(@Field("students_id")String userId, @Field("major") String major, @Field("phone_number") String phone_number, @Field("email")String email,@Field("description") String description);

}
