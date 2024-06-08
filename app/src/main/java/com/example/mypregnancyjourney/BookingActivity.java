package com.example.mypregnancyjourney;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        EditText btnDatePicker = findViewById(R.id.setDate);

        btnDatePicker.setOnClickListener(v -> {
            //To show current date in the datepicker
            Calendar mcurrentDate=Calendar.getInstance();
            int year = mcurrentDate.get(Calendar.YEAR);
            int month = mcurrentDate.get(Calendar.MONTH);
            int day = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog mDatePicker=new DatePickerDialog(BookingActivity.this, (view,year1, monthOfYear,dayOfMonth)-> {
                String dat = dayOfMonth + " - " + (monthOfYear+1)+" - "+year1;
                btnDatePicker.setText(dat);
            },year, month, day);
            mDatePicker.setTitle("Select date");
            mDatePicker.show();
        });

    }
}
