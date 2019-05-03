package apiratehat.androidsamplecode.exp.ecp3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class PersonDBHelper extends SQLiteOpenHelper {


    public PersonDBHelper(@Nullable Context context) {
        super(context,"person.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("create table info (_id integer primary key autoincrement , name varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
