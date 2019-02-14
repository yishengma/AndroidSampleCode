package apiratehat.androidsamplecode.sp;

import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.LinkedList;
import java.util.prefs.Preferences;

import apiratehat.androidsamplecode.R;

public class SpActivity extends AppCompatActivity {
    private static final String TAG = "SpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        //获取 Sp
//                getSharedPreferences();
//                getPreferences()
        //         PreferenceManager.getDefaultSharedPreferences()

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:get " + SpUtil.get(SpActivity.this));
            }
        });
        findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil.saveApply("apply", SpActivity.this);
                Log.e(TAG, "onClick: apply");
            }
        });

        findViewById(R.id.btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: commit" + SpUtil.saveCommit("commit", SpActivity.this));
            }
        });
        Log.e(TAG, "onCreate: "+Thread.currentThread());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: "+Thread.currentThread());
            }
        };
        final LinkedList<Runnable> sWork = new LinkedList<>();
        sWork.add(runnable);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "run: "+Thread.currentThread());
                LinkedList<Runnable> w = (LinkedList<Runnable>) sWork.clone();
                Runnable runnable1 = w.poll();
                runnable1.run();

            }
        }).start();
    }


}
//点击 get
//androidsamplecode E/SpActivity: onClick:get


//点击 apply
//androidsamplecode E/SpActivity: onClick: apply
//点击 commit
//androidsamplecode E/SpActivity: onClick: commit true

//SharedPreferences数据总是保存在/data/data/<package name>/shared_prefs目录下
//在真机上需要 root 权限