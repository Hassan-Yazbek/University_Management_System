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

import java.time.Year;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class AttendanceDetails extends AppCompatActivity {
    private ApiService apiService;
    private TableLayout tableLayout;
    private String userId,courseID;
    private TextView yearA;

    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendancedetails);
        yearA=findViewById(R.id.textView43);
        apiService = Connection.createApiService();
        tableLayout = findViewById(R.id.tableAttendance);

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        courseID=intent.getStringExtra("courseID");
        executorService = Executors.newCachedThreadPool();

        if (userId != null) {
            fetchAttendance(userId);
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAttendance(String userId) {
        Callable<ApiAttendanceResponse> callable = () -> {
            Call<ApiAttendanceResponse> call = apiService.getAttendance(userId,courseID);
            Response<ApiAttendanceResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiAttendanceResponse> future = executorService.submit(callable);
        try {
            ApiAttendanceResponse apiResponse = future.get(); // Blocking call
            if (apiResponse != null && apiResponse.getAttendance() != null && !apiResponse.getAttendance().isEmpty()) {
                populateTable(apiResponse.getAttendance());
            } else {
                runOnUiThread(() -> Toast.makeText(AttendanceDetails.this, "No Attendance found", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(AttendanceDetails.this, "Error fetching attendance: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void populateTable(List<ApiAttendanceResponse.Attendance> attendances) {
        for (ApiAttendanceResponse.Attendance attendance : attendances) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0);

            TextView courseText = createTextView(attendance.getCourseID());
            row.addView(courseText);

            TextView yearText = createTextView(attendance.getYearID());
            row.addView(yearText);
            yearA.setText(attendance.getYearID());
            TextView attendanceDate = createTextView(attendance.getAttendanceDate());
            row.addView(attendanceDate);

            TextView teacher = createTextView(attendance.getTeacherID());
            row.addView(teacher);

            TextView status = createTextView(attendance.getStatus());
            row.addView(status);

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


