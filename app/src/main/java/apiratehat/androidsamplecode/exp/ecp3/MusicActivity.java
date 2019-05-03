package apiratehat.androidsamplecode.exp.ecp3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import apiratehat.androidsamplecode.R;

public class MusicActivity extends AppCompatActivity {

    private TextView mTvPause;
    private TextView mTvStart;
    private  MusicService.MusicBinder musicBinder;
    private ServiceConnection connection  = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicBinder = (MusicService.MusicBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        mTvStart = findViewById(R.id.tv_play);
        mTvPause = findViewById(R.id.tv_pause);
        Intent intent = new Intent(this,MusicService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        Toast.makeText(this,"杨柳千条拂面丝，绿烟金穗不胜吹。\n" +
                "香随静婉歌尘起，影伴娇娆舞袖垂。\n" +
                "羌管一声何处曲，流莺百啭最高枝。\n" +
                "千门九陌花如雪，飞过宫墙两自知。",Toast.LENGTH_SHORT).show();
        mTvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBinder.play();
                mTvStart.setText("正在播放。。");
                mTvPause.setText("暂停");
            }
        });

        mTvPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicBinder.pause();
                mTvStart.setText("播放");
                mTvPause.setText("已暂停");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
