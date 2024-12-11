package com.example.studentiul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class Exams extends AppCompatActivity {

    private ApiService apiService;
    private TableLayout tableLayout;
    private ImageButton previous, next;
    private String userId;
    private ExecutorService executorService;
    private  TextView year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);

        apiService = Connection.createApiService();
        tableLayout = findViewById(R.id.tableExams);
        year=findViewById(R.id.yearExam);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        previous = findViewById(R.id.previousEnrollment);
        next = findViewById(R.id.nextAllAttendance);
        Intent marks = new Intent(this, Marks.class);
        Intent enrollment = new Intent(this, Courses.class);

        previous.setOnClickListener(v -> {
            enrollment.putExtra("id", userId);
            finish();
            startActivity(enrollment);
        });

        next.setOnClickListener(v -> {
            marks.putExtra("id", userId);
            finish();
            startActivity(marks);
        });

        executorService = Executors.newCachedThreadPool();

        if (userId != null) {
            fetchExams(userId);
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchExams(String userId) {
        Callable<ApiExamResponse> callable = () -> {
            Call<ApiExamResponse> call = apiService.getExams(userId);
            Response<ApiExamResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiExamResponse> future = executorService.submit(callable);
        try {
            ApiExamResponse apiResponse = future.get(); // Blocking call
            if (apiResponse != null && apiResponse.getExams() != null && !apiResponse.getExams().isEmpty()) {
                populateTable(apiResponse.getExams());
            } else {
                runOnUiThread(() -> Toast.makeText(Exams.this, "Failed to retrieve Exams or no Exams found", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Exams.this, "Error fetching exams: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void populateTable(List<ApiExamResponse.Exam> exams) {
        for (ApiExamResponse.Exam exam : exams) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0); // No padding to ensure borders align

            TextView date = createTextView(exam.getDate());
            row.addView(date);

            TextView course = createTextView(exam.getCourseId());
            row.addView(course);

            TextView title = createTextView(exam.getName());
            row.addView(title);

            TextView room = createTextView(exam.getRoom());
            row.addView(room);

            if(exam.getGrade()=="null"){
                TextView mark = createTextView("0");
                row.addView(mark);

            }
            else{
                TextView mark = createTextView(exam.getGrade());
                row.addView(mark);

            }

            year.setText(exam.getAcademicYear());

            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#AA711E"));
        textView.setTextSize(12);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 120, 1));
        textView.setBackgroundResource(R.drawable.border); // Set the border
        return textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
