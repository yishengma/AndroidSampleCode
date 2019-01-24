package apiratehat.androidsamplecode.ipc.client.Book;

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



    //从序列化的对象中创建原始对象
    protected Book(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        //从序列化中创建原始对象
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        //创建指定长度的原始数组对象
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };


    //返回当前对象的内容描述，如果含有文件描述符，返回1
    //否则返回 0
    @Override
    public int describeContents() {
        return 0;
    }

    //将当前对象写入序列化结构中，其中 i
    // i=1  时，标识当前对象需要作为返回值返回,不能立即释放资源
    // i=0
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
    }
}
