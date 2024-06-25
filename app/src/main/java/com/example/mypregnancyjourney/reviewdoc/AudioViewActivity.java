//package com.example.mypregnancyjourney.reviewdoc;
//
//import com.example.mypregnancyjourney.R;
//
//import android.media.AudioAttributes;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import androidx.appcompat.app.AppCompatActivity;
//import android.util.Log;
//import android.widget.Button;
//
//import java.io.IOException;
//
//public class AudioViewActivity extends AppCompatActivity {
//    private String docPath;
//    private MediaPlayer mediaPlayer;
//    private static final String TAG = "AudioViewActivity";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_review_audio);
//
//        Button playButton = findViewById(R.id.playButton);
//        Button pauseButton = findViewById(R.id.pauseButton);
//
//        docPath = getIntent().getStringExtra("docPath");
//
//        try {
//            // Load the audio file from assets
//            mediaPlayer = new MediaPlayer();
//
//            // Set audio attributes for media playback
//            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
//                    .setUsage(AudioAttributes.USAGE_MEDIA)
//                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//                    .build());
//
//            // Get the AssetFileDescriptor for the asset
//            mediaPlayer.setDataSource(getAssets().openFd(docPath));
//
//            // Prepare the MediaPlayer asynchronously
//            mediaPlayer.prepareAsync();
//
//            // Set up listeners
//            mediaPlayer.setOnPreparedListener(mp -> {
//                playButton.setOnClickListener(v -> {
//                    if (!mediaPlayer.isPlaying()) {
//                        mediaPlayer.start();
//                    }
//                });
//
//                pauseButton.setOnClickListener(v -> {
//                    if (mediaPlayer.isPlaying()) {
//                        mediaPlayer.pause();
//                    }
//                });
//            });
//
//            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
//                Log.e(TAG, "MediaPlayer error: " + what + ", " + extra);
//                return false;
//            });
//
//            mediaPlayer.setOnCompletionListener(mp -> {
//                // Handle completion if needed
//            });
//
//        } catch (IOException e) {
//            Log.e(TAG, "IOException: " + e.getMessage());
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            Log.e(TAG, "IllegalArgumentException: " + e.getMessage());
//            e.printStackTrace();
//        } catch (SecurityException e) {
//            Log.e(TAG, "SecurityException: " + e.getMessage());
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            Log.e(TAG, "IllegalStateException: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (mediaPlayer != null) {
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//}
package com.example.mypregnancyjourney.reviewdoc;

import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.mypregnancyjourney.R;

import java.io.IOException;

public class AudioViewActivity extends AppCompatActivity {
    private static final String TAG = "AudioViewActivity";

    private VideoView videoView;
    private ImageButton playButton;
    private ImageButton pauseButton;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;
    private String docPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_audio);

        videoView = findViewById(R.id.videoView);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);

        docPath = getIntent().getStringExtra("docPath");

        setupMediaPlayer();
        setupMediaController();
    }

    private void setupMediaPlayer() {
        try {
            mediaPlayer = new MediaPlayer();

            // Set audio attributes for media playback (optional but recommended)
            mediaPlayer.setAudioAttributes(new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build());

            // Get AssetFileDescriptor for the asset
            AssetFileDescriptor assetFileDescriptor = getAssets().openFd(docPath);

            // Set data source and prepare MediaPlayer
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
            mediaPlayer.prepare();

            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Playback completed");
            });

        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void setupMediaController() {
        mediaController = new MediaController(this);
        mediaController.setMediaPlayer(new MediaController.MediaPlayerControl() {
            @Override
            public void start() {
                mediaPlayer.start();
            }

            @Override
            public void pause() {
                mediaPlayer.pause();
            }

            @Override
            public int getDuration() {
                return mediaPlayer.getDuration();
            }

            @Override
            public int getCurrentPosition() {
                return mediaPlayer.getCurrentPosition();
            }

            @Override
            public void seekTo(int pos) {
                mediaPlayer.seekTo(pos);
            }

            @Override
            public boolean isPlaying() {
                return mediaPlayer.isPlaying();
            }

            @Override
            public int getBufferPercentage() {
                return 0;
            }

            @Override
            public boolean canPause() {
                return true;
            }

            @Override
            public boolean canSeekBackward() {
                return true;
            }

            @Override
            public boolean canSeekForward() {
                return true;
            }

            @Override
            public int getAudioSessionId() {
                return mediaPlayer.getAudioSessionId();
            }
        });

        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
    }

    public void onPlayButtonClick(View view) {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void onPauseButtonClick(View view) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}

