package com.example.mypregnancyjourney;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class LastSevenDaysActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventsAdapter adapter;
    private EventDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_seven_days);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();  // Close this activity
            }
        });

        dbHelper = new EventDbHelper(this);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
    }

    private void loadEvents() {
        String username = getIntent().getStringExtra("USERNAME"); // Get the username passed from the previous activity
        List<Event> events = dbHelper.getEventsForUserLastSevenDays(username);  // Fetch events for the last 7 days
        adapter = new EventsAdapter(events);
        eventsRecyclerView.setAdapter(adapter);
    }

}
