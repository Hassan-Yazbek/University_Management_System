package com.example.studentiul;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class Courses extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CourseAdapter courseAdapter;
    private List<CourseModel> courseList;
    private ApiService apiService;
    private String userId;
    private ImageButton previous, next;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        apiService = Connection.createApiService();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        courseList = new ArrayList<>();
        courseAdapter = new CourseAdapter(this, courseList);
        recyclerView.setAdapter(courseAdapter);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        previous = findViewById(R.id.previousEnrollment);
        next = findViewById(R.id.nextAllAttendance);

        previous.setOnClickListener(v -> {
            Intent profile = new Intent(Courses.this, Profile.class);
            profile.putExtra("id", userId);
            finish();
            startActivity(profile);
        });

        next.setOnClickListener(v -> {
            Intent exams = new Intent(Courses.this, Exams.class);
            exams.putExtra("id", userId);
            finish();
            startActivity(exams);
        });

        executorService = Executors.newCachedThreadPool();

        if (userId != null) {
            fetchCourses(userId);
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    // Method to fetch courses using ExecutorService
    private void fetchCourses(String userId) {
        executorService.submit(() -> {
            try {
                Call<ApiCourseResponse> call = apiService.getEnrollment(userId);
                Response<ApiCourseResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    ApiCourseResponse apiResponse = response.body();
                    List<ApiCourseResponse.Course> courses = apiResponse.getCourses();
                    runOnUiThread(() -> {
                        courseList.clear();
                        if (courses != null && !courses.isEmpty()) {
                            for (ApiCourseResponse.Course course : courses) {
                                courseList.add(new CourseModel(course.getCourseName(), course.getCourseCode(), course.getPicture_url()));
                            }
                            courseAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Courses.this, "No courses found", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(Courses.this, "Failed to fetch courses", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Courses.this, "Error fetching courses: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}
