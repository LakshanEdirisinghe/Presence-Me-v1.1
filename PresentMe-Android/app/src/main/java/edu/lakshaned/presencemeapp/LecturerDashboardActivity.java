package edu.lakshaned.presencemeapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LecturerDashboardActivity extends AppCompatActivity {

    private Button btnGenerateNewQR, btnViewQRHistory, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lecturer_dashboard);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    return insets;
                }
        );

        // Initialize Buttons
        btnGenerateNewQR = findViewById(R.id.btnGenerateNewQR);
        btnViewQRHistory = findViewById(R.id.btnViewQRHistory);
        btnLogout = findViewById(R.id.btnLogout);

        // Generate New QR
        btnGenerateNewQR.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LecturerDashboardActivity.this,
                    CreateNewQrActivity.class
            );

            startActivity(intent);
        });

        // View QR History
        btnViewQRHistory.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LecturerDashboardActivity.this,
                    QrHistoryActivity.class
            );

            startActivity(intent);
        });

        // Logout
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(
                    LecturerDashboardActivity.this,
                    MainActivity.class
            );

            // Clear previous activities from back stack
            intent.setFlags(
                    Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
            );

            startActivity(intent);
            finish();
        });
    }
}