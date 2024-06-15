package com.example.mypregnancyjourney;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
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
    private int selectedTimeslotId = -1;
    private String selectedTimeslot;
    private UserDbHelper dbHelper = new UserDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslots);

        selectedDate = findViewById(R.id.selected_date);
        calendarIcon = findViewById(R.id.calendar_icon);
        timeslotsContainer = findViewById(R.id.timeslots_container);
        backButton = findViewById(R.id.button_back);
        confirmButton = findViewById(R.id.button_confirm);

        String userName = getIntent().getStringExtra("USERNAME");
        String userEmail = getIntent().getStringExtra("UserEmail");
        String service = getIntent().getStringExtra("Service_selected");
        String clinic = getIntent().getStringExtra("Clinic_selected");
        int serviceId = getIntent().getIntExtra("ServiceId_selected",0);
        int clinicId = getIntent().getIntExtra("ClinicId_selected",0);

        String clinicEmail = dbHelper.getClinicEmail(clinicId);

        Log.d("TimeslotsActivity", "Service id: "+ serviceId + " ; Service: "+ service+
                " ; UserName is "+userName+" ; UserEmail is "+userEmail+" ; Clinic id: "+clinicId+" ; Clinic: "+clinic);


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
            if(selectedTimeslotId !=-1){
                if(!dbHelper.isTimeslotBooked(selectedTimeslotId)){
                    dbHelper.bookTimeslot(selectedTimeslotId);
                    Toast.makeText(TimeslotsActivity.this, "Timeslot booked successfully.", Toast.LENGTH_SHORT).show();
                    loadAvailableTimeslots(serviceId, clinicId, selectedDate.getText().toString()); // Refresh the timeslots view

                    Log.w("TimeslotsActivity Variable", "Service id: "+ serviceId + " ; Service: "+ service+
                            " ; UserName is "+userName+" ; UserEmail is "+userEmail+" ; Clinic id: "+clinicId+" ; Clinic: "+clinic +
                            " ; Selected Date: "+ selectedDate.getText().toString()+" ; Select time: "+ selectedTimeslot);

                    // Send email confirmation
                    String userSubject = "Appointment Confirmation";
                    String clinicSubject = "Appointment Confirmation with "+userName;
                    String userMsg = "Dear "+userName+", \n\n"+
                            "Your appointment for " + service + " at " + clinic + " on " + selectedDate.getText().toString() + " has been confirmed.\n\n"
                            +"We look forward to seeing you! Thank you.";
                    String clinicMsg = "Dear Team,\n\n"
                            + "We are pleased to inform you that a new appointment has been successfully booked with our clinic. Below are the details:\n\n"
                            + "- Client Name: "+userName
                            + "\n- Service: "+service
                            + "\n- Date: " + selectedDate.getText().toString()
                            + "\n- Time: "+selectedTimeslot
                            + "\n\nPlease ensure all preparations are made for the appointment.\n\n"
                            + "Thank you.";

                    sendEmail(userEmail, userSubject, userMsg);
                    sendEmail(clinicEmail, clinicSubject, clinicMsg); // Clinic email

                }else {
                    Toast.makeText(TimeslotsActivity.this, "Timeslot is already booked. Please select another.", Toast.LENGTH_LONG).show();
                }
            }
            else {
                    Toast.makeText(TimeslotsActivity.this, "Please select a timeslot.", Toast.LENGTH_LONG).show();
                }
//            if (selectedTimeslotView != null) {
//                String selectedTimeslot = ((TextView) selectedTimeslotView).getText().toString();
//                Toast.makeText(TimeslotsActivity.this, "Selected Timeslot: " + selectedTimeslot, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(TimeslotsActivity.this, "No timeslot selected", Toast.LENGTH_SHORT).show();
//            }
        });
    }

    private void loadAvailableTimeslots(int serviceId, int clinicId, String date) {
        if (date == null) {
            Log.e("TimeslotsActivity", "Date is null.");
            return;
        }
        timeslotsContainer.removeAllViews();

        List<Timeslot> availableTimeslots = dbHelper.getAllTimeslots(serviceId, clinicId, date);

        for(final Timeslot timeslot: availableTimeslots){
            Button timeslotButton =new Button(this);
            timeslotButton.setText(timeslot.getTime());
            timeslotButton.setEnabled(!timeslot.getIsBooked());// Disable if already booked

            timeslotButton.setOnClickListener(v->{
                selectedTimeslotId = timeslot.getId();
                selectedTimeslot = timeslot.getTime();
                highlightSelectedButton((Button) v); // Highlight the selected button
            });

            timeslotsContainer.addView(timeslotButton);
        }

//        selectedTimeslotView = null;

//        for (String timeslot : availableTimeslots) {
//            TextView timeslotView = new TextView(this);
//            timeslotView.setText(timeslot);
//            timeslotView.setPadding(16, 16, 16, 16);
//            timeslotView.setBackgroundResource(R.drawable.timeslot_background);
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            layoutParams.setMargins(0,0,0,20);
//            timeslotView.setLayoutParams(layoutParams);
//
//            timeslotView.setOnClickListener(v -> {
//                if (selectedTimeslotView !=null) {
//                    selectedTimeslotView.setBackgroundResource(R.drawable.timeslot_background);
//                }
//                timeslotView.setBackgroundResource(R.drawable.timeslot_selected_background);
//                selectedTimeslotView = timeslotView;
//
//            });
//            timeslotsContainer.addView(timeslotView);
//        }
    }

    private void highlightSelectedButton(Button selectedButton) {
        // Reset all button backgrounds to default
        for (int i = 0; i < timeslotsContainer.getChildCount(); i++) {
            View view = timeslotsContainer.getChildAt(i);
            if (view instanceof Button) {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        // Highlight the selected button
        selectedButton.setBackgroundColor(Color.LTGRAY);
    }


    private void sendEmail(String email, String subject, String message) {
        Log.d("TimeslotsActivity", "Sending email to: " + email);
        JavaMailAPI javaMailAPI = new JavaMailAPI(email, subject, message);
        javaMailAPI.execute();
    }
}

//    private List<String> getAvailableTimeslots(int serviceId, int clinicId, String date ) {
//        List<String> timeslots = new ArrayList<>();
//        Cursor cursor = dbHelper.getTimeslotByServiceClinicIDAndDate(serviceId, clinicId, date);
//        if (cursor != null) {
//            if (cursor.moveToFirst()) {
//                do {
//                    String timeslot = cursor.getString(cursor.getColumnIndexOrThrow(TimeslotsContract.TimeslotEntry.COLUMN_NAME_TIME));
//                    timeslots.add(timeslot);
//                } while (cursor.moveToNext());
//            }
//            cursor.close();
//        }
//        Log.d("TimeslotsActivity", "Time found: "+ timeslots);
//
//        return timeslots;
//    }


