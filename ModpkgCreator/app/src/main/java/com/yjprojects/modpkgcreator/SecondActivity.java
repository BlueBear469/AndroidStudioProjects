package com.yjprojects.modpkgcreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jyj on 2016-02-12.
 */
public class SecondActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.secondlayout);
    }
}
