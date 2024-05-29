package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class NextSevenDaysActivity extends AppCompatActivity {

    private RecyclerView eventsRecyclerView;
    private EventsAdapter adapter;
    private EventDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_seven_days);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finishing the activity will navigate the user back to the previous activity
                finish();
            }
        });

        dbHelper = new EventDbHelper(this);
        eventsRecyclerView = findViewById(R.id.eventsRecyclerView);
        eventsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadEvents();
    }

    private void loadEvents() {
        String username = getIntent().getStringExtra("USERNAME"); // Assuming username is passed from the previous activity
        List<Event> events = dbHelper.getEventsForUserNextSevenDays(username);
        adapter = new EventsAdapter(events);
        eventsRecyclerView.setAdapter(adapter);
    }

}
