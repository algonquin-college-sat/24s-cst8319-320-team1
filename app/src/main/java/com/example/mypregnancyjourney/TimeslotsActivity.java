package com.example.mypregnancyjourney;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TimeslotsActivity extends AppCompatActivity{

    private EditText selectedDate;
    private ImageButton calendarIcon;
    private LinearLayout timeslotsContainer;
    private Button backButton, confirmButton;
    private View selectedTimeslotView;
    private BookingDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslots);

        selectedDate = findViewById(R.id.selected_date);
        calendarIcon = findViewById(R.id.calendar_icon);
        timeslotsContainer = findViewById(R.id.timeslots_container);
        backButton = findViewById(R.id.button_back);
        confirmButton = findViewById(R.id.button_confirm);

        dbHelper = new BookingDbHelper(this);

        String userEmail = getIntent().getStringExtra("UserEmail");
        String service = getIntent().getStringExtra("Service_selected");
        String clinic = getIntent().getStringExtra("Clinic_selected");
        int serviceId = getIntent().getIntExtra("ServiceId_selected",0);
        int clinicId = getIntent().getIntExtra("ClinicId_selected",0);

        Log.d("TimeslotsActivity", "Service id is "+ serviceId + " ; clinicId is "+ clinicId);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String todayDate = dateFormat.format(calendar.getTime());
        selectedDate.setText(todayDate);

        calendarIcon.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    TimeslotsActivity.this,
                    (view, selectedYear, selectedMonth, selectedDayOfMonth) -> {
                        calendar.set(selectedYear, selectedMonth, selectedDayOfMonth);
                        //Format the date as YYYY-MM-DD
                        String date = selectedYear + "-" + String.format("%02d", (selectedMonth + 1)) + "-" + String.format("%02d", selectedDayOfMonth);
                        selectedDate.setText(date);
                        // Load available timeslots for the selected date
                        loadAvailableTimeslots(serviceId, clinicId, date);
                        Log.d("TimeslotsActivity", "selectedDate is "+ date);
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Load today's timeslots by default
        loadAvailableTimeslots(serviceId, clinicId, todayDate);

        backButton.setOnClickListener(v -> {
            Intent backToBook = new Intent(TimeslotsActivity.this, BookingActivity.class);
            startActivity(backToBook);
        });

        confirmButton.setOnClickListener(v -> {
            if (selectedTimeslotView != null) {
                String selectedTimeslot = ((TextView) selectedTimeslotView).getText().toString();
                Toast.makeText(TimeslotsActivity.this, "Selected Timeslot: " + selectedTimeslot, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TimeslotsActivity.this, "No timeslot selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAvailableTimeslots(int serviceId, int clinicId, String date) {
        if (date == null) {
            Log.e("TimeslotsActivity", "Date is null.");
            return;
        }

        timeslotsContainer.removeAllViews();
        List<String> availableTimeslots = getAvailableTimeslots(serviceId, clinicId, date);
        selectedTimeslotView = null;

        for (String timeslot : availableTimeslots) {
            TextView timeslotView = new TextView(this);
            timeslotView.setText(timeslot);
            timeslotView.setPadding(16, 16, 16, 16);
            timeslotView.setBackgroundResource(R.drawable.timeslot_background);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,20);
            timeslotView.setLayoutParams(layoutParams);

            timeslotView.setOnClickListener(v -> {
                if (selectedTimeslotView !=null) {
                    selectedTimeslotView.setBackgroundResource(R.drawable.timeslot_background);
                }
                timeslotView.setBackgroundResource(R.drawable.timeslot_selected_background);
                selectedTimeslotView = timeslotView;

            });
            timeslotsContainer.addView(timeslotView);
        }
    }

    private List<String> getAvailableTimeslots(int serviceId, int clinicId, String date ) {
        List<String> timeslots = new ArrayList<>();
        Cursor cursor = dbHelper.getTimeslotByServiceClinicIDAndDate(serviceId, clinicId, date);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String timeslot = cursor.getString(cursor.getColumnIndexOrThrow(TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIME));
                    timeslots.add(timeslot);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        Log.d("TimeslotsActivity", "Time found: "+ timeslots);

        return timeslots;
    }
}

