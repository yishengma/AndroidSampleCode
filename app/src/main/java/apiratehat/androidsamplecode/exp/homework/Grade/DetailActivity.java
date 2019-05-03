package apiratehat.androidsamplecode.exp.homework.Grade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import apiratehat.androidsamplecode.R;


public class DetailActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        TextView name = findViewById(R.id.tv_name);
        TextView grade = findViewById(R.id.tv_grade);
        TextView classes = findViewById(R.id.tv_class);
       name.setText("名字："+ getIntent().getStringExtra("name"));
        classes.setText("班级："+getIntent().getStringExtra("class"));
        grade.setText("分数:"+getIntent().getStringExtra("grade"));

    }


}