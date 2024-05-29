package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private UserDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        dbHelper = new UserDbHelper(this);

        EditText emailField = findViewById(R.id.email);
        EditText newPasswordField = findViewById(R.id.new_password);
        EditText confirmNewPasswordField = findViewById(R.id.confirm_new_password);
        Button resetPasswordButton = findViewById(R.id.reset_password_button);
        TextView toggleNewPasswordVisibility = findViewById(R.id.toggle_new_password_visibility);
        TextView toggleConfirmNewPasswordVisibility = findViewById(R.id.toggle_confirm_new_password_visibility);
        Button backButton = findViewById(R.id.back_button);

        toggleNewPasswordVisibility.setOnClickListener(v -> togglePasswordVisibility(newPasswordField, toggleNewPasswordVisibility));
        toggleConfirmNewPasswordVisibility.setOnClickListener(v -> togglePasswordVisibility(confirmNewPasswordField, toggleConfirmNewPasswordVisibility));

        backButton.setOnClickListener(v -> {
            // Navigate back to LoginActivity
            Intent intent = new Intent(ResetPasswordActivity.this, LogInActivity.class);
            startActivity(intent);
            finish();
        });

        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle reset password logic
                String email = emailField.getText().toString().trim().toLowerCase();
                String password = newPasswordField.getText().toString();
                String confirmPassword = confirmNewPasswordField.getText().toString();

                if (!isValidEmail(email)) {
                    Toast.makeText(ResetPasswordActivity.this, "Invalid email format.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(password)) {
                    Toast.makeText(ResetPasswordActivity.this, "Password must be at least 8 characters long and include a letter, a digit, and a special character.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!doPasswordsMatch(password, confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Proceed with resetting the password
                if (dbHelper.isEmailExists(email)) {
                    if (password.equals(confirmPassword)) {
                        // Update password in the database
                        dbHelper.updateUserPassword(email, password); // Ensure password is hashed
                        Toast.makeText(ResetPasswordActivity.this, "Password reset successfully.", Toast.LENGTH_SHORT).show();

                        // Redirect to the login page
                        Intent intent = new Intent(ResetPasswordActivity.this, LogInActivity.class);
                        startActivity(intent);
                        finish(); // Close the current activity
                    } else {
                        Toast.makeText(ResetPasswordActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Email does not exist. Please check your email or sign up.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void togglePasswordVisibility(EditText passwordField, TextView toggleTextView) {
        if (passwordField.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            passwordField.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            toggleTextView.setText("Hide Password");
        } else {
            passwordField.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            toggleTextView.setText("Show Password");
        }
        passwordField.setSelection(passwordField.getText().length());
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8
                && password.matches(".*[a-zA-Z].*") // Contains a letter
                && password.matches(".*\\d.*") // Contains a digit
                && password.matches(".*[!@#$%^&*+=?-].*"); // Contains a special character
    }

    private boolean doPasswordsMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }
}
