package apiratehat.androidsamplecode.exp.ecp3;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.exp.ecp3.PersonDBHelper;

public class CheckDataActivity extends AppCompatActivity implements View.OnClickListener {

    private ContentResolver resolver;
    private ContentValues values;
    private Uri uri;
    private Button mAdd;
    private Button mDelete;
    private Button mUpdate;
    private Button mQuery;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_data);
        initView();
        createDB();


    }

    private void initView() {
        mAdd = findViewById(R.id.btn_add);
        mDelete = findViewById(R.id.btn_delete);
        mQuery = findViewById(R.id.btn_query);
        mUpdate = findViewById(R.id.btn_update);
        mContent = findViewById(R.id.tv_content);

        mAdd.setOnClickListener(this);
        mDelete.setOnClickListener(this);
        mQuery.setOnClickListener(this);
        mUpdate.setOnClickListener(this);
    }

    private void createDB() {
        PersonDBHelper helper = new PersonDBHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int i = 0; i < 3; i++) {
            ContentValues values = new ContentValues();
            values.put("name", "itcast" + i);
            db.insert("info", null, values);
        }
        db.close();
    }

    @Override
    public void onClick(View v) {
        resolver = getContentResolver();
        uri = Uri.parse("content://apiratehat.androidsamplecode/info");
        values = new ContentValues();
        switch (v.getId()) {
            case R.id.btn_add:
                Random random = new Random();
                values.put("name", "add_itcast" + random.nextInt(10));
                Uri newuri = resolver.insert(uri, values);
                Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                query();
                break;
            case R.id.btn_delete:
                int deleteCount = resolver.delete(uri, "name=?", new String[]{"itcast0"});
                Toast.makeText(this, "添加删除了" + deleteCount + "行", Toast.LENGTH_SHORT).show();
                query();
                break;
            case R.id.btn_query:
               query();
                break;
            case R.id.btn_update:
                values.put("name", "update_itcast");
                int updateCount = resolver.update(uri, values, "name=?", new String[]{"itcast1"});
                Toast.makeText(this, "成功更新了" + updateCount + "行", Toast.LENGTH_SHORT).show();
                query();
                break;
            default:
                break;

        }
    }

    private void query(){
        List<Map<String, String>> data = new ArrayList<>();
        Cursor cursor = resolver.query(uri, new String[]{"_id", "name"}, null, null, null);
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<>();
            map.put("_id", cursor.getString(0));
            map.put("name", cursor.getString(1));
            data.add(map);
        }
        cursor.close();
        mContent.setText(""+data.toString());
    }
}
