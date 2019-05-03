package apiratehat.androidsamplecode.exp.homework.Grade;

import android.content.Context;
import android.content.DialogInterface;

import java.lang.reflect.Field;
import java.util.List;


public class GradeInfoManager {
    private static GradeInfoManager instance;
    private Context context;
    private static GradeDao dao;
    public static String CLASS = "class";
    public static String GRADE = "grade";
    public static String NAME = "name";

    public static GradeInfoManager getInstance(Context context) {
        if (instance == null) {
            instance = new GradeInfoManager(context);

        }
        return instance;

    }

    private GradeInfoManager(Context context) {
        this.context = context;
        dao = new GradeDao(context);

    }

    public void insertUser(Grade grade) {
        dao.insert(grade);

    }

    public String findData(String name, String data) {
        return dao.findData(name, data);

    }

    public void updateUser(Grade grade) {
        dao.update(grade);

    }

    public void delete(String name) {
        dao.delete(name);

    }

    public List<Grade> queryAll() {
        return dao.queryAll();

    }

    public void deleteAll() {
        dao.deleteAll();

    }

    /**
     * 由于对话框无论点击哪个系统按钮都会自动关闭,通过mShowing判断
     * 设置为false不关闭,设置为true关闭对话框
     */


    public static void dialogState(DialogInterface dialog, boolean flag) {
        try {
            Field field = dialog.getClass().getSuperclass().
                    getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, flag);

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}