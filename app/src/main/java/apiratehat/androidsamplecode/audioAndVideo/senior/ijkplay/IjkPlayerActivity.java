package apiratehat.androidsamplecode.audioAndVideo.senior.ijkplay;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.audioAndVideo.senior.ijkplay.widget.IjkVideoView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class IjkPlayerActivity extends AppCompatActivity {

    private IjkVideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijkplayer);

        initPlayer();
        initView();



    }

    /**
     * 初始化 播放器
     */
    private void initPlayer() {
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

    }

    /**
     * 初始化 View
     */
    private void initView() {
        mVideoView = findViewById(R.id.video_view);
        mVideoView.setVideoURI(Uri.parse("http://vod.cntv.lxdns.com/flash/mp4video61/TMS/2017/08/17/63bf8bcc706a46b58ee5c821edaee661_h264818000nero_aac32-5.mp4"));
        mVideoView.start();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (!mVideoView.isBackgroundPlayEnabled()){
            mVideoView.stopPlayback();
            mVideoView.release(true);
            mVideoView.stopPlayback();
        }else {
            mVideoView.enterBackground();
        }

        //
        IjkMediaPlayer.native_profileEnd();
    }
}
