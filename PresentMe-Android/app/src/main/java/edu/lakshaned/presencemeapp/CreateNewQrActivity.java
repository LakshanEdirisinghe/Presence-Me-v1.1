package edu.lakshaned.presencemeapp;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.TimePickerDialog;
import android.widget.Button;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Locale;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateNewQrActivity extends AppCompatActivity {

    private Button btnStartTime, btnEndTime, btnQrExpiry;

    private String startTime;
    private String endTime;
    private String qrExpiryTime;

    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    private static final String BASE_URL =
            "http://192.168.1.2:8080";

    private String lecturerCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_create_new_qr);

        // Get logged-in user details
        SharedPreferences preferences =
                getSharedPreferences("PresentMePrefs", MODE_PRIVATE);

        String userId = preferences.getString("userId", null);
        String role = preferences.getString("role", null);

        String firstName = preferences.getString("firstName", null);
        String lastName = preferences.getString("lastName", null);

        if (userId != null && "LECTURER".equals(role)) {

            executorService.execute(() -> getLecturerCode(userId));

        } else {

            Toast.makeText(
                    this,
                    "Lecturer information not found",
                    Toast.LENGTH_SHORT
            ).show();
        }

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars()
                    );

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;
                }
        );

        btnStartTime = findViewById(R.id.btnStartTime);
        btnEndTime = findViewById(R.id.btnEndTime);
        btnQrExpiry = findViewById(R.id.btnQrExpiry);

        btnStartTime.setOnClickListener(v -> showTimePicker(btnStartTime, 1));

        btnEndTime.setOnClickListener(v -> showTimePicker(btnEndTime, 2));

        btnQrExpiry.setOnClickListener(v -> showTimePicker(btnQrExpiry, 3));

    }
    private void getLecturerCode(String userId) {

        HttpURLConnection connection = null;

        try {

            URL url = new URL(
                    BASE_URL + "/api/lecturers/" + userId
            );

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {

                InputStream inputStream = connection.getInputStream();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream)
                );

                StringBuilder response = new StringBuilder();

                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                JSONObject jsonResponse =
                        new JSONObject(response.toString());

                lecturerCode = jsonResponse.optString("lecturerCode");

                Log.d("LECTURER_API", "Lecturer Code: " + lecturerCode);

            } else {

                Log.e("LECTURER_API", "Response Code: " + responseCode);

            }

        } catch (Exception e) {

            Log.e("LECTURER_API", "Error: " + e.getMessage());

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    private void showTimePicker(Button button, int type) {

        Calendar calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {

                    String formattedTime = String.format(
                            Locale.getDefault(),
                            "%02d:%02d %s",
                            (selectedHour % 12 == 0) ? 12 : selectedHour % 12,
                            selectedMinute,
                            selectedHour >= 12 ? "PM" : "AM"
                    );

                    button.setText(formattedTime);

                    String time24 = String.format(
                            Locale.getDefault(),
                            "%02d:%02d",
                            selectedHour,
                            selectedMinute
                    );

                    if (type == 1) {
                        startTime = time24;
                    } else if (type == 2) {
                        endTime = time24;
                    } else {
                        qrExpiryTime = time24;
                    }

                },
                hour,
                minute,
                false
        );

        timePickerDialog.show();
    }
}