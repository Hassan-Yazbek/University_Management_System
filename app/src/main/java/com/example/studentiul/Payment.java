package com.example.studentiul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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

public class Payment extends AppCompatActivity {
    private ApiService apiService;
    private ImageButton previous, next;
    private String userId;
    private ExecutorService executorService;
    private Button viewDetails;
    private TableLayout table;
    private TextView yearP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        yearP=findViewById(R.id.yearPayment);
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        previous = findViewById(R.id.previousLecture);
        next = findViewById(R.id.nextLecture);
        Intent attendance = new Intent(this, Attendance.class);
        Intent lectures = new Intent(this, Lectures.class);
        Intent paymentDetails=new Intent(this,PaymentDetails.class);

        previous.setOnClickListener(v -> {
            attendance.putExtra("id", userId);
            finish();
            startActivity(attendance);
        });

        next.setOnClickListener(v -> {
            lectures.putExtra("id", userId);
            finish();
            startActivity(lectures);
        });
        table=findViewById(R.id.tablePayment);
        viewDetails=findViewById(R.id.detailsbtn);
        apiService = Connection.createApiService();
        executorService = Executors.newCachedThreadPool();

        fetchPayment(userId, "OTHER_METHOD");
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentDetails.putExtra("id",userId);
                finish();
                startActivity(paymentDetails);
            }
        });
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
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Alert Payment");
                    builder.setMessage("You should pay to get your courses");

                    AlertDialog dialog = builder.create();
                    dialog.show();
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> Toast.makeText(Payment.this, "Error fetching payments: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        }
    }

    private void populateTable(List<ApiPaymentResponse.Payment> payments) {
        for (ApiPaymentResponse.Payment payment : payments) {
            TableRow row = new TableRow(this);
            row.setPadding(0, 0, 0, 0);

            TextView method = createTextView(payment.getStudents_id());
            row.addView(method);

            TextView year = createTextView(payment.getYearID());
            row.addView(year);
            yearP.setText(payment.getYearID());
            TextView amount = createTextView(String.valueOf(payment.getAnnualAmount()));
            row.addView(amount);

            TextView remain = createTextView(payment.getAnnualRemaining());
            row.addView(remain);

            table.addView(row);
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