package com.yjprojects.mydesignapplication;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    AppBarLayout appbar;
    FloatingActionButton fab;
    FloatingActionButton fab2;
    CollapsingToolbarLayout ctl;
    ImageView backdrop;

    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initCtl();
        initNavi();
        initFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_search :
                return true;
            case R.id.home :
                drawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void initToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_short_name);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
    }

    private void initFab(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(this);

    }

    private void initCtl(){
        ctl = (CollapsingToolbarLayout) findViewById(R.id.coll_toolbar);
        ctl.setTitle(toolbar.getTitle());
    }

    private void initNavi(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_1:
                        Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.hello_world, Snackbar.LENGTH_LONG)
                                .show();
                        break;
                }
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab :
                Snackbar.make(findViewById(R.id.coordinatorLayout),R.string.snack_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snack_button_a, this)
                        .show();
                break;
            case R.id.fab2 :
                Snackbar.make(findViewById(R.id.coordinatorLayout),R.string.snack_message, Snackbar.LENGTH_LONG)
                        .setAction(R.string.snack_button_b, this)
                        .show();
                break;

            case R.id.snackbar_action:
                Snackbar.make(findViewById(R.id.coordinatorLayout), R.string.hello_world, Snackbar.LENGTH_SHORT)
                        .show();
                break;
        }
    }
}
