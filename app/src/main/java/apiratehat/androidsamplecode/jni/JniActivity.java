package apiratehat.androidsamplecode.jni;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import apiratehat.androidsamplecode.R;


public class JniActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni);
        ((TextView)findViewById(R.id.tv_text)).setText(NDKTools.getStringFromNDK());


    }
}


// 在NDKTools.java 目录下 键入  javac -h . NDKTools.java   注意中间有个 . 前后都有空格，其不能包含注释


// javac -h -classpath  apiratehat.androidsamplecode.jni.NDKTools.java
//《jdk10》删除javah.exe文件，在Android studio编译jni，使用jdk10生成头文件
//只要一输入"javah -jni..."的命令就会一直提示
//        'javah'不是内部命令或外部命令，也不是可运行的程序或批处理文件
//       找了很久才发现是之前升级过的jdk10，bin目录下已经没有javah.exe这个文件了
//      jdk10的新特性中，删除了javah，而提出了使用"javac -h"命令替代"javah"