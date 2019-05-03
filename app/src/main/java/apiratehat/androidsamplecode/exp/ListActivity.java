package apiratehat.androidsamplecode.exp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import apiratehat.androidsamplecode.R;

public class ListActivity extends AppCompatActivity {

    private ListView mListView;
    private  int [] icons={R.drawable.qq,R.drawable.qq_dizhu,R.drawable.sina,R.drawable.tmall,R.drawable.uc,R.drawable.weixin,R.drawable.qq,R.drawable.qq_dizhu,R.drawable.sina,R.drawable.tmall,R.drawable.uc,R.drawable.weixin,R.drawable.qq,R.drawable.qq_dizhu,R.drawable.sina,R.drawable.tmall,R.drawable.uc,R.drawable.weixin};//准备数据
    private  String[] appName={"qq","斗地主","新浪","天猫","UC浏览器","微信","qq","斗地主","新浪","天猫","UC浏览器","微信","qq","斗地主","新浪","天猫","UC浏览器","微信",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mListView=(ListView) findViewById(R.id.lv_list);
        //创建一个Adapter实例
        MyAdapter adapter=new MyAdapter();
        //设置Adapter
        mListView.setAdapter(adapter);

    }

    //创建一个类并集成BaseAdapter
    class MyAdapter extends BaseAdapter {

        //得到Item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return appName.length;
        }

        @Override
        public Object getItem(int position) {
            // 返回ListView Item代表的对象
            return appName[position];
        }

        @Override
        public long getItemId(int position) {
            // 返回ListView Item条目的Id
            return position;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup parent) {
            // 将list_item.xml文件找出来并转化成View对象
            View view=View.inflate(ListActivity.this, R.layout.list_item, null);
            TextView mTvName=(TextView) view.findViewById(R.id.tv_name);
            mTvName.setText(appName[position]);
            ImageView mIvimage=(ImageView) view.findViewById(R.id.iv_image);
            mIvimage.setBackgroundResource(icons[position]);
            return view;
        }
    }
}
