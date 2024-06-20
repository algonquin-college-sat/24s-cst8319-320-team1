package com.example.mypregnancyjourney.reviewdoc;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class VideoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String docPath = getIntent().getStringExtra("docPath");

        //TODO: Add something to show video
    }
}
