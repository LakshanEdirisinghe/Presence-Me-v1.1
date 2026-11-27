package edu.lakshaned.presencemeapp;

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

public class CreateNewQrActivity extends AppCompatActivity {

    private Button btnStartTime, btnEndTime, btnQrExpiry;

    private String startTime;
    private String endTime;
    private String qrExpiryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_create_new_qr);

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