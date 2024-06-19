package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    private ProfileDbHelper dbHelper;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbHelper = new ProfileDbHelper(this);

        // Retrieve the username from the intent
        username = getIntent().getStringExtra("USERNAME");

        final EditText editTextName = findViewById(R.id.editTextName);
        final EditText editTextAge = findViewById(R.id.editTextAge);
        final EditText editTextDueDate = findViewById(R.id.editTextDueDate);
        final EditText editTextMedicalHistory = findViewById(R.id.editTextMedicalHistory);
        final EditText editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        Button buttonSaveProfile = findViewById(R.id.buttonSaveProfile);
        Button buttonBack = findViewById(R.id.buttonBack);

        loadProfileDetails(username, editTextName, editTextAge, editTextDueDate, editTextMedicalHistory, editTextEmergencyContact);

        buttonSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String ageString = editTextAge.getText().toString().trim();
                String dueDate = editTextDueDate.getText().toString().trim();
                String medicalHistory = editTextMedicalHistory.getText().toString().trim();
                String emergencyContact = editTextEmergencyContact.getText().toString().trim();

                if (name.isEmpty() || ageString.isEmpty() || dueDate.isEmpty() || medicalHistory.isEmpty() || emergencyContact.isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageString);

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(ProfileContract.ProfileEntry.COLUMN_USERNAME, username);
                values.put(ProfileContract.ProfileEntry.COLUMN_NAME, name);
                values.put(ProfileContract.ProfileEntry.COLUMN_AGE, age);
                values.put(ProfileContract.ProfileEntry.COLUMN_DUE_DATE, dueDate);
                values.put(ProfileContract.ProfileEntry.COLUMN_MEDICAL_HISTORY, medicalHistory);
                values.put(ProfileContract.ProfileEntry.COLUMN_EMERGENCY_CONTACT, emergencyContact);

                // Check if the profile already exists
                long newRowId;
                if (profileExists(username)) {
                    newRowId = db.update(ProfileContract.ProfileEntry.TABLE_NAME, values, ProfileContract.ProfileEntry.COLUMN_USERNAME + "=?", new String[]{username});
                    if (newRowId == -1) {
                        Toast.makeText(ProfileActivity.this, "Error updating profile", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    newRowId = db.insert(ProfileContract.ProfileEntry.TABLE_NAME, null, values);
                    if (newRowId == -1) {
                        Toast.makeText(ProfileActivity.this, "Error saving profile", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Profile saved successfully", Toast.LENGTH_SHORT).show();
                    }
                }
                finish();
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, WelcomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadProfileDetails(String username, EditText name, EditText age, EditText dueDate, EditText medicalHistory, EditText emergencyContact) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                ProfileContract.ProfileEntry.COLUMN_NAME,
                ProfileContract.ProfileEntry.COLUMN_AGE,
                ProfileContract.ProfileEntry.COLUMN_DUE_DATE,
                ProfileContract.ProfileEntry.COLUMN_MEDICAL_HISTORY,
                ProfileContract.ProfileEntry.COLUMN_EMERGENCY_CONTACT
        };
        String selection = ProfileContract.ProfileEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                ProfileContract.ProfileEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            name.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_NAME)));
            age.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_AGE)));
            dueDate.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_DUE_DATE)));
            medicalHistory.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_MEDICAL_HISTORY)));
            emergencyContact.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProfileContract.ProfileEntry.COLUMN_EMERGENCY_CONTACT)));
        } else {
            // Clear fields if no profile exists for the selected username
            name.setText("");
            age.setText("");
            dueDate.setText("");
            medicalHistory.setText("");
            emergencyContact.setText("");
        }
        cursor.close();
    }

    private boolean profileExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = { ProfileContract.ProfileEntry.COLUMN_USERNAME };
        String selection = ProfileContract.ProfileEntry.COLUMN_USERNAME + " = ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                ProfileContract.ProfileEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
