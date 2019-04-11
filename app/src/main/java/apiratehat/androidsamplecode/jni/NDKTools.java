package apiratehat.androidsamplecode.jni;

/**
 *
 * Created by PirateHat on 2019/4/3.
 */

public class NDKTools {


    static {
        System.loadLibrary("opensl");
    }


    public static native String getStringFromNDK();

}


