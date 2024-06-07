package com.example.mypregnancyjourney.reviewdoc;
import com.example.mypregnancyjourney.R;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_image);

        ImageView imageView = findViewById(R.id.imageView);
        //TODO: replace the image to show
        imageView.setImageResource(R.drawable.logo);
    }
}

