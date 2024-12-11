package com.example.studentiul;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Button registerBtn, loginBtn;
    private EditText email, pass;
    private TextView textError;
    private Intent registerIntent;
    private Intent homePage;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        email = findViewById(R.id.userName);
        pass = findViewById(R.id.password);
        textError = findViewById(R.id.errorView);
        registerIntent = new Intent(this, Request.class);
        homePage = new Intent(this, HomePage.class);
        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(v -> startActivity(registerIntent));

        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(v -> {
            String userId = this.email.getText().toString();
            String password = pass.getText().toString();

            if (userId.isEmpty() || password.isEmpty()) {
                showErrorMessage("Please enter both username and password");
                return;
            }

            apiService = Connection.createApiService();
            checkStudentCredentials(userId, password);
        });
    }

    private void checkStudentCredentials(String userId, String password) {
        Call<ApiLoginResponse> call = apiService.checkStudentCredentials(userId, password);
        call.enqueue(new Callback<ApiLoginResponse>() {
            @Override
            public void onResponse(Call<ApiLoginResponse> call, Response<ApiLoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiLoginResponse apiResponse = response.body();

                    if ("success".equals(apiResponse.getStatus())) {
                        ApiLoginResponse.Login student = apiResponse.getStudent();
                        if (student != null) {
                            String studentName = student.getName();
                            homePage.putExtra("id", student.getStudentId());
                            homePage.putExtra("name", studentName);
                            homePage.putExtra("profile", student.getProfile());
                            startActivity(homePage);
                        } else {
                            showErrorMessage("No student data found.");
                        }
                    } else {
                        showErrorMessage(apiResponse.getMessage());
                    }
                } else {
                    showErrorMessage("Error fetching data: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiLoginResponse> call, Throwable t) {
                Log.e("Network Error", t.getMessage(), t);
                runOnUiThread(() -> {
                    Log.d("UI Thread", "Setting error message on TextView");
                    textError.setVisibility(View.VISIBLE);
                    textError.setText("Username Or Password Not Valid");
                });
            }
        });
    }

    private void showErrorMessage(String message) {
        runOnUiThread(() -> {
            textError.setVisibility(View.VISIBLE);
            textError.setText(message);
            Toast.makeText(Login.this, "Error", Toast.LENGTH_LONG).show();
        });
    }
}


