package com.yjprojects.blurry;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    ImageView sample;

    Button btn;

    int level = 10;
    public static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    public void init(){
        iv = (ImageView) findViewById(R.id.imageView2);
        btn = (Button) findViewById(R.id.button);
        sample = (ImageView) findViewById(R.id.sample);

        // Create an animation instance
        Animation an = new RotateAnimation(0.0f, 360.0f, 0, 0);

        // Set the animation's parameters
        an.setDuration(1000);               // duration in ms
        an.setRepeatCount(-1);                // -1 = infinite repeated
        //an.setRepeatMode(Animation.REVERSE); // reverses each repeat
        an.setFillAfter(true);               // keep rotation after animation

        sample.setAnimation(an);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClickedListener(v);
            }
        });
    }

    public Bitmap getBackground(){
        long startMs = System.currentTimeMillis();
        View rootView = this.getWindow().getDecorView().getRootView();
        iv.setVisibility(View.INVISIBLE);
        rootView.setDrawingCacheEnabled(true);
        Bitmap rawBmp = Bitmap.createScaledBitmap(rootView.getDrawingCache(), rootView.getWidth()/level, rootView.getHeight()/level, true);
        rootView.setDrawingCacheEnabled(false);
        iv.setVisibility(View.VISIBLE);

        long endMs = System.currentTimeMillis();

        Log.i(TAG, endMs-startMs + "ms");
        return rawBmp;
    }

    public void onButtonClickedListener(View v){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(60);
                        Message msg = handler.obtainMessage();
                        handler.sendMessage(msg);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            int[] locations = new int[2];
            iv.getLocationOnScreen(locations);

            Drawable drawable = new BitmapDrawable(Bitmap.createBitmap(Blur.fastblur(MainActivity.this, getBackground(), 10), locations[0]/level, locations[1]/level, iv.getWidth()/level, iv.getHeight()/level));

            iv.setImageDrawable(drawable);
        }
    };



}
