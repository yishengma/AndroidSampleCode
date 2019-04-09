package apiratehat.androidsamplecode.audioAndVideo.junior.drawBitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;

import apiratehat.androidsamplecode.R;

public class BitmapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);

         Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.scale_image);
        ImageView imageView = findViewById(R.id.imv_bitmap);
        imageView.setImageBitmap(bitmap);


        SurfaceView surfaceView = findViewById(R.id.sv_bitmap);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (holder == null){
                    return;
                }
                Paint paint = new Paint();
                paint.setAntiAlias(true);
                paint.setStyle(Paint.Style.STROKE);

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.scale_image);
                Canvas canvas = holder.lockCanvas();
                canvas.drawBitmap(bitmap1,0,0,paint);
                holder.unlockCanvasAndPost(canvas);


            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }
}
