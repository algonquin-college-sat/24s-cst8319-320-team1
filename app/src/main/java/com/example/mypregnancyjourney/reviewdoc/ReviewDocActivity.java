package com.example.mypregnancyjourney.reviewdoc;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.content.Intent;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mypregnancyjourney.R;
import com.example.mypregnancyjourney.UserDbHelper;

import java.util.ArrayList;

public class ReviewDocActivity extends AppCompatActivity {

    private Spinner dateSpinner, clinicSpinner, typeSpinner;
    private Button viewButton;
    private String selectedDate, selectedClinic, selectedType, selectedDocPath;
    private int selectedClinicId, selectedDocId;
    private UserDbHelper dbHelper = new UserDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_doc);

        dateSpinner = findViewById(R.id.dateSpinner);
        clinicSpinner = findViewById(R.id.clinicSpinner);
        typeSpinner = findViewById(R.id.typeSpinner);
        viewButton = findViewById(R.id.view_button);

        // When populate date spinner, it will automatically populate others
        populateDateSpinner();

        viewButton.setOnClickListener((view) -> {
            Intent intent = null;
            if (selectedType.equalsIgnoreCase("pdf") ){
                intent = new Intent(ReviewDocActivity.this, PdfViewActivity.class);
            } else if (selectedType.equalsIgnoreCase("image")) {
                intent = new Intent(ReviewDocActivity.this, ImageViewActivity.class);
            } else if (selectedType.equalsIgnoreCase("video")) {
                intent = new Intent(ReviewDocActivity.this, VideoViewActivity.class);
            } else if (selectedType.equalsIgnoreCase("audio")) {
                intent = new Intent(ReviewDocActivity.this, AudioViewActivity.class);
            }
            intent.putExtra("docPath", selectedDocPath);
            startActivity(intent);
        });

    }

    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void populateDateSpinner() {
        try (Cursor cursor = dbHelper.getDatesWithDoc()){
            ArrayList<String> datesWithDoc = new ArrayList<>();
            while (cursor.moveToNext()) {
                datesWithDoc.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, datesWithDoc);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            dateSpinner.setAdapter(adapter);
        } catch (Exception e){
            Log.e("ReviewDocActivity", "Error while getDatesWithDoc()", e);
        }

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedDate = dateSpinner.getSelectedItem().toString();
                populateClinicSpinner(selectedDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing here
            }
        });
    }

    private void populateClinicSpinner(String date) {
        try (Cursor cursor = dbHelper.getClinicByDate(date)){
            ArrayList<String> clinics = new ArrayList<>();
            while (cursor.moveToNext()) {
                clinics.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, clinics);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            clinicSpinner.setAdapter(adapter);

            clinicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    selectedClinic = clinicSpinner.getSelectedItem().toString();
                    selectedClinicId = dbHelper.getClinicId(selectedClinic);
                    populateTypeSpinner(date, selectedClinicId);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing here
                }
            });
        }catch (Exception e){
            Log.e("ReviewDocActivity", "Error while getClinicByDate(date)", e);
        }

    }

    private void populateTypeSpinner(String date, int clinic_id) {
        try(Cursor cursor = dbHelper.getTypesByDateAndClinic(date, clinic_id)){
            ArrayList<String> types = new ArrayList<>();
            while (cursor.moveToNext()) {
                types.add(cursor.getString(0));
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            typeSpinner.setAdapter(adapter);

            typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    selectedType = typeSpinner.getSelectedItem().toString();
                    selectedDocId = dbHelper.getDocIdByDateClinicAndType(selectedDate, selectedClinicId, selectedType);
                    selectedDocPath = dbHelper.getDocPathByDocId(selectedDocId); ;
                    Log.d("ReviewDocActivity","selectedDate: " + selectedDate + "; selectedClinic: "+ selectedClinic
                            + "; selectedType: "+ selectedType + "; selectedDocId: "+ selectedDocId
                            + "; selectedDocPath: "+ selectedDocPath );
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    // Do nothing here
                }
            });
        }catch (Exception e){
            Log.e("BookingActivity", "Error while getServicesByProvinceAndCity()", e);
        }
    }

}
