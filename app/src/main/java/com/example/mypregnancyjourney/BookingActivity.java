package com.example.mypregnancyjourney;



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

    private Spinner provinceSpinner, citySpinner, serviceSpinner, clinicSpinner;
    private Button searchBtn, backBtn;

    private String selectedService, selectedClinic;
    private int selectedService_id , selectedClinic_id;

    private UserDbHelper dbHelper = new UserDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);



        provinceSpinner = findViewById(R.id.province_spinner);
        citySpinner = findViewById(R.id.city_spinner);
        serviceSpinner = findViewById(R.id.service_spinner);
        clinicSpinner = findViewById(R.id.clinic_spinner);
        searchBtn = findViewById(R.id.search_button);
        backBtn = findViewById(R.id.back_button);

        String userName = getIntent().getStringExtra("USERNAME");
        String userEmail = getIntent().getStringExtra("UserEmail");

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
                toTimeslots.putExtra("UserEmail",userEmail);
                toTimeslots.putExtra("USERNAME",userName);
                toTimeslots.putExtra("ServiceId_selected",selectedService_id);
                toTimeslots.putExtra("ClinicId_selected",selectedClinic_id);
                toTimeslots.putExtra("Service_selected",selectedService);
                toTimeslots.putExtra("Clinic_selected", selectedClinic);
                startActivity(toTimeslots);
        });

        backBtn.setOnClickListener(click->{
            Intent backtoMain = new Intent(BookingActivity.this,WelcomeActivity.class);
            startActivity(backtoMain);
            finish();
        });

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void populateProvinceSpinner() {
        try (Cursor cursor = dbHelper.getUniqueProvinces()){
            ArrayList<String> provinces = new ArrayList<>();
            while (cursor.moveToNext()) {
                provinces.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinces);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            provinceSpinner.setAdapter(adapter);
        } catch (Exception e){
            Log.e("BookingActivity", "Error while getUniqueProvinces()", e);
        }

    }

    private void populateCitySpinner(String province) {
        try (Cursor cursor = dbHelper.getCitiesByProvince(province)){
            ArrayList<String> cities = new ArrayList<>();
            while (cursor.moveToNext()) {
                cities.add(cursor.getString(0));
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
        }catch (Exception e){
            Log.e("BookingActivity", "Error while getCitiesByProvince()", e);
        }

    }

    private void populateServiceSpinner(String province, String city) {
        try(Cursor cursor = dbHelper.getServicesByProvinceAndCity(province, city)){
            ArrayList<String> services = new ArrayList<>();
            while (cursor.moveToNext()) {
                services.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, services);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serviceSpinner.setAdapter(adapter);

            serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedService = serviceSpinner.getSelectedItem().toString();
                    selectedService_id = dbHelper.getServiceId(selectedService);
                    Log.d("BookingActivity","selectedService is "+selectedService + "; service id is "+ selectedService_id );
                    populateClinicSpinner(province, city, selectedService);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing here
                }
            });
        }catch (Exception e){
            Log.e("BookingActivity", "Error while getServicesByProvinceAndCity()", e);
        }
    }

    private void populateClinicSpinner(String province, String city, String service) {
        try(Cursor cursor = dbHelper.getClinicsByProvinceCityAndService(province, city, service)){
            ArrayList<String> clinics = new ArrayList<>();
            while (cursor.moveToNext()) {
                clinics.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clinics);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            clinicSpinner.setAdapter(adapter);

            clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedClinic = clinicSpinner.getSelectedItem().toString();
                    selectedClinic_id = dbHelper.getClinicId(selectedClinic);
                    Log.d("BookingActivity","selectedClinic is "+selectedClinic + "; clinic id is "+ selectedClinic_id );
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Do nothing here
                }
            });
        } catch (Exception e){
            Log.e("BookingActivity", "Error while getClinicsByProvinceCityAndService()", e);
        }

    }


}
