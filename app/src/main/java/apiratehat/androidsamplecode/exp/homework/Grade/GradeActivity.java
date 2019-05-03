package apiratehat.androidsamplecode.exp.homework.Grade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import apiratehat.androidsamplecode.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


public class GradeActivity extends AppCompatActivity {
    // 登录界面所填写的用户名和密码

    private EditText loginName, loginClass;

    private String name, classe, grades;

    private EditText etName;

    private EditText etClass;

    private EditText etGrade;

    private String existName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);
        loginName = (EditText) findViewById(R.id.et_name);
        loginClass = (EditText) findViewById(R.id.et_class);

    }
    // 登录事件


    public void login(View view) {
        String name = loginName.getText().toString().trim();
        String classes = loginClass.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(classes)) {
            Toast.makeText(this, "选项不能为空", Toast.LENGTH_SHORT).show();
            return;

        }
        String classed = GradeInfoManager.getInstance(this).findData(name,
                GradeInfoManager.CLASS);
        if (classes.equals(classed)) {
            String grade = GradeInfoManager.getInstance(this).findData(name,
                    GradeInfoManager.GRADE);
            // 打开新的应用，使用Intent把姓名和电话传递到SecondActivity界面
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("grade", grade);
            intent.putExtra("class",classed);
            startActivity(intent);

        } else {
            Toast.makeText(this, "该学生不存在，请先登记", Toast.LENGTH_SHORT).show();

        }

    }
    // 注册对话框


    public void register(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        view = View.inflate(this, R.layout.regist_dialog, null);
        builder.setView(view);
        etName = (EditText) view.findViewById(R.id.editTxt_username_regist);
        etClass = (EditText) view.findViewById(R.id.editTxt_phone_regist);
        etGrade = (EditText) view.findViewById(R.id.editTxt_psw_regist);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override


            public void onClick(DialogInterface dialog, int which) {
                name = etName.getText().toString().trim();
                classe = etClass.getText().toString().trim();
                grades = etGrade.getText().toString().trim();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(classe)
                        || TextUtils.isEmpty(grades)) {
                    Toast.makeText(GradeActivity.this, "选项不能为空",
                            Toast.LENGTH_SHORT).show();
                    GradeInfoManager.dialogState(dialog, false);
                    return;

                }
                List<Grade> all = GradeInfoManager.getInstance(GradeActivity.this)
                        .queryAll();
                for (Grade grade : all) {
                    name = grade.getName();

                }
                if (existName != null && existName.equals(name)) {
                    Toast.makeText(GradeActivity.this, "该分数已存在，无需重复等级",
                            Toast.LENGTH_SHORT).show();
                    GradeInfoManager.dialogState(dialog, false);

                } else {
                    Grade grade = new Grade(classe,name , grades);
                    GradeInfoManager.getInstance(GradeActivity.this)
                            .insertUser(grade);
                    GradeInfoManager.dialogState(dialog, true);
                    Toast.makeText(GradeActivity.this, "登记成功",
                            Toast.LENGTH_SHORT).show();
                    loginName.setText(name);
                    loginClass.setText(grades);

                }

            }

        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override


            public void onClick(DialogInterface dialog, int which) {
                GradeInfoManager.dialogState(dialog, true);

            }

        });
        builder.create().show();

    }}