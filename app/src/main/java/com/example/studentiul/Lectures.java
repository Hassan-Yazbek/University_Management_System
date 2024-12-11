package com.example.studentiul;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class Lectures extends AppCompatActivity {
    private ListView lectures;
    private ApiService apiService;
    private String userId;
    private ImageButton previous, next;
    private ExecutorService executorService;
    private List<String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectures);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        lectures = findViewById(R.id.listLectures);
        previous = findViewById(R.id.previousLecture);
        next = findViewById(R.id.nextLecture);
        executorService = Executors.newSingleThreadExecutor();
        courses = new ArrayList<>();

        Intent payment = new Intent(Lectures.this, Payment.class);
        Intent schedule = new Intent(Lectures.this, Schedule.class);

        previous.setOnClickListener(v -> {
            payment.putExtra("id", userId);
            finish();
            startActivity(payment);
        });

        next.setOnClickListener(v -> {
            schedule.putExtra("id", userId);
            finish();
            startActivity(schedule);
        });

        apiService = Connection.createApiService();

        if (userId != null) {
            fetchCourse();
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

    private void fetchCourse() {
        Callable<ApiCourseResponse> callable = () -> {
            Call<ApiCourseResponse> call = apiService.getCourseName(userId);
            Response<ApiCourseResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiCourseResponse> future = executorService.submit(callable);
        try {
            ApiCourseResponse apiResponse = future.get(); // Blocking call
            if (apiResponse != null && apiResponse.getCourses() != null && !apiResponse.getCourses().isEmpty()) {
                runOnUiThread(() -> {
                    for (ApiCourseResponse.Course course : apiResponse.getCourses()) {
                        courses.add(course.getCourseName()); // Assuming `getName` returns the course name
                    }
                    CustomList adapter = new CustomList((Context) this, courses);
                    lectures.setAdapter(adapter);
                    Intent details = new Intent(Lectures.this, LectureDetails.class);
                    lectures.setOnItemClickListener((parent, view, position, id) -> {
                        details.putExtra("id", userId);
                        details.putExtra("course", lectures.getItemAtPosition(position).toString());
                        startActivity(details);
                    });
                });
            } else {
                runOnUiThread(() -> Toast.makeText(Lectures.this, "Failed to retrieve courses or no courses found", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Lectures.this, "Error fetching courses: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }
}
