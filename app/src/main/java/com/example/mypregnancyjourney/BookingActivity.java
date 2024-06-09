package com.example.mypregnancyjourney;


import static com.example.mypregnancyjourney.BookingDbHelper.*;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {

    private BookingDbHelper dbHelper;
    private Spinner provinceSpinner, citySpinner, serviceSpinner, clinicSpinner;
    private Button searchBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        dbHelper = new BookingDbHelper(this);

        provinceSpinner = findViewById(R.id.province_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        serviceSpinner = findViewById(R.id.service_spinner);
        clinicSpinner = findViewById(R.id.clinic_spinner);
        searchBtn = findViewById(R.id.search_button);
        backBtn = findViewById(R.id.back_button);

        populateProvinceSpinner();

        provinceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedProvince = provinceSpinner.getSelectedItem().toString();
                populateCitySpinner(selectedProvince);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });

        searchBtn.setOnClickListener(click-> {
                Intent toTimeslots = new Intent(BookingActivity.this, TimeslotsActivity.class);
                startActivity(toTimeslots);
        });

        backBtn.setOnClickListener(click->{
            Intent backtoMain = new Intent(BookingActivity.this,WelcomeActivity.class);
            startActivity(backtoMain);
        });

    }

    private void populateProvinceSpinner() {
        Cursor cursor = dbHelper.getUniqueProvinces();
        ArrayList<String> provinces = new ArrayList<>();
        while (cursor.moveToNext()) {
            provinces.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_CLINIC_PROVINCE)));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        provinceSpinner.setAdapter(adapter);
    }

    private void populateCitySpinner(String province) {
        Cursor cursor = dbHelper.getCitiesByProvince(province);
        ArrayList<String> cities = new ArrayList<>();
        while (cursor.moveToNext()) {
            cities.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_CLINIC_CITY)));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCity = citySpinner.getSelectedItem().toString();
                populateServiceSpinner(province, selectedCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void populateServiceSpinner(String province, String city) {
        Cursor cursor = dbHelper.getServicesByProvinceAndCity(province, city);
        ArrayList<String> services = new ArrayList<>();
        while (cursor.moveToNext()) {
            services.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_SERVICE_NAME)));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(adapter);

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedService = serviceSpinner.getSelectedItem().toString();
                populateClinicSpinner(province, city, selectedService);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void populateClinicSpinner(String province, String city, String service) {
        Cursor cursor = dbHelper.getClinicsByProvinceCityAndService(province, city, service);
        ArrayList<String> clinics = new ArrayList<>();
        while (cursor.moveToNext()) {
            clinics.add(cursor.getString(cursor.getColumnIndexOrThrow(COL_CLINIC_NAME)));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clinics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clinicSpinner.setAdapter(adapter);
    }


}
