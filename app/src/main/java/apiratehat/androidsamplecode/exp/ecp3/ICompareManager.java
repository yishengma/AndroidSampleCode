package apiratehat.androidsamplecode.exp.ecp3;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by PirateHat on 2019/1/22.
 */

public interface ICompareManager extends IInterface {
    String TAG = "ICompareManager";
    /*public static final*/ String DESCREPTOR = "apiratehat.androidsamplecode.exp.ecp3.ICompareManager";//d.running.Book.IBookManager

    /*public static final*/ int TRANSACTION_COMPARE = IBinder.FIRST_CALL_TRANSACTION;

    int compare(int a,int b) throws RemoteException;

    /*public static*/ abstract class ICompareManagerStub extends Binder implements ICompareManager {

        public ICompareManagerStub() {
            this.attachInterface(this, DESCREPTOR);
        }

        //将一个 Binder  对象转换为 客户端所需要的 接口类型对象
        //如果是客户端和服务端同一个进程，则此方法返回的是 客户端本身的定义的 接口
        //否则就是 将 服务端 Binder 对象封装后的一个 代理类
        public static ICompareManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterface = obj.queryLocalInterface(DESCREPTOR);
            Log.e(TAG, "asInterface: "+  iInterface );
            Log.e(TAG, "asInterface: "+  (iInterface != null && iInterface instanceof ICompareManager));
            if (iInterface != null && iInterface instanceof ICompareManager) {
                Log.e(TAG, "asInterface: iInterface" );
                return (ICompareManager) iInterface;
            }
            Log.e(TAG, "asInterface: Proxy");
            return new ICompareManagerStub.Proxy(obj);
        }




        private static class Proxy implements ICompareManager{
            private IBinder mRemote;

            public Proxy(IBinder remote) {
                mRemote = remote;
            }

            public String getInterfaceDescriptor() {
                return DESCREPTOR;
            }

            //这个一个服务端在客户端的代理对象，使用的是 代理模式
            //所有的方法都是

            @Override
            public int compare(int a, int b)throws RemoteException {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                int result  = 0;
                try {
                    data.writeInterfaceToken(DESCREPTOR);
                    data.writeInt(a);
                    data.writeInt(b);
                    mRemote.transact(TRANSACTION_COMPARE, data, reply, 0);
                    reply.readException();
                    result = reply.readInt();
                } finally {
                    reply.recycle();
                    data.recycle();
                }
                return result;
            }


            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
    }


}
