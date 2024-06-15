package com.example.mypregnancyjourney;



import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialize fields
        EditText emailField = findViewById(R.id.email);
        EditText passwordField = findViewById(R.id.password);
        TextView togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);
        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide the password
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setText("Show Password");
                } else {
                    // Show the password
                    passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setText("Hide Password");
                }

                // Move cursor to the end of the text
                passwordField.setSelection(passwordField.getText().length());

                // Toggle the boolean flag
                isPasswordVisible = !isPasswordVisible;
            }
        });

        Button loginButton = findViewById(R.id.login_button);
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to go back to MainActivity
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close the login activity
            }
        });


        // Initialize the database helper
        UserDbHelper dbHelper = new UserDbHelper(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim().toLowerCase();
                String password = passwordField.getText().toString();

                if (dbHelper.checkUser(email, password)) {
                    String username = dbHelper.getUsername(email);
                    // User exists and password matches. Proceed with login.
                    Intent intent = new Intent(LogInActivity.this, WelcomeActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("UserEmail",email);
                    startActivity(intent);
                    finish(); // Optional: Close the login activity
                } else {
                    // User doesn't exist or password doesn't match. Show error message.
                    Toast.makeText(LogInActivity.this, "Email doesn't exist or password is not correct", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView forgotPassword = findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, ResetPasswordActivity.class);
                startActivity(intent);
            }
        });



    }
}