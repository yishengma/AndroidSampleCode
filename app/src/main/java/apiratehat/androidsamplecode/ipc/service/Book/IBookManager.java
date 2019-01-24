package apiratehat.androidsamplecode.ipc.service.Book;

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
    static final String TAG = "IBookManager";
    /*public static final*/ String DESCREPTOR = "d.running.Book.IBookManager";

    /*public static final*/ int TRANSACTION_GETBOOKLIST = IBinder.FIRST_CALL_TRANSACTION;

    /*public static final*/ int TRANSACTION_ADDBOOK = IBinder.FIRST_CALL_TRANSACTION + 1;

    /*public*/ List<Book> getBookList() throws RemoteException;

    /*public*/ void addBook(Book book) throws RemoteException;

    /*public static*/ abstract class BookManagerStub extends Binder implements IBookManager {

        public BookManagerStub() {
            this.attachInterface(this, DESCREPTOR);
        }


        //这个方法运行在 服务端的 Binder 线程池中，当客户端发起跨进程请求的时候
        //远程请求会通过系统封装后交由此方法来处理。

        //服务端可以通过 code 确定请求的目标方法
        //接着从 data 中取出 传递的参数
        //然后执行目标方法，就像 reply 中写入返回值
        //
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            Log.e(TAG, "onTransact: ");
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(DESCREPTOR);
                    return true;
                }
                case TRANSACTION_GETBOOKLIST: {
                    data.enforceInterface(DESCREPTOR);
                    List<Book> books = this.getBookList();

                    reply.writeNoException();
                    reply.writeTypedList(books);
                    return true;
                }
                case TRANSACTION_ADDBOOK: {
                    data.enforceInterface(DESCREPTOR);
                    Book book;
                    if (0 != data.readInt()) {
                        Log.e(TAG, "onTransact: "+data);
                        book = Book.CREATOR.createFromParcel(data);
                    } else {
                        book = null;
                    }
                    this.addBook(book);
                    reply.writeNoException();
                    return true;
                }
                default:
                    break;
            }

            return super.onTransact(code, data, reply, flags);
        }

        //返回服务端当前的 Binder , 即 Stub （这个是接口的具体实现类）
        @Override
        public IBinder asBinder() {
            return this;
        }


    }


}
