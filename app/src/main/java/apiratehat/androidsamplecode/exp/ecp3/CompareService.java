package apiratehat.androidsamplecode.exp.ecp3;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CompareService extends Service {
    private Binder mBinder = new Binder();
    public CompareService() {
        Log.e("AG","A");
    }

    public class Binder extends android.os.Binder{
        public int compare(int a,int b){
            return a > b ?a :b;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
