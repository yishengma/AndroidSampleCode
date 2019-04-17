package apiratehat.androidsamplecode.lifecycler;

import android.content.res.Configuration;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import apiratehat.androidsamplecode.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "onCreate: " );

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.e(TAG, "onConfigurationChanged: " );
    }
}
// 没有设置时
//切横屏 onDestroy-> onCreate
//切竖屏 onDestroy-> onCreate

//设置  android:configChanges="orientation"
//切横屏 onDestroy-> onCreate
//切竖屏 onDestroy-> onCreate

//设置  android:configChanges="orientation|keyboardHidden"
//切横屏 onDestroy-> onCreate
//切竖屏 onDestroy-> onCreate

//设置  android:configChanges="orientation|screenSize"
//切横屏 onConfigurationChanged
//切竖屏 onConfigurationChanged