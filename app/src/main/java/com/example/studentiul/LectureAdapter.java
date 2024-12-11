package com.example.studentiul;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LectureAdapter extends BaseAdapter {
    private Context context;
    private List<ApiLecturesResponse.Lecture> lectures;
    private ExecutorService executorService;

    public LectureAdapter(Context context, List<ApiLecturesResponse.Lecture> lectures, ExecutorService executorService) {
        this.context = context;
        this.lectures = lectures;
        this.executorService = executorService;
    }

    @Override
    public int getCount() {
        return lectures.size();
    }

    @Override
    public Object getItem(int position) {
        return lectures.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lecture, parent, false);
        }

        TextView lectureName = convertView.findViewById(R.id.lectureName);
        ImageButton downloadButton = convertView.findViewById(R.id.downloadButton);

        ApiLecturesResponse.Lecture lecture = lectures.get(position);
        lectureName.setText(lecture.getLecture_name());

        downloadButton.setOnClickListener(v -> {
            executorService.submit(() -> {
                try {
                    byte[] decodedBytes = Base64.decode(lecture.getFile_url(), Base64.DEFAULT);
                    String fileName = "lecture_" + position + "." + lecture.getExtension();
                    String mimeType = getMimeType(lecture.getExtension());

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        saveFileToMediaStore(fileName, decodedBytes, mimeType);
                    } else {
                        saveFileLegacy(fileName, decodedBytes);
                    }
                    runOnUiThread(() -> Toast.makeText(context, "File saved as " + fileName, Toast.LENGTH_SHORT).show());
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(context, "Error downloading file: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            });
        });

        return convertView;
    }

    private void runOnUiThread(Runnable action) {
        if (context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).runOnUiThread(action);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void saveFileToMediaStore(String fileName, byte[] data, String mimeType) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
        values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
        values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

        Uri uri = context.getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
        if (uri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                if (outputStream != null) {
                    outputStream.write(data);
                    outputStream.flush();
                } else {
                    runOnUiThread(() -> Toast.makeText(context, "Failed to open output stream", Toast.LENGTH_SHORT).show());
                }
            } catch (IOException e) {
                runOnUiThread(() -> Toast.makeText(context, "Failed to write file: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                Log.e("SaveToFile", "Error writing to file", e);
            }
        } else {
            runOnUiThread(() -> Toast.makeText(context, "Failed to create new MediaStore record", Toast.LENGTH_SHORT).show());
        }
    }

    private void saveFileLegacy(String fileName, byte[] data) {
        try (FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(data);
        } catch (IOException e) {
            runOnUiThread(() -> Toast.makeText(context, "Error saving file: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            Log.e("SaveFileLegacy", "Error saving file", e);
        }
    }

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
                mimeType = "text/plain";
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
}
