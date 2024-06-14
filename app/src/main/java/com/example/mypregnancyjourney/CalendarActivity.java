package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.CalendarView;
//import android.widget.CalendarView.OnDateChangeListener;


import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;


import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeFormatterBuilder;
import org.threeten.bp.format.DateTimeParseException;
import org.threeten.bp.temporal.ChronoField;


import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends Activity {
    private EventDbHelper dbHelper;
    private MaterialCalendarView calendarView;

    private static final int NOTE_ACTIVITY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        dbHelper = new EventDbHelper(this);
        // Retrieve the username passed from WelcomeActivity
        String username = getIntent().getStringExtra("USERNAME");

        // Setup the "Back to Home" button
        Button backHomeButton = findViewById(R.id.backHomeButton);
        backHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start HomeActivity
                Intent intent = new Intent(CalendarActivity.this, WelcomeActivity.class);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
                finish(); // Optionally finish CalendarActivity
            }
        });

        Button nextSevenDaysButton = findViewById(R.id.nextSevenDaysButton);
        nextSevenDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, NextSevenDaysActivity.class);
                intent.putExtra("USERNAME", username);  // Make sure 'username' is the currently logged-in user
                startActivity(intent);
            }
        });

        Button lastSevenDaysButton = findViewById(R.id.lastSevenDaysButton);
        lastSevenDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, LastSevenDaysActivity.class);
                intent.putExtra("USERNAME", username);  // Make sure 'username' is the currently logged-in user
                startActivity(intent);
            }
        });


        calendarView = (MaterialCalendarView) findViewById(R.id.calendar);
        calendarView.addDecorator(new TodayDecorator());
        try {
            markEventDays(username);
        }catch (Exception e){
            e.printStackTrace();
        }


        calendarView.setOnDateChangedListener((widget, date, selected) ->  {


            // String selectedDate = date.getDay() + "/" + (date.getMonth() ) + "/" + date.getYear();
                String selectedDate = date.getYear() + "-" + String.format(Locale.getDefault(), "%02d", date.getMonth()) + "-" + String.format(Locale.getDefault(), "%02d", date.getDay());
                Intent intent = new Intent(CalendarActivity.this, NoteActivity.class);
                intent.putExtra("DATE", selectedDate);
                // Pass the username to CalendarActivity
                intent.putExtra("USERNAME", username);
                Log.d("IntentDebug", "Sending username: " + username + ", Date: " + selectedDate);
                startActivityForResult(intent, NOTE_ACTIVITY_REQUEST_CODE);


            });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String username = getIntent().getStringExtra("USERNAME");
            markEventDays(username);  // Refresh the calendar marks
            calendarView.invalidateDecorators();  // Force the calendar to re-draw the decorators
        }
    }


    private void markEventDays(String username) {
        List<String> eventDates = dbHelper.getEventDatesForUser(username);
        HashSet<CalendarDay> datesWithEvents = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Log.d("CalendarDebug", "Event Dates: " + eventDates); // Check if event dates are fetched

        for (String dateString : eventDates) {
            try {
                LocalDate date = LocalDate.parse(dateString, formatter);
                CalendarDay day = CalendarDay.from(date);
                datesWithEvents.add(day);
                Log.d("CalendarDebug", "Date parsed and added: " + day.toString()); // Log added dates
            } catch (DateTimeParseException e) {
                e.printStackTrace();
                Log.e("CalendarDebug", "Error parsing date: " + dateString, e);
            }
        }

        // Clear all decorators and apply new ones
        calendarView.removeDecorators();  // This line clears all decorators
        calendarView.addDecorator(new TodayDecorator());

        calendarView.addDecorator(new EventDecorator(datesWithEvents));

    }


}
