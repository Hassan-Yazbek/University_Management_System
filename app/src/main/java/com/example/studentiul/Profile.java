package com.example.studentiul;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile";
    private ApiService apiService;
    private TextView name, id, email, year, major, full;
    private ImageView image;
    private ImageButton next;
    private int repeatCount = 0;
    private final int maxRepeatCount = 1;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Initialize views and API service
        initializeViews();
        apiService = Connection.createApiService();

        // Fetch profile with the user ID passed in the intent
        Intent intent = getIntent();
        String userId = intent.getStringExtra("id");
        if (userId != null && !userId.isEmpty()) {
            fetchProfile(userId);
        } else {
            Toast.makeText(this, "Invalid User ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        name = findViewById(R.id.fileProfile);
        id = findViewById(R.id.idProfile);
        email = findViewById(R.id.emailProfile);
        year = findViewById(R.id.yearProfile);
        major = findViewById(R.id.majorProfile);
        image = findViewById(R.id.imageProfile);
        full = findViewById(R.id.fullName);
        next = findViewById(R.id.nextAllAttendance);
        Intent enrollment = new Intent(this, Courses.class);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollment.putExtra("id", id.getText().toString());
                finish();
                startActivity(enrollment);
            }
        });
    }

    private void fetchProfile(String userId) {
        executorService = Executors.newCachedThreadPool();

        Callable<ApiProfileResponse> callable = () -> {
            Call<ApiProfileResponse> call = apiService.getProfile(userId);
            Response<ApiProfileResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                Log.e(TAG, "Profile Response failed: " + response.errorBody());
                return null;
            }
        };

        Future<ApiProfileResponse> future = executorService.submit(callable);
        try {
            ApiProfileResponse apiProfileResponse = future.get(); // Blocking call
            if (apiProfileResponse != null && "success".equals(apiProfileResponse.getStatus())) {
                ApiProfileResponse.NewProfile profile = apiProfileResponse.getProfile();
                // Update UI with profile data
                runOnUiThread(() -> {
                    full.setText(profile.getName());
                    name.setText(profile.getName());
                    id.setText(String.valueOf(profile.getStudentsId()));
                    email.setText(profile.getEmail());
                    year.setText(String.valueOf(profile.getYearID()));
                    major.setText(profile.getMajor());

//                    // Decode base64 and set image
                    String base64Image = profile.getProfilePicture();
                    if (base64Image != null && !base64Image.isEmpty()) {
                        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        image.setImageBitmap(decodedByte);
                    }
                });

            } else {
                runOnUiThread(() -> {
                    Log.e(TAG, "Profile Error: No profile data found or status not successful");
                    Toast.makeText(Profile.this, "Profile not found", Toast.LENGTH_SHORT).show();
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> {
                Log.e(TAG, "Profile Error: Exception occurred: " + e.getMessage(), e);
                Toast.makeText(Profile.this, "Failed to fetch profile", Toast.LENGTH_SHORT).show();
            });
        } finally {
            executorService.shutdown();
        }
    }
}
