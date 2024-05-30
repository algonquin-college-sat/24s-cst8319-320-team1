package com.example.mypregnancyjourney;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new UserDbHelper(this);

        EditText usernameField = findViewById(R.id.username);
        EditText emailField = findViewById(R.id.email);
        EditText passwordField = findViewById(R.id.password);
        EditText confirmPasswordField = findViewById(R.id.confirm_password);
        TextView togglePasswordVisibility = findViewById(R.id.toggle_password_visibility);
        TextView toggleConfirmPasswordVisibility = findViewById(R.id.toggle_confirm_password_visibility);
        Button registerButton = findViewById(R.id.register_button);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to MainActivity
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Optional: Close the sign-up activity
            }
        });

        togglePasswordVisibility.setOnClickListener(new View.OnClickListener() {
            boolean isPasswordVisible = false;

            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    togglePasswordVisibility.setText("Show Password");
                } else {
                    passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    togglePasswordVisibility.setText("Hide Password");
                }
                passwordField.setSelection(passwordField.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });

        toggleConfirmPasswordVisibility.setOnClickListener(new View.OnClickListener() {
            boolean isConfirmPasswordVisible = false;

            @Override
            public void onClick(View v) {
                if (isConfirmPasswordVisible) {
                    confirmPasswordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    toggleConfirmPasswordVisibility.setText("Show Confirm Password");
                } else {
                    confirmPasswordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    toggleConfirmPasswordVisibility.setText("Hide Confirm Password");
                }
                confirmPasswordField.setSelection(confirmPasswordField.getText().length());
                isConfirmPasswordVisible = !isConfirmPasswordVisible;
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim().toLowerCase();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                if (!isValidUsername(username)) {
                    Toast.makeText(SignUpActivity.this, "Invalid username. Username cannot be blank or contain spaces.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(SignUpActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidPassword(password)) {
                    Toast.makeText(SignUpActivity.this, "Password must be at least 8 characters long and contain a letter, a digit, and a special character.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignUpActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Proceed with registration logic
                if (dbHelper.isEmailExists(email)) {
                    Toast.makeText(SignUpActivity.this, "Email already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (dbHelper.isUsernameExists(username)) {
                    Toast.makeText(SignUpActivity.this, "Username already exists.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!dbHelper.isEmailExists(email) && !dbHelper.isUsernameExists(username)) {
                    // Add user to the database
                    dbHelper.addUser(username, email, password);

                    // Show success message and redirect to login
                    Toast.makeText(SignUpActivity.this, "Registration successful. Please Log in.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(SignUpActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidUsername(String username) {
        return !username.isEmpty() && !username.contains(" ");
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.matches(".*[a-zA-Z].*") &&
                password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*+=?-].*");
    }


}
