package com.example.studentiul;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Response;

public class HomePage extends AppCompatActivity {

    private ListView list;
    private ImageButton btnMenu, returnMen;
    private LinearLayout menueLayout, mainLayout, left;
    private ApiService apiService;
    private TextView title, news, fullName;
    private ImageView imageNews, profilePic;
    private CustomAdapter customAdapter;
    private ExecutorService executorService;
    private String userId;

    String[] array = {"Profile", "Courses", "Exams", "Marks", "Assignment", "Attendance", "Payment", "Lectures", "Schedule", "Logout"};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedDispatcher onBackPressedDispatcher = getOnBackPressedDispatcher();
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (checkSomeCondition()) {
                    showConfirmationDialog();
                }
            }
        };
        onBackPressedDispatcher.addCallback(callback);
        profilePic = findViewById(R.id.profile);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String profile = intent.getStringExtra("profile");
        if (profile != null && !profile.isEmpty()) {
            byte[] data = Base64.decode(profile, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            profilePic.setImageBitmap(bitmap);
        }

        list = findViewById(R.id.menue);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listcolor, array);

        // Convert ArrayAdapter to CustomAdapter
        List<String> dataList = Arrays.asList(array);
        customAdapter = new CustomAdapter(this, dataList);

        // Set CustomAdapter to ListView
        list.setAdapter(customAdapter);

        list.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = array[position];
            userId = intent.getStringExtra("id");

            switch (selectedItem) {
                case "Profile":
                    startActivity(new Intent(HomePage.this, Profile.class).putExtra("id", userId));
                    break;
                case "Courses":
                    startActivity(new Intent(HomePage.this, Courses.class).putExtra("id", userId));
                    break;
                case "Exams":
                    startActivity(new Intent(HomePage.this, Exams.class).putExtra("id", userId));
                    break;
                case "Marks":
                    startActivity(new Intent(HomePage.this, Marks.class).putExtra("id", userId));
                    break;
                case "Assignment":
                    startActivity(new Intent(HomePage.this, Assignment.class).putExtra("id", userId));
                    break;
                case "Attendance":
                    startActivity(new Intent(HomePage.this, Attendance.class).putExtra("id", userId));
                    break;
                case "Payment":
                    startActivity(new Intent(HomePage.this, Payment.class).putExtra("id", userId));
                    break;
                case "Schedule":
                    startActivity(new Intent(HomePage.this, Schedule.class).putExtra("id", userId));
                    break;
                case "Lectures":
                    startActivity(new Intent(HomePage.this, Lectures.class).putExtra("id", userId));
                    break;
                case "Logout":
                    showConfirmationDialog();
                    break;
            }
        });

        returnMen = findViewById(R.id.returnMenu);
        menueLayout = findViewById(R.id.layoutM);
        mainLayout = findViewById(R.id.layoutMain);
        left = findViewById(R.id.leftLayout);
        btnMenu = findViewById(R.id.menuBtn);
//        imageNews = findViewById(R.id.imageNews);
        title = findViewById(R.id.newstitle);
        news = findViewById(R.id.newsText);
        fullName = findViewById(R.id.fileProfile);

        if (name.length() > 14) {
            fullName.setTextSize(16);
        }
        fullName.setText(name);
        apiService = Connection.createApiService();

        executorService = Executors.newCachedThreadPool();
        getNews();

        btnMenu.setOnClickListener(v -> toggleMenu());
        returnMen.setOnClickListener(v -> toggleMenu());

        // Animation Part
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        ImageView imageView = findViewById(R.id.animation);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(animation);
        animationSet.addAnimation(animation1);
        animationSet.setFillAfter(true);
        imageView.startAnimation(animationSet);
    }

    private void getNews() {
        executorService.submit(() -> {
            try {
                Call<List<ApiNewsResponse.NewsItem>> call = apiService.getNews();
                Response<List<ApiNewsResponse.NewsItem>> response = call.execute();
                if (response.isSuccessful() && response.body() != null) {
                    List<ApiNewsResponse.NewsItem> newsItems = response.body();
                    runOnUiThread(() -> {
                        if (newsItems != null && !newsItems.isEmpty()) {
                            ApiNewsResponse.NewsItem newsItem = newsItems.get(0);
                            title.setText(newsItem.getTitle());
                            news.setText(newsItem.getArticle());

//                            if (newsItem.getImage() != null && !newsItem.getImage().isEmpty()) {
//                                byte[] data = Base64.decode(newsItem.getImage(), Base64.DEFAULT);
//                                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                imageNews.setImageBitmap(bitmap);
////
//                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "No news found", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    runOnUiThread(() -> {
                        try {
                            String errorResponse = response.errorBody().string();
                            Log.e("API Error", errorResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getApplicationContext(), "Failed to fetch news", Toast.LENGTH_LONG).show();
                    });
                }
            } catch (Exception e) {
                Log.e("API Error", "Error fetching news", e);
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error fetching news", Toast.LENGTH_LONG).show());
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private void toggleMenu() {
        if (left.getVisibility() == View.VISIBLE) {
            left.setVisibility(View.GONE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
            params.weight = 1.0f;
            mainLayout.setLayoutParams(params);
            returnMen.setVisibility(View.VISIBLE);
        } else {
            left.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mainLayout.getLayoutParams();
            params.weight = 3.0f;
            mainLayout.setLayoutParams(params);
            returnMen.setVisibility(View.GONE);
        }
    }

    private boolean checkSomeCondition() {
        return true;
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Logout?");
        builder.setPositiveButton("Yes", (dialog, which) -> logout());
        builder.setNegativeButton("No", (dialog, which) -> {
        });
        builder.show();
    }

    private void logout() {
        clearSharedPreferences();
        clearAppCache();
        clearCookies(this);

        Intent login = new Intent(this, Login.class);
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }

    private void clearSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    private void clearAppCache() {
        try {
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String child : children) {
                boolean success = deleteDir(new File(dir, child));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    private void clearCookies(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
            CookieManager.getInstance().flush();
        } else {
            CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
            cookieSyncMngr.startSync();
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.removeAllCookie();
            cookieManager.removeSessionCookie();
            cookieSyncMngr.stopSync();
            cookieSyncMngr.sync();
        }
    }
}
