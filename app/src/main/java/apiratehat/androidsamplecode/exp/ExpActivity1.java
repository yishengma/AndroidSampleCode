package apiratehat.androidsamplecode.exp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import apiratehat.androidsamplecode.R;

public class ExpActivity1 extends AppCompatActivity {
    private static final String TAG = "ExpActivity1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp1);
        Log.e(TAG, "onCreate: " );
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: " );
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: " );
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: " );
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e(TAG, "onSaveInstanceState: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }



}
