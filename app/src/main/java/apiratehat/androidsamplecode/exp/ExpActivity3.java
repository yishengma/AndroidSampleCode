package apiratehat.androidsamplecode.exp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.exp.ecp3.CheckDataActivity;
import apiratehat.androidsamplecode.exp.ecp3.ClientActivity;
import apiratehat.androidsamplecode.exp.ecp3.MusicActivity;
import apiratehat.androidsamplecode.exp.homework.FrameActivity;
import apiratehat.androidsamplecode.exp.homework.Grade.GradeActivity;
import apiratehat.androidsamplecode.exp.homework.VideoActivity;
import apiratehat.androidsamplecode.exp.homework.WeatherActivity;

public class ExpActivity3 extends AppCompatActivity implements View.OnClickListener {

    private Button mEa;
    private Button mEb;
    private Button mEc;
    private Button mHomeWorka;
    private Button mHomeWorkb;
    private Button mHomeWorkc;
    private Button mHomeWorkd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp3);
        mEa = findViewById(R.id.btn_3a);
        mEb = findViewById(R.id.btn_3b);
        mEc = findViewById(R.id.btn_3c);

        mHomeWorka = findViewById(R.id.btn_a);
        mHomeWorkb = findViewById(R.id.btn_b);
        mHomeWorkc = findViewById(R.id.btn_c);
        mHomeWorkd = findViewById(R.id.btn_d);

        mEa.setOnClickListener(this);
        mEb.setOnClickListener(this);
        mEc.setOnClickListener(this);

        mHomeWorka.setOnClickListener(this);
        mHomeWorkb.setOnClickListener(this);
        mHomeWorkc.setOnClickListener(this);
        mHomeWorkd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_3a:
                Intent intent = new Intent(ExpActivity3.this, ClientActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_3b:
                Intent intent1 = new Intent(ExpActivity3.this, MusicActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_3c:
                Intent intent2 = new Intent(ExpActivity3.this, CheckDataActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_a:
                Intent intent3 = new Intent(ExpActivity3.this, WeatherActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_b:
                Intent intent4 = new Intent(ExpActivity3.this, GradeActivity.class);
                startActivity(intent4);
                break;
            case R.id.btn_c:
                Intent intent5 = new Intent(ExpActivity3.this, VideoActivity.class);
                startActivity(intent5);
                break;
            case R.id.btn_d:
                Intent intent6 = new Intent(ExpActivity3.this, FrameActivity.class);
                startActivity(intent6);
                break;
        }
    }
}
