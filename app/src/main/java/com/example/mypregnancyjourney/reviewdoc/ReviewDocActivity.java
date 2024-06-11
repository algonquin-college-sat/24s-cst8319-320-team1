package com.example.mypregnancyjourney.reviewdoc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mypregnancyjourney.R;

public class ReviewDocActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_doc);

        Button pdfViewButton = findViewById(R.id.pdf_view_button);
        Button imageViewButton = findViewById(R.id.image_view_button);
        Button videoViewButton = findViewById(R.id.video_view_button);
        Button audioViewButton = findViewById(R.id.audio_view_button);

        pdfViewButton.setOnClickListener((view) -> {
                Intent intent = new Intent(ReviewDocActivity.this, PdfViewActivity.class);
                startActivity(intent);
        });

        imageViewButton.setOnClickListener((view) -> {
                Intent intent = new Intent(ReviewDocActivity.this, ImageViewActivity.class);
                startActivity(intent);
        });

        videoViewButton.setOnClickListener((view) -> {
                Intent intent = new Intent(ReviewDocActivity.this, VideoViewActivity.class);
                startActivity(intent);
        });

        audioViewButton.setOnClickListener((view) -> {
                Intent intent = new Intent(ReviewDocActivity.this, AudioViewActivity.class);
                startActivity(intent);
        });

    }

}
