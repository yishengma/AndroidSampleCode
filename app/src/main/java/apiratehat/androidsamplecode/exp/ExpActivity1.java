package apiratehat.androidsamplecode.exp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import apiratehat.androidsamplecode.R;

public class ExpActivity1 extends AppCompatActivity {
    private static final String TAG = "ExpActivity1";
    private static final int INPUT_ACTIVITY = 1;
    private EditText mEtUserName;
    private EditText mEtPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp1);
        mEtUserName = findViewById(R.id.et_name);
        mEtPsw = findViewById(R.id.et_psw);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpActivity1.this, InputActivity.class);
                startActivityForResult(intent, INPUT_ACTIVITY);
            }
        });

        findViewById(R.id.btn_classmate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpActivity1.this, ClassMateActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_introduce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpActivity1.this, IntroduceActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INPUT_ACTIVITY) {
            mEtUserName.setText(data.getStringExtra("name"));
            mEtPsw.setText(data.getStringExtra("psw"));
        }
    }
}
