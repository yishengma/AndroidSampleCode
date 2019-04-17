package apiratehat.androidsamplecode.exp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.audioAndVideo.junior.audioRecord.AudioActivity;

public class ClassMateActivity extends AppCompatActivity {


    private EditText mEtName;
    private EditText mEtSex;
    private EditText mEtClass;
    private EditText mEtPhone;
    private Button mBtnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_mate);
        if (ContextCompat.checkSelfPermission(ClassMateActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ClassMateActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1);
        }
        mEtName = findViewById(R.id.et_name);
        mEtClass = findViewById(R.id.et_class);
        mEtPhone = findViewById(R.id.et_phone);
        mEtSex = findViewById(R.id.et_sex);
        mBtnSave = findViewById(R.id.btn_save);

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mEtName.getText().toString();
                String classes = mEtClass.getText().toString();
                String sex = mEtSex.getText().toString();
                String phone = mEtPhone.getText().toString();

                JSONObject object = new JSONObject();
                try {
                    object.put("name", name);
                    object.put("classes", sex);
                    object.put("sex", classes);
                    object.put("phone", phone);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                //创建一个文件
                File file = new File(directory + "同学录" + ".txt");
                FileOutputStream fileOutputStream = null;
                try {
                    file.createNewFile();
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(object.toString().getBytes());

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                Toast.makeText(ClassMateActivity.this,"保存成功！",Toast.LENGTH_SHORT).show();
            }


        });


    }
}