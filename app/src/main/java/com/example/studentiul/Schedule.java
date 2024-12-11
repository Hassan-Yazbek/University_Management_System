package com.example.studentiul;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class Schedule extends AppCompatActivity {
    private ApiService apiService;
    private LinearLayout mondayLayout, tuesdayLayout, wednesdayLayout, thursdayLayout, fridayLayout, saturdayLayout;
    private ImageButton previous;
    private String userId;
    private ExecutorService executorService;
    private TextView yearTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        previous = findViewById(R.id.previousSchedule);

        Intent lectures = new Intent(this, Lectures.class);

        previous.setOnClickListener(v -> {
            lectures.putExtra("id", userId);
            finish();
            startActivity(lectures);
        });

        mondayLayout = findViewById(R.id.monday);
        tuesdayLayout = findViewById(R.id.tuesday);
        wednesdayLayout = findViewById(R.id.wednesday);
        thursdayLayout = findViewById(R.id.thursday);
        fridayLayout = findViewById(R.id.friday);
        saturdayLayout = findViewById(R.id.saturday);
        yearTxt=findViewById(R.id.year);
        apiService = Connection.createApiService();
        executorService = Executors.newSingleThreadExecutor();

        fetchSchedule(userId);
    }

    private void fetchSchedule(String userId) {
        Callable<ApiScheduleResponse> callable = () -> {
            Call<ApiScheduleResponse> call = apiService.getSchedule(userId);
            Response<ApiScheduleResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiScheduleResponse> future = executorService.submit(callable);
        executorService.execute(() -> {
            try {
                ApiScheduleResponse apiResponse = future.get();
                runOnUiThread(() -> {
                    if (apiResponse != null && apiResponse.getSchedule() != null && !apiResponse.getSchedule().isEmpty()) {
                        populateSchedule(apiResponse.getSchedule());
                    } else {
                        Toast.makeText(Schedule.this, "Failed to retrieve schedule or no schedule found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void populateSchedule(List<ApiScheduleResponse.ScheduleItem> scheduleItems) {
        Map<String, LinearLayout> dayLayoutMap = new HashMap<>();
        dayLayoutMap.put("monday", mondayLayout);
        dayLayoutMap.put("tuesday", tuesdayLayout);
        dayLayoutMap.put("wednesday", wednesdayLayout);
        dayLayoutMap.put("thursday", thursdayLayout);
        dayLayoutMap.put("friday", fridayLayout);
        dayLayoutMap.put("saturday", saturdayLayout);

        for (ApiScheduleResponse.ScheduleItem scheduleItem : scheduleItems) {
            TextView scheduleTextView = createTextView(scheduleItem.getStartTime().substring(0, 5)
                    + "\n" + scheduleItem.getCourseID()
                    + "\nDr. " + scheduleItem.getTeacherID()
                    + "\nRoom: " + scheduleItem.getClassID());
            scheduleTextView.setPadding(0,0,0,27);
            yearTxt.setText(scheduleItem.getYearID());
            LinearLayout dayLayout = dayLayoutMap.get(scheduleItem.getDay().toLowerCase());
            if (dayLayout != null) {
                dayLayout.addView(scheduleTextView);
            } else {
                Log.e("populateSchedule", "Unknown day: " + scheduleItem.getDay());
            }
        }
        // Create a list to hold your ViewGroup objects (assuming these are LinearLayouts or similar)
        List<ViewGroup> rows = new ArrayList<>();
        rows.add(mondayLayout);
        rows.add(tuesdayLayout);
        rows.add(wednesdayLayout);
        rows.add(thursdayLayout);
        rows.add(fridayLayout);
        rows.add(saturdayLayout);

// Iterate through each ViewGroup
        for (ViewGroup row : rows) {
            if (row.getChildCount() == 1) {
                row.setBackgroundColor(Color.parseColor("#7C686C70"));
            }
        }


    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setWidth(320);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#AA711E"));
        textView.setTextSize(10);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        return textView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
