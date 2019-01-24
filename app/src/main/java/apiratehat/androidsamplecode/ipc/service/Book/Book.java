package apiratehat.androidsamplecode.ipc.service.Book;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PirateHat on 2019/1/22.
 */

public class Book implements Parcelable {
    private int mId;
    private String mName;

    public Book(int id, String name) {
        mId = id;
        mName = name;
    }

    @Override
    public String toString() {
        return super.toString() + "id=" + mId+"," +"name="+mName ;
    }

    protected Book(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
    }
}
