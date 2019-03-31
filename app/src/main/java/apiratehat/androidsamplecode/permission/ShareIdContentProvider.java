package apiratehat.androidsamplecode.permission;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by PirateHat on 2019/3/31.
 */

public class ShareIdContentProvider extends ContentProvider {
    private static final String TAG = "ShareIdContentProvider";
    private static UriMatcher uriMatcher;
    public static final String AUTHORITY = "apiratehat.androidsamplecode";

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "id", 0);
    }

    @Override
    public boolean onCreate() {

        //返回 true 表示创建成功
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.e(TAG, "query: " );
        Cursor cursor = null;
        switch (uriMatcher.match(uri)) {
            case 0:
                cursor = new Cursor() {
                    @Override
                    public int getCount() {
                        return 0;
                    }

                    @Override
                    public int getPosition() {
                        return 0;
                    }

                    @Override
                    public boolean move(int offset) {
                        return false;
                    }

                    @Override
                    public boolean moveToPosition(int position) {
                        return false;
                    }

                    @Override
                    public boolean moveToFirst() {
                        return false;
                    }

                    @Override
                    public boolean moveToLast() {
                        return false;
                    }

                    @Override
                    public boolean moveToNext() {
                        return false;
                    }

                    @Override
                    public boolean moveToPrevious() {
                        return false;
                    }

                    @Override
                    public boolean isFirst() {
                        return false;
                    }

                    @Override
                    public boolean isLast() {
                        return false;
                    }

                    @Override
                    public boolean isBeforeFirst() {
                        return false;
                    }

                    @Override
                    public boolean isAfterLast() {
                        return false;
                    }

                    @Override
                    public int getColumnIndex(String columnName) {
                        return 0;
                    }

                    @Override
                    public int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException {
                        return 0;
                    }

                    @Override
                    public String getColumnName(int columnIndex) {
                        return null;
                    }

                    @Override
                    public String[] getColumnNames() {
                        return new String[0];
                    }

                    @Override
                    public int getColumnCount() {
                        return 0;
                    }

                    @Override
                    public byte[] getBlob(int columnIndex) {
                        return new byte[0];
                    }

                    @Override
                    public String getString(int columnIndex) {
                        return null;
                    }

                    @Override
                    public void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer) {

                    }

                    @Override
                    public short getShort(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public int getInt(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public long getLong(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public float getFloat(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public double getDouble(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public int getType(int columnIndex) {
                        return 1; //修改为 1
                    }

                    @Override
                    public boolean isNull(int columnIndex) {
                        return false;
                    }

                    @Override
                    public void deactivate() {

                    }

                    @Override
                    public boolean requery() {
                        return false;
                    }

                    @Override
                    public void close() {

                    }

                    @Override
                    public boolean isClosed() {
                        return false;
                    }

                    @Override
                    public void registerContentObserver(ContentObserver observer) {

                    }

                    @Override
                    public void unregisterContentObserver(ContentObserver observer) {

                    }

                    @Override
                    public void registerDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public void unregisterDataSetObserver(DataSetObserver observer) {

                    }

                    @Override
                    public void setNotificationUri(ContentResolver cr, Uri uri) {

                    }

                    @Override
                    public Uri getNotificationUri() {
                        return null;
                    }

                    @Override
                    public boolean getWantsAllOnMoveCalls() {
                        return false;
                    }

                    @Override
                    public void setExtras(Bundle extras) {

                    }

                    @Override
                    public Bundle getExtras() {
                        return null;
                    }

                    @Override
                    public Bundle respond(Bundle extras) {
                        return null;
                    }
                };
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
