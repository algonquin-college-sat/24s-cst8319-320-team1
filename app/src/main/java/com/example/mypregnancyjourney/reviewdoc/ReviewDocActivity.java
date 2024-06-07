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
        pdfViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewDocActivity.this, PdfViewActivity.class);
                startActivity(intent);
            }
        });

        imageViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReviewDocActivity.this, ImageViewActivity.class);
                startActivity(intent);
            }
        });

    }

}
