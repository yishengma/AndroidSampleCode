package apiratehat.androidsamplecode.permission;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

import apiratehat.androidsamplecode.R;

public class PermissionActivity extends AppCompatActivity {

    private static final String TAG = "PermissionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        findViewById(R.id.btn_call).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: " + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M));
                click(v);
//                Uri uri = Uri.parse("content://apiratehat.androidsamplecode/id");
//                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
//                if (cursor!=null){
//                    Toast.makeText(PermissionActivity.this,""+cursor.getInt(0),Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
 //keytool  -printcert  -jarfile  G:\Android Projects\AndroidSampleCode\app\release\app-release.apk
    public void click(View v) {
        //不需要申请权限的时候直接使用
//        startActivityForResult(new Intent(
//                Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);

        //需要申请的权限的时候先判断权限
        //只有 6.0 以上 ，才需要运行时权限， M 是版本代号 API 为 23
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            //检查权限 是否授权
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                //请求授权
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 2);
            } else {

                //授权了就 进行请求
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), 0);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //                                                      //没有点击 不再询问返回 true ，点击不再询问后就返回 false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !shouldShowRequestPermissionRationale(permissions[0])){
            showMissingPermissionDialog();
        }
        Log.e(TAG, "onRequestPermissionsResult: requestCode:" + requestCode + "permissions:" + Arrays.toString(permissions) + "grantResults: " + Arrays.toString(grantResults));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            ContentResolver ContentResolver = getContentResolver();
            Uri contactData = data.getData();
            @SuppressWarnings("deprecation")
            Cursor cursor = managedQuery(contactData, null, null, null, null);
            cursor.moveToFirst();
            String username, usernumber;
            username = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = ContentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null,
                    null);
            while (phone != null && phone.moveToNext()) {
                usernumber = phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                System.out.println(username + usernumber);

            }

        }
    }
//targetSDKVersion 27
// 6.0 版本 resultCode=-1，没有返回数据
//抛出异常 java.lang.SecurityException: Permission Denial: reading com.android.providers.contacts.ContactsProvider2 uri content://com.android.contacts/data/phones from pid=11655, uid=10329 requires android.permission.READ_CONTACTS, or grantUriPermission()

// 第一次申请的时候 ，没有 不再询问的标识 如果 拒绝，就返回
    //onRequestPermissionsResult   方法 返回 requestCode:2permissions:[android.permission.READ_CONTACTS]grantResults: [-1]
    //第二次申请的时候 ，有不再询问的标识，如果 再次拒绝 ，且没有点击不再提示，那么每次 点击的时候就会 提示

    //如果点击的是是不再提醒，那么 就再次点击的时候就不会有反应
   // onRequestPermissionsResult: requestCode:2permissions:[android.permission.READ_CONTACTS]grantResults: [-1]

//5.0 版本  resultCode=-1，返回数据




    //// 显示缺失权限提示
    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PermissionActivity.this);
        builder.setTitle("帮助");
        builder.setMessage("当前应用缺少必要的权限，请打开 “设置” -> “权限” 打开所需权限");

        // 拒绝, 退出应用
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }

    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
