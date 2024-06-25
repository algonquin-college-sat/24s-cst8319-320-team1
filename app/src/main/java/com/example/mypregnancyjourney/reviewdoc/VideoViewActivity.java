package com.example.mypregnancyjourney.reviewdoc;

import com.example.mypregnancyjourney.R;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class VideoViewActivity extends AppCompatActivity {
    private String docPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_video);

        VideoView videoView = findViewById(R.id.videoView);
        docPath = getIntent().getStringExtra("docPath");

        try {
            // Copy video from assets to a local file
            File videoFile = copyAssetToFile(docPath);

            if (videoFile != null) {
                // Get the URI of the video file in the local storage
                Uri videoUri = Uri.fromFile(videoFile);

                // Set the URI to the VideoView
                videoView.setVideoURI(videoUri);

                // Add media controls
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);

                // Start playing the video
                videoView.setOnPreparedListener(mp -> videoView.start());
            } else {
                throw new IOException("Failed to copy video file from assets.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File copyAssetToFile(String assetPath) throws IOException {
        File outFile = new File(getCacheDir(), assetPath);
        try (InputStream inputStream = getAssets().open(assetPath);
             FileOutputStream outputStream = new FileOutputStream(outFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
        return outFile;
    }
}
