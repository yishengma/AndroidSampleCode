package apiratehat.androidsamplecode.audioAndVideo.senior.ijkplay;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.audioAndVideo.senior.ijkplay.widget.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayerActivity extends AppCompatActivity {

    private IjkVideoView mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);

        mVideoView = findViewById(R.id.video_view);

        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        mVideoView.setVideoURI(Uri.parse("http://vod.cntv.lxdns.com/flash/mp4video61/TMS/2017/08/17/63bf8bcc706a46b58ee5c821edaee661_h264818000nero_aac32-5.mp4"));
        mVideoView.start();

    }
}
