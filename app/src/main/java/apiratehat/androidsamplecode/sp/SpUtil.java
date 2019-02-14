package apiratehat.androidsamplecode.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * Created by PirateHat on 2019/2/14.
 */

public class SpUtil {

    public static void saveApply(String data, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("Sp",Context.MODE_PRIVATE).edit();
        editor.putString("id",data);
        editor.apply();
    }

    public static boolean saveCommit(String data, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("Sp",Context.MODE_PRIVATE).edit();
        editor.putString("id",data);
        return editor.commit();
    }

    public static String get(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("Sp",Context.MODE_PRIVATE);
        return sharedPreferences.getString("id","");
    }

}
