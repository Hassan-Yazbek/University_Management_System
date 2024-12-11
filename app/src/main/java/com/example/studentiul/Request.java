package com.example.studentiul;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request extends AppCompatActivity {
    private ApiService apiService;
    private EditText fileNum, mobileNum, email, major,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fileNum = findViewById(R.id.fileProfile);
        mobileNum = findViewById(R.id.phoneMobile);
        email = findViewById(R.id.emailProfile);
        major = findViewById(R.id.majorProfile);
        description=findViewById(R.id.editTextText);

        apiService = Connection.createApiService();

        findViewById(R.id.button).setOnClickListener(v -> {
            String file = fileNum.getText().toString().trim();
            String mobile = mobileNum.getText().toString().trim();
            String mail = email.getText().toString().trim();
            String maj = major.getText().toString().trim();
            String desc=description.getText().toString().trim();

            if (file.isEmpty() || mobile.isEmpty() || mail.isEmpty() || maj.isEmpty()) {
                Toast.makeText(Request.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            } else {
                makeRequest(file, maj, mobile, mail,desc);
            }
        });
    }

    private void makeRequest(String userId, String major, String phone_number, String email ,String description) {
        Call<ApiResponse> call = apiService.addRequest(userId, major, phone_number, email,description);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(Request.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    Log.d("Success", "Request was successful");
                } else {
                    Log.d("Error", "Request failed with code: " + response.code());
                    Toast.makeText(Request.this, "Request failed. Please try again.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d("Failure", "Request failed: " + t.getMessage());
                Toast.makeText(Request.this, "Request failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

