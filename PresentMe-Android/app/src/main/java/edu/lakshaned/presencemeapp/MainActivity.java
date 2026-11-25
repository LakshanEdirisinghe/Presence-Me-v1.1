package edu.lakshaned.presencemeapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import android.graphics.Color;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private final ExecutorService executorService =
            Executors.newSingleThreadExecutor();

    private static final String BASE_URL =
            "http://192.168.1.2:8080";


    private TextInputEditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            return insets;
        });

        // Initialize Views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvError = findViewById(R.id.tvError);

        btnLogin.setOnClickListener(v -> {

            // Get user inputs
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString();

            // Clear previous error
            tvError.setVisibility(View.GONE);

            // Validate inputs
            if (email.isEmpty() || password.isEmpty()) {

                tvError.setText("Please enter email and password");
                tvError.setVisibility(View.VISIBLE);

                return;
            }

            // Disable button while API request is processing
            btnLogin.setEnabled(false);

            tvError.setText("Signing in...");
            tvError.setVisibility(View.VISIBLE);

// Send login request in background
            executorService.execute(() -> loginUser(email, password));

        });
    }

    private void loginUser(String email, String password) {

        HttpURLConnection connection = null;

        try {

            // Create API URL
            URL url = new URL(BASE_URL + "/api/auth/login");

            connection = (HttpURLConnection) url.openConnection();

            // Configure HTTP request
            connection.setRequestMethod("POST");
            connection.setRequestProperty(
                    "Content-Type", "application/json"
            );
            connection.setRequestProperty(
                    "Accept", "application/json"
            );

            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            // Create JSON request body
            JSONObject jsonRequest = new JSONObject();

            jsonRequest.put("email", email);
            jsonRequest.put("password", password);

            // Send JSON data to backend
            try (OutputStream outputStream = connection.getOutputStream()) {

                outputStream.write(
                        jsonRequest.toString().getBytes("UTF-8")
                );

                outputStream.flush();
            }

            // Get HTTP response code
            int responseCode = connection.getResponseCode();

            // Read response
            InputStream inputStream;

            if (responseCode >= 200 && responseCode < 300) {
                inputStream = connection.getInputStream();
            } else {
                inputStream = connection.getErrorStream();
            }

            StringBuilder response = new StringBuilder();


            if (inputStream != null) {

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream))) {

                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }
            }

            // Convert response into JSON
            JSONObject jsonResponse =
                    new JSONObject(response.toString());
            Log.d("PRESENT_ME_API", "Backend Response: " + jsonResponse);

            boolean success = jsonResponse.optBoolean("success", false);

            String message = jsonResponse.optString(
                    "message", "Something went wrong"
            );

            // Update Android UI
            runOnUiThread(() -> {

                if (success) {

                    String role = jsonResponse.optString("role");
//                    Log.i("Role_Name :",role);

                    tvError.setText(
                            "Login successful! Role: " + role
                    );

                    DashboardLoder.screenLoder(role);

                } else {

                    tvError.setText(message);
                }

                tvError.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(true);
            });

        } catch (Exception e) {

            runOnUiThread(() -> {

                tvError.setText(
                        "Connection error: " + e.getMessage()
                );

                tvError.setVisibility(View.VISIBLE);
                btnLogin.setEnabled(true);
            });

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}