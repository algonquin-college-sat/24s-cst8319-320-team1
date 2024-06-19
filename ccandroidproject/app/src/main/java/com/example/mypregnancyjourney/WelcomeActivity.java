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

        Button buttonFunction1 = findViewById(R.id.buttonFunction1);
        buttonFunction1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CalendarActivity.class);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        Button buttonFunction2 = findViewById(R.id.buttonFunction2);
        buttonFunction2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

    }
}