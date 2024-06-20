package com.example.mypregnancyjourney.reviewdoc;
import com.example.mypregnancyjourney.R;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageViewActivity extends AppCompatActivity {
    private String docPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_image);

        ImageView imageView = findViewById(R.id.imageView);
        docPath = getIntent().getStringExtra("docPath");

        //TODO: replace the image address in assets folder to show
        try {
            // Load the image as an InputStream
            InputStream inputStream = getAssets().open(docPath);
            // Set the InputStream to the ImageView
            imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

