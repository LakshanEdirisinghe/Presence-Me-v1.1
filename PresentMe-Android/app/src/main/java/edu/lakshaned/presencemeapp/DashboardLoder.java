package edu.lakshaned.presencemeapp;

import android.content.Context;
import android.content.Intent;

public class DashboardLoder {

    public static void screenLoder(String role, Context currentClz) {

        if (role.equals("STUDENT")) {

            Intent intent = new Intent(
                    currentClz,
                    StudentDashboardActivity.class
            );

            currentClz.startActivity(intent);

        } else if (role.equals("LECTURER")) {

            Intent intent = new Intent(
                    currentClz,
                    LecturerDashboardActivity.class
            );

            currentClz.startActivity(intent);

        }else {

        }
    }
}