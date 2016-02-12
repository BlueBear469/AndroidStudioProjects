package com.yjprojects.modpkgcreator;

/*
* fab under version 5.0
* http://www.android4devs.com/2015/03/how-to-make-floating-action-button-fab.html
* fab animation
* http://stackoverflow.com/questions/31624935/floatingactionbutton-expand-into-a-new-activity
 */

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import me.yugy.github.reveallayout.RevealLayout;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RevealLayout mRevealLayout;
    View mRevealView;
    View mFab;
    ImageView iv;

    Boolean isMaterial = false;
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
        iv = (ImageView)findViewById(R.id.imageView);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) isMaterial = true;

        mFab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mFab.setClickable(false); // Avoid naughty guys clicking FAB again and again...
                int[] location = new int[2];
                mFab.getLocationOnScreen(location);
                location[0] += mFab.getWidth() / 2;
                location[1] += mFab.getHeight() / 2;

                final Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                if(isMaterial) iv.setVisibility(View.VISIBLE);
                mRevealView.setVisibility(View.VISIBLE);
                mRevealLayout.setVisibility(View.VISIBLE);

                mRevealLayout.show(location[0], location[1]); // Expand from center of FAB. Actually, it just plays reveal animation.
                mFab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);

                        overridePendingTransition(0, R.anim.hold);
                    }
                }, 600); // 600 is default duration of reveal animation in RevealLayout
                mFab.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mFab.setClickable(true);
                        mRevealLayout.setVisibility(View.INVISIBLE);
                        mRevealView.setVisibility(View.INVISIBLE);
                        if(isMaterial) iv.setVisibility(View.INVISIBLE);


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


}
