package apiratehat.androidsamplecode.exp.homework;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import apiratehat.androidsamplecode.R;

public class VideoActivity extends AppCompatActivity {
    public static final String URL = "https://vodkgeyttp9.vod.126.net/vodkgeyttp8/fleU2Mdz_2473779161_sd.mp4?wsSecret=a9d74c43d67369e048e5c1f16971328e&wsTime=1556869805&ext=xCD6aZqhJLNNWiTdkcRDFYNh8eZqpXQIB4Hh9EO3B393LwURp6aMcIfXasJBDel%2Ba1zGjUtq8LMJZdbh59bsaBupQtwAzBF19dEN99IJk3gEX4U1k7R3sAuR5frVQ8WteaCl9fXoeK0Z%2FL0erWyP242UKxrAuVYuCS75RyGqxLTqorPmeBKOaN4l75va3y3K2WThHsjZe4gV6yblUAOVZq9XoauVAedovExFhTMIkbfTQE0b6w9Sgn%2BwRxMEvZuSxJyL3oCbh3C6fHcHRDwGM0UM5RXBugwa0NuDNw3pID6iliuCPKw3ZXTtNp%2F5CteFjPDTRu%2Bb6UoEcBIHBIhMx0PuQKA4QCWjwzHzfbywitCF5Vp3k25PCmYNS6PqyHFGcZ0EQxkLYcTM2ZQX4K7Mkk5Fpl18gL2qz%2Fumm7DT9UTMAKJnsH0TuWiUob8pxQiUX3AE6ZrQRtcyshImsz6lr8zMPliX7z62D0DaZyyBsARoUhyFR%2FrrnPFaHAI19vQKNocXhJP8ZD%2BfepTaY77WeRpn7aL70mbtQL6%2BWgKHEoA%3D";
    private ImageView play;
    private VideoView videoView;
    private MediaController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        play = findViewById(R.id.imv_play);
        videoView = findViewById(R.id.video_view);
        controller = new MediaController(this);
        videoView.setMediaController(controller);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

    }

    private void play() {
        if (videoView != null && videoView.isPlaying()) {
            play.setImageResource(android.R.drawable.ic_media_play);
            videoView.stopPlayback();
            return;
        }
        videoView.setVideoPath(URL);
        videoView.start();
        play.setImageResource(android.R.drawable.ic_media_pause);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                play.setImageResource(android.R.drawable.ic_media_play);
            }
        });
    }
}
