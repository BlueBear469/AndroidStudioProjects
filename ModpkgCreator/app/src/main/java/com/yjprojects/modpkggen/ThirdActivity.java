package com.yjprojects.modpkggen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by jyj on 2016-02-14.
 */
public class ThirdActivity extends AppCompatActivity implements View.OnClickListener{


    View mFab1;
    View mFab2;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdlayout);
        init();
        initToolbar();
    }

    private void init(){
        mFab1 = findViewById(R.id.actionButton1);
        mFab2 = findViewById(R.id.actionButton2);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mFab1.setOnClickListener(this);
        mFab2.setOnClickListener(this);
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.actionButton1:
                Intent intent = new Intent(ThirdActivity.this, MainActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.righttocenter, R.anim.centertoleft);
                finish();
                break;

            case R.id.actionButton2:
                finish();
                break;
        }
    }
}
