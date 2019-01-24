package apiratehat.androidsamplecode.ipc.client.Book;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.util.List;

/**
 * Created by PirateHat on 2019/1/22.
 */

public interface IBookManager extends IInterface {
     String TAG = "IBookManager";
    /*public static final*/ String DESCREPTOR = "d.running.Book.IBookManager";//d.running.Book.IBookManager

    /*public static final*/ int TRANSACTION_GETBOOKLIST = IBinder.FIRST_CALL_TRANSACTION;

    /*public static final*/ int TRANSACTION_ADDBOOK = IBinder.FIRST_CALL_TRANSACTION + 1;

    /*public*/ List<Book> getBookList() throws RemoteException;

    /*public*/ void addBook(Book book) throws RemoteException;

    /*public static*/ abstract class BookManagerStub extends Binder implements IBookManager {

        public BookManagerStub() {
            this.attachInterface(this, DESCREPTOR);
        }

        //将一个 Binder  对象转换为 客户端所需要的 接口类型对象
        //如果是客户端和服务端同一个进程，则此方法返回的是 客户端本身的定义的 接口
        //否则就是 将 服务端 Binder 对象封装后的一个 代理类
        public static IBookManager asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterface = obj.queryLocalInterface(DESCREPTOR);
            Log.e(TAG, "asInterface: "+  iInterface );
            Log.e(TAG, "asInterface: "+  (iInterface != null && iInterface instanceof IBookManager));
            if (iInterface != null && iInterface instanceof IBookManager) {
                Log.e(TAG, "asInterface: iInterface" );
                return (IBookManager) iInterface;
            }
            Log.e(TAG, "asInterface: Proxy");
            return new BookManagerStub.Proxy(obj);
        }




        private static class Proxy implements IBookManager {
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
            public List<Book> getBookList() throws RemoteException {
                Log.e(TAG, mRemote+"getBookList: " );
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                List<Book> books;

                try {
                    data.writeInterfaceToken(DESCREPTOR);
                    mRemote.transact(TRANSACTION_GETBOOKLIST, data, reply, 0);
                    reply.readException();
                    books = reply.createTypedArrayList(Book.CREATOR);
                } finally {
                    reply.recycle();
                    data.recycle();
                }


                return books;
            }

            @Override
            public void addBook(Book book) throws RemoteException {
                Log.e(TAG, "addBook: " );
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

               try {
                   data.writeInterfaceToken(DESCREPTOR);
                   if (book != null){
                       data.writeInt(1);
                       book.writeToParcel(data,0);

                   }else {
                       data.writeInt(0);
                   }
                   mRemote.transact(TRANSACTION_ADDBOOK,data,reply,0);
                   reply.readException();
               }finally {
                   data.recycle();
                   reply.recycle();
               }
            }

            @Override
            public IBinder asBinder() {
                return mRemote;
            }
        }
    }


}
