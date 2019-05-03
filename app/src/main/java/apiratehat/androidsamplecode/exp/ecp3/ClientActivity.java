package apiratehat.androidsamplecode.exp.ecp3;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import apiratehat.androidsamplecode.R;

public class ClientActivity extends AppCompatActivity {

    private ICompareManager mManager;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mManager = ICompareManager.ICompareManagerStub.asInterface(service);
            Log.e("TAG", "NODE");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mManager = null;
        }
    };

    private ServiceConnection mLocalConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (TextUtils.isEmpty(mInputA.getText().toString())
                    || TextUtils.isEmpty(mInputB.getText().toString())) {
                return;
            }

            int result = ((CompareService.Binder) service).compare(Integer.valueOf(mInputA.getText().toString()), Integer.valueOf(mInputB.getText().toString()));
            mTvResult.setText("本地：" + result);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    private TextView mTvResult;
    private Button mBtnRemote;
    private Button mBtnClient;
    private EditText mInputA;
    private EditText mInputB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client2);

        mTvResult = findViewById(R.id.tv_result);
        mBtnClient = findViewById(R.id.btn_client);
        mBtnRemote = findViewById(R.id.btn_remote);

        mInputA = findViewById(R.id.et_a);
        mInputB = findViewById(R.id.et_b);
        final Intent intent = new Intent();
        intent.setPackage("apiratehat.androidsamplecode2");
        intent.setAction("apiratehat.androidsamplecode2.exp.RemoteService");
        bindService(intent, mConnection, BIND_AUTO_CREATE);

        mBtnRemote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mInputA.getText().toString())
                        || TextUtils.isEmpty(mInputB.getText().toString())) {
                    return;
                }
                try {
                    int result = mManager.compare(Integer.valueOf(mInputA.getText().toString()), Integer.valueOf(mInputB.getText().toString()));
                    mTvResult.setText("远程：" + result);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

            }
        });

        mBtnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setPackage("apiratehat.androidsamplecode");
                intent1.setAction("apiratehat.androidsamplecode.exp.CompareService");
                bindService(intent1,mLocalConnection,BIND_AUTO_CREATE);
            }
        });
    }
}

