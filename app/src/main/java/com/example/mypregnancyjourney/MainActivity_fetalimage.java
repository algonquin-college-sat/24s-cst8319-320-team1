package com.example.mypregnancyjourney;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity_fetalimage extends AppCompatActivity {
    private FetalImageDAO fetalImageDAO;
    private EditText weekInput;
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetal_image);

        weekInput = findViewById(R.id.et_week_of_pregnancy);
        imageView = findViewById(R.id.fetal_image_view);
        textView = findViewById(R.id.related_article_text_view);
        Button submitButton = findViewById(R.id.btn_submit);

        // Initialize the database
        FetalImageDatabaseHelper databaseHelper = new FetalImageDatabaseHelper(this);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        // Initialize the FetalImageDAO object
        fetalImageDAO = new FetalImageDAO(database, this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weekStr = weekInput.getText().toString();
                if (!weekStr.isEmpty()) {
                    int week = Integer.parseInt(weekStr);
                    updateFetalImage(week);
                } else {
                    Toast.makeText(MainActivity_fetalimage.this, "Please enter a week number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateFetalImage(int week) {
        Log.d("MainActivity_fetalimage", "updateFetalImage: Getting fetal image for week " + week);
        FetalImage fetalImage = fetalImageDAO.getFetalImage(week);
        if (fetalImage != null) {
            Log.d("MainActivity_fetalimage", "updateFetalImage: Fetal image found");
            imageView.setImageResource(fetalImage.getImageResourceId());
            textView.setText(fetalImage.getRelatedArticleUrl());
        } else {
            Log.d("MainActivity_fetalimage", "updateFetalImage: No fetal image found");
            Toast.makeText(this, "No fetal image found for week " + week, Toast.LENGTH_SHORT).show();
        }
    }
}
