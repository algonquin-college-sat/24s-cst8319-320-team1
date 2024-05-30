package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {

    private EventDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        dbHelper = new EventDbHelper(this); // Initialize the database helper

        // Retrieve the username from the intent
        final String username = getIntent().getStringExtra("USERNAME");
        // Retrieve the date passed from CalendarActivity
        final String date = getIntent().getStringExtra("DATE");
        Log.d("IntentDebug", "Received username: " + username + ", Date: " + date);

        TextView dateView = findViewById(R.id.dateTextView);
        dateView.setText(date);

        final EditText event1 = findViewById(R.id.event1EditText);
        final EditText event2 = findViewById(R.id.event2EditText);
        final EditText event3 = findViewById(R.id.event3EditText);

        loadEventDetails(username, date, event1, event2, event3);


        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Save the events here, validate event1 is not empty
                if (event1.getText().toString().trim().isEmpty()) {
                    event1.setError("Event 1 cannot be blank");
                    return;
                }
                // Save logic goes here
                // Insert the data into the database
                saveEvent(username, date, event1.getText().toString(), event2.getText().toString(), event3.getText().toString());

                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);

                finish(); // Close this activity after save
            }
        });

        Button deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(username, date);
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK, returnIntent);
                finish(); // Close this activity after delete
            }
        });

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Simply close this activity
            }
        });
    }

    private void saveEvent(String username, String date, String event1, String event2, String event3) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.EventEntry.COLUMN_NAME_USERNAME, username);
        values.put(CalendarContract.EventEntry.COLUMN_NAME_DATE, date);
        values.put(CalendarContract.EventEntry.COLUMN_NAME_EVENT1, event1);
        values.put(CalendarContract.EventEntry.COLUMN_NAME_EVENT2, event2);
        values.put(CalendarContract.EventEntry.COLUMN_NAME_EVENT3, event3);

        // Define 'where' part of query.
        String selection = CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " = ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = { username, date };

        // Issue SQL statement.
        int count = db.update(
                CalendarContract.EventEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count > 0) {
            Toast.makeText(this, "Event updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            // If no rows were updated, this means no event was found with the given username and date, so insert a new one
            long newRowId = db.insert(CalendarContract.EventEntry.TABLE_NAME, null, values);
            if (newRowId == -1) {
                Toast.makeText(this, "Error saving event", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Event created successfully", Toast.LENGTH_SHORT).show();
            }
        }

        //db.close();
    }


    private void loadEventDetails(String username, String date, EditText event1, EditText event2, EditText event3) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                CalendarContract.EventEntry.COLUMN_NAME_EVENT1,
                CalendarContract.EventEntry.COLUMN_NAME_EVENT2,
                CalendarContract.EventEntry.COLUMN_NAME_EVENT3
        };
        String selection = CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { username, date };

        Cursor cursor = db.query(
                CalendarContract.EventEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            event1.setText(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.EventEntry.COLUMN_NAME_EVENT1)));
            event2.setText(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.EventEntry.COLUMN_NAME_EVENT2)));
            event3.setText(cursor.getString(cursor.getColumnIndexOrThrow(CalendarContract.EventEntry.COLUMN_NAME_EVENT3)));
        } else {
            // Clear fields if no event exists for the selected date
            event1.setText("");
            event2.setText("");
            event3.setText("");
        }
        cursor.close();
       // db.close();
    }

    private void deleteEvent(String username, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = CalendarContract.EventEntry.COLUMN_NAME_USERNAME + " = ? AND " +
                CalendarContract.EventEntry.COLUMN_NAME_DATE + " = ?";
        String[] selectionArgs = { username, date };
        int deletedRows = db.delete(CalendarContract.EventEntry.TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            Toast.makeText(this, "Event deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No event found to delete", Toast.LENGTH_SHORT).show();
        }

        //db.close();
    }
}
