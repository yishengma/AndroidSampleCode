package apiratehat.androidsamplecode.exp.homework.Grade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GradeDao {
    private SQLiteHelper helper;

    public GradeDao(Context context) {
        helper = new SQLiteHelper(context);
    }

    public void insert(Grade grade) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", grade.getName());
        values.put("class", grade.getClasses());
        values.put("grade", grade.getGrade());
        db.insert("secret", null, values);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("secret", "name=?", new String[]{name});
        db.close();
    }

    public int update(Grade password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", password.getName());
        values.put("class", password.getClasses());
        values.put("grade", password.getGrade());
        int count = db.update("secret", values, "name=?",
                new String[]{password.getName()});
        db.close();
        return count;
    }

    public List<Grade> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("secret", new String[]{"name", "class",
                "grade"}, null, null, null, null, null);
        List<Grade> list = new ArrayList<Grade>();
        while (c.moveToNext()) {
            String name = c.getString(c.getColumnIndex("name"));
            String psw = c.getString(c.getColumnIndex("class"));
            String phone = c.getString(c.getColumnIndex("grade"));
            Grade password = new Grade(name, psw, phone);
            list.add(password);
        }
        c.close();
        db.close();
        return list;
    }

    public String findData(String name, String data) {
        String phone = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("secret", null, "name=?", new String[]{name},
                null, null, null);
        while (c.moveToNext()) {
            phone = c.getString(c.getColumnIndex(data));
        }
        c.close();
        db.close();
        return phone;
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM secret;";
        db.execSQL(sql);
        db.close();
    }
}
