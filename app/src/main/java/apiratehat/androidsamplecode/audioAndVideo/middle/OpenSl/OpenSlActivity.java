package apiratehat.androidsamplecode.audioAndVideo.middle.OpenSl;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import apiratehat.androidsamplecode.R;

public class OpenSlActivity extends AppCompatActivity {
    private static final String TAG = "OpenSlActivity";
    static {
        System.loadLibrary("opensl");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_sl);

        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
///storage/emulated/0/Downloadmydream.pcm
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                Log.e(TAG, "onClick: "+directory+"/mydream.pcm");
                playAudioByOpenSL_pcm(directory + "/mydream.pcm");
            }
        });
    }

//
//    public native void playAudioByOpenSL_assets(AssetManager assetManager, String filename);

//    public native void playAudioByOpenSL_uri(String uri);

    public native void playAudioByOpenSL_pcm(String pamPath);

//
//    public native void sendPcmData(byte[] data, int size);


}
