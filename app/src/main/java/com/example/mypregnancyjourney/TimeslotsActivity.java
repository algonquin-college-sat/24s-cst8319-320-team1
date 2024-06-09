package com.example.mypregnancyjourney;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.List;

public class TimeslotsActivity extends AppCompatActivity{

    private EditText selectedDate;
    private ImageButton calendarIcon;
    private LinearLayout timeslotsContainer;
    private Button backButton, confirmButton;
    private View selectedTimeslotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslots);

        selectedDate = findViewById(R.id.selected_date);
        calendarIcon = findViewById(R.id.calendar_icon);
        timeslotsContainer = findViewById(R.id.timeslots_container);
        backButton = findViewById(R.id.button_back);
        confirmButton = findViewById(R.id.button_confirm);

        Calendar calendar = Calendar.getInstance();
        selectedDate.setText("Today");

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
                        loadAvailableTimeslots();
                    },
                    year, month, day);
            datePickerDialog.show();
        });

        // Initially load today's timeslots
        loadAvailableTimeslots();

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

    private void loadAvailableTimeslots() {
        timeslotsContainer.removeAllViews();
        List<String> availableTimeslots = getAvailableTimeslots();
        selectedTimeslotView = null;

        for (String timeslot : availableTimeslots) {
            TextView timeslotView = new TextView(this);
            timeslotView.setText(timeslot);
            timeslotView.setPadding(16, 16, 16, 16);
            timeslotView.setBackgroundResource(R.drawable.timeslot_background);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,16);
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

    private List<String> getAvailableTimeslots() {
        // Fetch available timeslots from the database
        // This is a placeholder implementation
        return List.of(
                "09:00 AM - 10:00 AM",
                "10:00 AM - 11:00 AM",
                "11:00 AM - 12:00 AM",
                "01:00 PM - 02:00 PM",
                "03:00 PM - 04:00 PM"
        );
    }


}
