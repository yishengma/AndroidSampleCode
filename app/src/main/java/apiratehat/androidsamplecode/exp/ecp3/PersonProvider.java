package apiratehat.androidsamplecode.exp.ecp3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PersonProvider extends ContentProvider {

    private static UriMatcher matcher = new UriMatcher(-1);
    private static final  int SUCCESS  = 1;
    private PersonDBHelper helper;
    static {
        matcher.addURI("apiratehat.androidsamplecode","info",SUCCESS);
    }
    @Override
    public boolean onCreate() {
        helper = new PersonDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       int code = matcher.match(uri);
       if (code == SUCCESS){
           SQLiteDatabase db = helper.getReadableDatabase();
           return  db.query("info",projection,selection,selectionArgs,null,null,sortOrder);
       }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int code = matcher.match(uri);
        if (code == SUCCESS){
            SQLiteDatabase db = helper.getReadableDatabase();
            long id = db.insert("info",null,values);
            if (id > 0){
                Uri insertedUri = ContentUris.withAppendedId(uri,id);
                getContext().getContentResolver().notifyChange(insertedUri,null);
                return insertedUri;
            }
            db.close();
            return uri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = matcher.match(uri);
        if (code == SUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            int count = db.delete("info",selection,selectionArgs);
            if (count > 0){
                getContext().getContentResolver().notifyChange(uri,null);
            }
            db.close();
            return  count;
        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int code = matcher.match(uri);
        if (code == SUCCESS){
            SQLiteDatabase db = helper.getWritableDatabase();
            int count = db.update("info",values,selection,selectionArgs);
            if (count > 0){
                getContext().getContentResolver().notifyChange(uri,null);
            }
            db.close();
            return count;
        }
        return 0;
    }
}
