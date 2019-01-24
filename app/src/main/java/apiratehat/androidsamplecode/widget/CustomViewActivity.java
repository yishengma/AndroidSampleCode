package apiratehat.androidsamplecode.widget;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import apiratehat.androidsamplecode.R;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        ArrayList<PieView.PieData> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PieView.PieData pieData = new PieView.PieData(String.valueOf(i),i+1);
            data.add(pieData);
        }

        ((PieView)findViewById(R.id.pieView)).setData(data);

    }
}
