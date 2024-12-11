package com.example.studentiul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class PaymentDetails extends AppCompatActivity {

    private TableLayout tableLayout;
    private ApiService apiService;
    private String userId;
    private ExecutorService executorService;
    private TextView yearP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetails);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        yearP=findViewById(R.id.textView42);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");
        tableLayout = findViewById(R.id.tableLayout2);
        apiService = Connection.createApiService();
        executorService = Executors.newCachedThreadPool();

        fetchPayment(userId, "DETAILS");
    }

    private void fetchPayment(String userId, String method) {
        Callable<ApiPaymentResponse> callable = () -> {
            Call<ApiPaymentResponse> call = apiService.getPayment(userId, method);
            Response<ApiPaymentResponse> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                return null;
            }
        };

        Future<ApiPaymentResponse> future = executorService.submit(callable);
        try {
            ApiPaymentResponse apiResponse = future.get(); // Blocking call
            if (apiResponse != null && apiResponse.getPayment() != null && !apiResponse.getPayment().isEmpty()) {
                runOnUiThread(() -> populateTable(apiResponse.getPayment()));
            } else {
                runOnUiThread(() -> Toast.makeText(PaymentDetails.this, "Failed to retrieve payments or no payments found", Toast.LENGTH_SHORT).show());
            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(PaymentDetails.this, "Error fetching payments: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void populateTable(List<ApiPaymentResponse.Payment> payments) {
        for (ApiPaymentResponse.Payment payment : payments) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0);

            TextView method = createTextView(payment.getMethod());
            row.addView(method);

            TextView amount = createTextView(String.valueOf(payment.getPaidAmount()));
            row.addView(amount);

            TextView year = createTextView(payment.getYearID());
            row.addView(year);
            yearP.setText(payment.getYearID());


            TextView date = createTextView(payment.getPaymentDate());
            row.addView(date);

            TextView remaining = createTextView(String.valueOf(payment.getRemainingAmount()));
            row.addView(remaining);

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
