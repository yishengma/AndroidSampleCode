package apiratehat.androidsamplecode.ipc.service.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import apiratehat.androidsamplecode.ipc.service.Book.Book;
import apiratehat.androidsamplecode.ipc.service.Book.IBookManager;


public class BookService extends Service {

    private List<Book> mBooks = new ArrayList<>();
    private static final String TAG = "BookService";
    public BookService() {

    }

    public IBinder mIBinder = new IBookManager.BookManagerStub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.e(TAG, "getBookList: "+mBooks );
            return mBooks;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.e(TAG, "addBook: "+book);
            mBooks.add(book);
        }


    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "onBind: "+mIBinder );
        return mIBinder;
    }
}


//BookService: onBind: d.running.remoteapplication.BookService$1@69ac97e
//E/IBookManager: onTransact:
// E/IBookManager: addBook: d.running.Book.Book@ec05edfid=0,name=1