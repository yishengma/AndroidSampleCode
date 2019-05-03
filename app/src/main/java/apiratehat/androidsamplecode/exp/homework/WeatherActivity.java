package apiratehat.androidsamplecode.exp.homework;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import apiratehat.androidsamplecode.R;

public class WeatherActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String WEATHER_URL = "https://free-api.heweather.com/s6/weather/forecast?key=9cdd4b9f2d20498180819a442ee0a8fc&location=";//+city
    private RelativeLayout mRelativeBg;
    private TextView mCity;
    private ImageView mWea;
    private TextView mTemp;
    private TextView mWind;
    private TextView mPm;
    private TextView mClothes;
    private TextView mWeather;

    private TextView mBeijing;
    private TextView mShanghai;
    private TextView mGuangzhou;
    private TextView mWulumuqi;
    private String beijingData;
    private String shanghaiData;
    private String guangzhouData;
    private String wulumuqiData;

    private MyHandler handler = new MyHandler(this);

    private class MyHandler extends Handler {
        private WeakReference<WeatherActivity> weakReference;

        public MyHandler(WeatherActivity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 1:
                    setData(beijingData);
                    break;
                case 2:
                    setData(shanghaiData);
                    break;
                case 3:
                    setData(guangzhouData);
                    break;
                case 4:
                    setData(wulumuqiData);
                    break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initView();
    }

    private void initView() {
        mRelativeBg = findViewById(R.id.rl_bg);
        mCity = findViewById(R.id.tv_city);
        mWea = findViewById(R.id.imv_image);
        mTemp = findViewById(R.id.tv_temp);
        mWind = findViewById(R.id.tv_wind);
        mPm = findViewById(R.id.tv_pm);
        mClothes = findViewById(R.id.tv_clothes);
        mWeather = findViewById(R.id.tv_weather);
        mBeijing = findViewById(R.id.tv_1);
        mShanghai = findViewById(R.id.tv_2);
        mGuangzhou = findViewById(R.id.tv_3);
        mWulumuqi = findViewById(R.id.tv_4);

        mBeijing.setOnClickListener(this);
        mShanghai.setOnClickListener(this);
        mGuangzhou.setOnClickListener(this);
        mWulumuqi.setOnClickListener(this);
        mRelativeBg.setBackgroundResource(R.drawable.beijing);
        mCity.setText("北京");
        new Thread(new Runnable() {
            @Override
            public void run() {
                beijingData = get("北京");
                handler.sendEmptyMessage(1);

            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_1:
                mRelativeBg.setBackgroundResource(R.drawable.beijing);
                mCity.setText("北京");
                if (!TextUtils.isEmpty(beijingData)) {
                    setData(beijingData);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        beijingData = get("北京");
                        handler.sendEmptyMessage(1);

                    }
                }).start();
                break;
            case R.id.tv_2:
                mRelativeBg.setBackgroundResource(R.drawable.shanghai);
                mCity.setText("上海");
                if (!TextUtils.isEmpty(shanghaiData)) {
                    setData(shanghaiData);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        shanghaiData = get("上海");
                        handler.sendEmptyMessage(2);

                    }
                }).start();

                break;
            case R.id.tv_3:
                mRelativeBg.setBackgroundResource(R.drawable.guangzhou);
                mCity.setText("广州");
                if (!TextUtils.isEmpty(guangzhouData)) {
                    setData(guangzhouData);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        guangzhouData = get("广州");
                        handler.sendEmptyMessage(3);

                    }
                }).start();


                break;
            case R.id.tv_4:
                mRelativeBg.setBackgroundResource(R.drawable.wulumuqi);
                mCity.setText("乌鲁木齐");
                if (!TextUtils.isEmpty(wulumuqiData)) {
                    setData(wulumuqiData);
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        wulumuqiData = get("乌鲁木齐");
                        handler.sendEmptyMessage(4);

                    }
                }).start();


                break;
        }
    }

    public String get(String city) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            //找水源，创建URL
            URL url = new URL(WEATHER_URL + city);
            //开水闸-openConnection
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //建水管-InputStream
            InputStream inputStream = httpURLConnection.getInputStream();
            //建蓄水池蓄水-InputStreamReader
            InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
            //水桶盛水-BufferedReader
            BufferedReader bufferedReader = new BufferedReader(reader);


            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp);
            }
            bufferedReader.close();
            reader.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return stringBuilder.toString();
    }

    private void setData(String city) {
        JsonRootBean bean = GsonUtil.gsonToBean(city, JsonRootBean.class);
        Daily_forecast daily_forecast = bean.getHeWeather6().get(0).getDaily_forecast().get(0);
        mWea.setImageResource(ImageCodeUtil.getImageByCode(daily_forecast.getCond_code_d()));
        mTemp.setText("温度：" + daily_forecast.getTmp_max());
        mWind.setText(String.format("%s: %s", daily_forecast.getWind_dir(), daily_forecast.getWind_sc()));
        mPm.setText("Pm:" + daily_forecast.getHum());
        mClothes.setText("穿衣指数：" + daily_forecast.getVis());
        mWeather.setText(daily_forecast.getCond_txt_d());

    }
}
