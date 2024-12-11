package com.example.studentiul;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class Assignment extends AppCompatActivity {

    private ApiService apiService;
    private TableLayout table;
    private ImageButton previous, next;
    private String userId;
    private TextView year;
    private EditText commentEditText; // For storing the comment
    private ExecutorService executorService;
    private ActivityResultLauncher<String[]> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> filePickerLauncher;
    private static String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);

        // Initialize ApiService
        apiService = Connection.createApiService();

        // Retrieve userId from intent
        Intent intent = getIntent();
        userId = intent.getStringExtra("id");

        // Initialize views
        year = findViewById(R.id.yearA);
        table = findViewById(R.id.tableLayout);
        previous = findViewById(R.id.previousEnrollment);
        next = findViewById(R.id.nextAllAttendance);

        // Initialize ExecutorService
        executorService = Executors.newCachedThreadPool();

        // Set click listeners
        previous.setOnClickListener(v -> navigateToActivity(Marks.class));
        next.setOnClickListener(v -> navigateToActivity(Attendance.class));

        // Initialize permission launcher
        requestPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                this::handlePermissionsResult
        );

        // Initialize file picker launcher
        filePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::handleFilePickerResult
        );

        // Request permissions if not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }

        // Fetch assignments if userId is provided
        if (userId != null) {
            fetchAssignments(userId);
        } else {
            showError("User ID not provided");
            Log.e("Assignment", "User ID not provided");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    // Navigate to the specified activity
    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(Assignment.this, activityClass);
        intent.putExtra("id", userId);
        startActivity(intent);
        finish();
    }

    // Handle permissions result
    private void handlePermissionsResult(Map<String, Boolean> permissions) {
        Boolean readGranted = permissions.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false);
        Boolean writeGranted = permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false);
        if (readGranted != null && writeGranted != null && readGranted && writeGranted) {
        } else {
        }
    }

    // Handle file picker result
    private void handleFilePickerResult(ActivityResult result) {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                handleFileUri(uri);
            }
        }
    }

    // Fetch assignments using ExecutorService
    private void fetchAssignments(String userId) {
        executorService.submit(() -> {
            try {
                Call<ApiAssignmentsResponse> call = apiService.getAssignments(userId);
                Response<ApiAssignmentsResponse> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<ApiAssignmentsResponse.Assignment> assignments = response.body().getAssignments();
                    if (assignments != null && !assignments.isEmpty()) {
                        runOnUiThread(() -> populateTable(assignments));
                    } else {
                        showToast("No assignments found");
                    }
                } else {
                    showToast("Failed to retrieve assignments");
                }
            } catch (Exception e) {
                showError("Error fetching assignments: " + e.getMessage());
            }
        });
    }

    // Populate the table with assignments data
    private void populateTable(List<ApiAssignmentsResponse.Assignment> assignments) {
        for (ApiAssignmentsResponse.Assignment assignment : assignments) {
            LinearLayout row = createTableRow();
            row.addView(createTextView(assignment.getCourseName()));
            row.addView(createTextView(assignment.getTitle()));
            row.addView(createTextView(assignment.getDescription()));
            table.addView(row);

            LinearLayout files = createTableRow();
            year.setText(assignment.getYearId());

            files.addView(createDownloadButton(assignment));
            files.addView(createUploadButton(assignment.getCourseName()));
            table.addView(files);
        }
    }

    // Create a table row
    private LinearLayout createTableRow() {
        LinearLayout row = new LinearLayout(this);
        row.setPadding(0, 50, 0, 50);
        return row;
    }

    // Create a TextView with common styling
    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(Color.parseColor("#AA711E"));
        textView.setTextSize(16);
        textView.setPadding(8, 8, 8, 8);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        return textView;
    }

    // Create a download button
    private Button createDownloadButton(ApiAssignmentsResponse.Assignment assignment) {
        Button download = new Button(this);
        download.setText("Download");
        download.setTextColor(Color.parseColor("#AA711E"));
        download.setBackgroundColor(Color.parseColor("#1e3955"));
        download.setLayoutParams(createButtonLayoutParams());
        download.setOnClickListener(v -> downloadAssignment(assignment));
        return download;
    }

    // Create an upload button
    private Button createUploadButton(String courseName) {
        Button upload = new Button(this);
        upload.setText("Upload");
        upload.setTextColor(Color.parseColor("#AA711E"));
        upload.setBackgroundColor(Color.parseColor("#1e3955"));
        upload.setLayoutParams(createButtonLayoutParams());
        upload.setOnClickListener(v -> {this.course=courseName ; openFilePicker();upload.setVisibility(View.INVISIBLE);});
        return upload;
    }

    // Create button layout params
    private LinearLayout.LayoutParams createButtonLayoutParams() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 16;
        params.rightMargin = 12;
        return params;
    }

    // Download assignment
    private void downloadAssignment(ApiAssignmentsResponse.Assignment assignment) {
        executorService.submit(() -> {
            try {
                byte[] data = Base64.decode(assignment.getAssignment_path(), Base64.DEFAULT);
                String fileName = "assignment_" + assignment.getCourseName() + "." + assignment.getExtension();
                String mimeType = getMimeType(assignment.getExtension());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    saveFileToMediaStore(fileName, data, mimeType);
                }
            } catch (Exception e) {
                showError("Error writing to file: " + e.getMessage());
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void saveFileToMediaStore(String fileName, byte[] data, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = getContentResolver().openOutputStream(uri)) {
                if (outputStream != null) {
                    outputStream.write(data);
                    outputStream.flush();
                    runOnUiThread(() -> Toast.makeText(Assignment.this, "File downloaded: " + fileName, Toast.LENGTH_SHORT).show());
                } else {
                    runOnUiThread(() -> Toast.makeText(Assignment.this, "Failed to open output stream", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                runOnUiThread(() -> Toast.makeText(Assignment.this, "Failed to write file: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("SaveToFile", "Error writing to file", e);
            }
        } else {
            runOnUiThread(() -> Toast.makeText(Assignment.this, "Failed to create new MediaStore record", Toast.LENGTH_SHORT).show());
        }
    }

    // Get MIME type based on file extension
    private String getMimeType(String extension) {
        String mimeType;
        switch (extension.toLowerCase()) {
            case "pdf":
                mimeType = "application/pdf";
                break;
            case "doc":
            case "docx":
                mimeType = "application/msword";
                break;
            case "ppt":
            case "pptx":
                mimeType = "application/vnd.ms-powerpoint";
                break;
            case "xls":
            case "xlsx":
                mimeType = "application/vnd.ms-excel";
                break;
            case "zip":
                mimeType = "application/zip";
                break;
            case "rar":
                mimeType = "application/x-rar-compressed";
                break;
            case "txt":
                mimeType = "text/txt";
                break;
            case "jpg":
            case "jpeg":
                mimeType = "image/jpeg";
                break;
            case "png":
                mimeType = "image/png";
                break;
            default:
                mimeType = "application/octet-stream";
                break;
        }
        return mimeType;
    }

    // Open file picker
    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        filePickerLauncher.launch(intent);
    }

    // Handle file URI and encode it to Base64
    private void handleFileUri(Uri uri) {
        executorService.submit(() -> {
            try (InputStream inputStream = getContentResolver().openInputStream(uri)) {
                if (inputStream != null) {

                    byte[] bytes = getBytes(inputStream);
                    String encoded = Base64.encodeToString(bytes, Base64.DEFAULT);
                    String extension = getFileExtension(uri);
                    uploadFile(encoded, extension);
                } else {
                    showError("Failed to open input stream");
                }
            } catch (IOException e) {
                showError("Failed to read file: " + e.getMessage());
                Log.e("FileEncoding", "Error encoding file", e);
            }
        });
    }

    // Get file extension from URI
    private String getFileExtension(Uri uri) {
        String extension = getContentResolver().getType(uri);
        if (extension != null) {
            extension = extension.substring(extension.lastIndexOf("/") + 1);
        } else {
            extension = "unknown";
        }
        return extension;
    }

    // Upload file
    private void uploadFile(String encodedFile, String extension) {
        executorService.submit(() -> {
            Call<ResponseBody> call = apiService.uploadSolution(userId, course.trim(), encodedFile, extension);
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    showToast("File uploaded successfully"+" "+userId+course+"!1");
                } else {
                    showToast("Failed to upload file");
                }
            } catch (IOException e) {
                showError("Error uploading file: " + e.getMessage());
                Log.e("UploadFile", "Error uploading file", e);
            }
        });
    }

    // Read bytes from InputStream
    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }

        return byteBuffer.toByteArray();
    }

    // Show toast message
    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    // Show error message
    private void showError(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
        Log.e("Assignment", message);
    }
}
