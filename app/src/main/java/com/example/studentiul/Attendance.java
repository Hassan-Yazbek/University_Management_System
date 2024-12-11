package com.example.studentiul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class Attendance extends AppCompatActivity {
    private ApiService apiService;
    private TableLayout tableLayout;
    private ImageButton previous, next;
    private String userId;
    private Intent assignments, payment;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_attendance);

        // Handle window insets to adjust view padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        apiService = Connection.createApiService();
        tableLayout = findViewById(R.id.tableAllAttendance);
        executorService = Executors.newSingleThreadExecutor();

        // Retrieve user ID from intent
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        // Initialize buttons and intents for navigation
        previous = findViewById(R.id.previousLecture);
        next = findViewById(R.id.nextAllAttendance);
        assignments = new Intent(this, Assignment.class);
        payment = new Intent(this, Payment.class);

        // Set up button click listeners
        previous.setOnClickListener(v -> {
            assignments.putExtra("id", userId);
            finish();
            startActivity(assignments);
        });

        next.setOnClickListener(v -> {
            payment.putExtra("id", userId);
            finish();
            startActivity(payment);
        });

        // Fetch attendance data if user ID is provided
        if (userId != null) {
            fetchAttendance(userId);
        } else {
            Toast.makeText(this, "User ID not provided", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAttendance(String userId) {
        executorService.execute(() -> {
            try {
                Call<ApiAllAttendancesResponse> call = apiService.getAllAttendance(userId);
                Response<ApiAllAttendancesResponse> response = call.execute();
                runOnUiThread(() -> {
                    if (response.isSuccessful() && response.body() != null && "success".equals(response.body().getStatus())) {
                        populateTable(response.body().getAttendance());
                    } else {
                        Toast.makeText(Attendance.this, "No Attendance found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Attendance.this, "Error fetching attendance", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void populateTable(List<ApiAllAttendancesResponse.Attendance> attendances) {
        for (ApiAllAttendancesResponse.Attendance attendance : attendances) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0);

            TextView courseText = createTextView(attendance.getCourseName());
            row.addView(courseText);

            TextView yearText = createTextView(attendance.getYearID());
            row.addView(yearText);

            TextView teacherText = createTextView(attendance.getTeacherID());
            row.addView(teacherText);

            TextView absenceText = createTextView(String.valueOf(attendance.getAbsence()));
            row.addView(absenceText);

            TextView presenceText = createTextView(String.valueOf(attendance.getPresence()));
            row.addView(presenceText);

            tableLayout.addView(row);

            TableRow buttonRow = new TableRow(this);
            Button viewDetailsButton = new Button(this);
            viewDetailsButton.setText("View Details");
            viewDetailsButton.setTextColor(Color.parseColor("#AA711E"));
            viewDetailsButton.setBackgroundResource(android.R.drawable.alert_light_frame);
            viewDetailsButton.setOnClickListener(v -> {
                Intent details = new Intent(this, AttendanceDetails.class);
                details.putExtra("id", userId);
                details.putExtra("courseID", attendance.getCourseId());
                startActivity(details);
            });

            buttonRow.addView(viewDetailsButton);
            tableLayout.addView(buttonRow);
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
        textView.setBackgroundResource(R.drawable.border);
        return textView;
    }
}
