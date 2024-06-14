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

        // Retrieve the user email from the intent
        String userEmail = getIntent().getStringExtra("UserEmail");

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

        Button calenderBtn = findViewById(R.id.calendar_btn);
        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CalendarActivity.class); // input your class
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        // Button for review Account Information
        Button accountInfoBtn = findViewById(R.id.accountinfo_btn);
        accountInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        // Button for Booking Appointment
        Button bookappointBtn = findViewById(R.id.bookAppoint_btn);
        bookappointBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, BookingActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                intent.putExtra("UserEmail", userEmail);
                startActivity(intent);
            }
        });

        Button babyimageBtn = findViewById(R.id.babyImage_btn);
        babyimageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CalendarActivity.class); // input your class
                // Pass the username to CalendarActivity
//                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        Button reviewDocBtn = findViewById(R.id.reviewDoc_btn);
        reviewDocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, CalendarActivity.class); // input your class
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

    }
}