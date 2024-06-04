package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Retrieve the username from the intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username != null) {
            // Set the welcome message with the username
            TextView welcomeMessage = findViewById(R.id.welcome_message); // Assume you have a TextView for the message
            welcomeMessage.setText("Welcome " + username);
        }

        // Initialize the logout button
        Button logoutButton = findViewById(R.id.logout_button);

        // Set an OnClickListener on the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement any required logout logic here
                // For example, clearing user session data

                // Redirect to MainActivity
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);

                // Optionally, finish this activity
                finish();
            }
        });

        Button calenderBtn = findViewById(R.id.calender_btn);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        // Button for review Account Information
        Button accountInfoBtn = findViewById(R.id.accountinfo_btn);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        // Button for Booking Appointment
        Button bookappointBtn = findViewById(R.id.bookAppoint_btn);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        Button babyimageBtn = findViewById(R.id.babyImage_btn);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        Button revDocBtn = findViewById(R.id.reviewDoc_btn);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

    }
}