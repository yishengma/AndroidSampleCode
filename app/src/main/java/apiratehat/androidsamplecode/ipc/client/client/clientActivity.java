package apiratehat.androidsamplecode.ipc.client.client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;


import java.util.List;

import apiratehat.androidsamplecode.R;
import apiratehat.androidsamplecode.ipc.client.Book.Book;
import apiratehat.androidsamplecode.ipc.client.Book.IBookManager;


public class clientActivity extends AppCompatActivity {
    private IBookManager mIBookManager;
    private static final String TAG = "MainActivity";
    private int mInt;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.e(TAG, "onServiceConnected: " + iBinder);
            mIBookManager = IBookManager.BookManagerStub.asInterface(iBinder);
            Log.e(TAG, "onServiceConnected: " + mIBookManager);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mIBookManager = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        Intent intent = new Intent();
        intent.setPackage("d.running.remoteapplication");
        intent.setAction("d.running.remoteapplication.BookService");

        bindService(intent,mConnection,BIND_AUTO_CREATE);
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book book = new Book(mInt++,String.valueOf(mInt));
                Log.e(TAG, "onClick: "+book);
                try {
                    mIBookManager.addBook(book);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_getBookList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Book> books = null;
                try {
                    books = mIBookManager.getBookList();

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "onClick: "+books.toString() );
            }
        });
    }
}

//E/MainActivity: onServiceConnected: android.os.BinderProxy@69ac97e

// E/IBookManager: asInterface: Proxy

//E/MainActivity: onServiceConnected: d.running.Book.IBookManager$BookManagerStub$Proxy@ec05edf

//d.running.Book.Book@1e497c7