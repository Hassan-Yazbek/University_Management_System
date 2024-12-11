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

public class Marks extends AppCompatActivity {
    private ApiService apiService;
    private TableLayout tableLayout;
    private ImageButton previous, next;
    private String userId;
    private ExecutorService executorService;
    private TextView yearM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marks);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        yearM=findViewById(R.id.textView44);
        apiService = Connection.createApiService();
        tableLayout = findViewById(R.id.tableMarks);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        previous = findViewById(R.id.previousEnrollment);
        next = findViewById(R.id.nextAllAttendance);
        Intent exams = new Intent(this, Exams.class);
        Intent assignments = new Intent(this, Assignment.class);

        previous.setOnClickListener(v -> {
            exams.putExtra("id", userId);
            finish();
            startActivity(exams);
        });

        next.setOnClickListener(v -> {
            assignments.putExtra("id", userId);
            finish();
            startActivity(assignments);
        });

        executorService = Executors.newCachedThreadPool();

        if (userId != null) {
            fetchMarks(userId);
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }


    private void fetchMarks(String userId) {
        Callable<ApiMarksResponse> callable = () -> {
            Call<ApiMarksResponse> call = apiService.getMark(userId);
            Response<ApiMarksResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiMarksResponse> future = executorService.submit(callable);
        try {
            ApiMarksResponse apiResponse = future.get(); // Blocking call
            if (apiResponse != null && apiResponse.getMarks() != null && !apiResponse.getMarks().isEmpty()) {
                populateTable(apiResponse.getMarks());
            } else {
                runOnUiThread(() -> Toast.makeText(Marks.this, "Failed to retrieve Exams or no Exams found", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Marks.this, "Error fetching exams: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void populateTable(List<ApiMarksResponse.Mark> marks) {
        for (ApiMarksResponse.Mark mark : marks) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0);
//            row.setBackgroundResource(R.drawable.row);

            TextView code = createTextView(mark.getCourseID());
            row.addView(code);

            TextView course = createTextView(mark.getName());
            row.addView(course);

            TextView dateSubmit = createTextView(mark.getTime());
            row.addView(dateSubmit);

            TextView year = createTextView(mark.getYearID());
            row.addView(year);
            yearM.setText(mark.getYearID());

            TextView finalMark=createTextView(mark.getMark());
            row.addView(finalMark);
            tableLayout.addView(row);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#AA711E"));
        textView.setTextSize(12);
        textView.setPadding(0, 0, 0, 0);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(new TableRow.LayoutParams(0, 120, 1));
        textView.setBackgroundResource(R.drawable.border);
        return textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }

}