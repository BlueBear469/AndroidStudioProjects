package com.yjprojects.modpkggen;

/*
* fab under version 5.0
* http://www.android4devs.com/2015/03/how-to-make-floating-action-button-fab.html
* fab animation
* http://stackoverflow.com/questions/31624935/floatingactionbutton-expand-into-a-new-activity
 */

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.PathInterpolator;
import android.widget.ImageView;

import me.yugy.github.reveallayout.RevealLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RevealLayout mRevealLayout;
    View mRevealView;
    View mFab;
    ImageView iv;
    View nbp;


    static Boolean isMaterial = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        init();
    }

    private void init(){
        mRevealLayout = (RevealLayout) findViewById(R.id.reveal_layout);
        mRevealView = findViewById(R.id.reveal_view);
        mFab = findViewById(R.id.actionButton);
        iv = (ImageView)findViewById(R.id.arrowobj1);
        nbp = findViewById(R.id.arrowobj2);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) isMaterial = true;

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {


                v.setClickable(false); // Avoid naughty guys clicking FAB again and again...
                final int[] location = new int[2];
                final float[] centerloc = new float[2];

                v.getLocationOnScreen(location);
                location[0] += v.getWidth() / 2;
                location[1] += v.getHeight() / 2;

                final Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                mRevealView.setVisibility(View.VISIBLE);
                mRevealLayout.setVisibility(View.VISIBLE);

                if(isMaterial){
                    centerloc[0] = iv.getX();
                    centerloc[1] = iv.getY();
                    iv.setVisibility(View.VISIBLE);
                    moveit(iv);
                }

                mRevealLayout.show(location[0], location[1]); // Expand from center of FAB. Actually, it just plays reveal animation.

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);

                        overridePendingTransition(R.anim.fade_in,R.anim.hold);
                    }
                }, 600); // 600 is default duration of reveal animation in RevealLayout
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setClickable(true);
                        mRevealLayout.setVisibility(View.INVISIBLE);
                        mRevealView.setVisibility(View.INVISIBLE);
                        if(isMaterial) {
                            iv.setVisibility(View.INVISIBLE);
                            iv.clearAnimation();
                            iv.setX(centerloc[0]);
                            iv.setY(centerloc[1]);

                        }
                        finish();
                    }
                }, 960); // Or some numbers larger than 600.

            }
        });
    }
    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void moveit(final View view) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {


            Path path = new Path();

            float x1 = view.getX();
            float y1 = view.getY();
            float x3 = nbp.getX();
            float y3 = nbp.getY();
            float x2 = (x3 - x1)/4 + x1;
            float y2 = (y3 - y1)/4*3 + y1;

            path.moveTo(x1,y1);
            path.cubicTo(x1,y1,x2,y2,x3,y3);


            ObjectAnimator objectAnimator = null;
            objectAnimator = ObjectAnimator.ofFloat(view, View.X, View.Y, path);

            objectAnimator.setDuration(600);
            PathInterpolator pip = new PathInterpolator(0.25f,0.1f,0.25f,1f);
            objectAnimator.setInterpolator(pip);
            objectAnimator.start();
        }
    }

}



