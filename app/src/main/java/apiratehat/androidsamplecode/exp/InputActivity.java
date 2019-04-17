package apiratehat.androidsamplecode.exp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;

import apiratehat.androidsamplecode.R;

public class InputActivity extends AppCompatActivity {

    private EditText mEtUserName;
    private EditText mEtPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        mEtUserName = findViewById(R.id.et_name);
        mEtPsw = findViewById(R.id.et_psw);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.putExtra("name",mEtUserName.getText().toString());
            intent.putExtra("psw",mEtPsw.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

}
