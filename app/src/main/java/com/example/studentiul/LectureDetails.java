package com.example.studentiul;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class LectureDetails extends AppCompatActivity {
    private String userId;
    private String courseName;
    private ListView filesList;
    private ApiService apiService;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lecture_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        courseName = intent.getStringExtra("course");
        filesList = findViewById(R.id.lectureDetails);
        apiService = Connection.createApiService();
        executorService = Executors.newFixedThreadPool(4); // Use a thread pool with multiple threads

        if (userId != null && courseName != null) {
            fetchLectures();
        } else {
            Toast.makeText(this, "User ID or Course Name not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchLectures() {
        executorService.submit(() -> {
            try {
                Call<ApiLecturesResponse> call = apiService.getLectures(courseName); // Assuming the endpoint expects courseName
                Response<ApiLecturesResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<ApiLecturesResponse.Lecture> lectures = response.body().getLectures();
                    runOnUiThread(() -> {
                        if (lectures != null && !lectures.isEmpty()) {
                            LectureAdapter adapter = new LectureAdapter(LectureDetails.this, lectures, executorService);
                            filesList.setAdapter(adapter);
                        } else {
                            Toast.makeText(LectureDetails.this, "Failed to retrieve lectures or no lectures found", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LectureDetails.this, "Error fetching lectures", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(LectureDetails.this, "Error fetching lectures: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });
    }
}
