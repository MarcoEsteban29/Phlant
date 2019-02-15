package com.example.simonh07.phlant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simonh07.phlant.activities.MainActivity;
import com.squareup.picasso.Picasso;

public class BaseActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    ImageView imageView;
    TextView textView;
    String URL,name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View nView = navigationView.getHeaderView(0);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        imageView = nView.findViewById(R.id.imageViews);
        textView =nView.findViewById(R.id.textView);
        SharedPreferences preferences = getSharedPreferences("Information",MODE_PRIVATE);
        name = preferences.getString("name","");
        email = preferences.getString("email","");
        textView.setText(name+"\n"+ email);
        URL = preferences.getString("photo","");
        Picasso.with(this).load(URL).into(imageView);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final String appPackageName = getPackageName();

                switch (item.getItemId()) {

                    case R.id.nav_dashboard:
                        Intent dash = new Intent(getApplicationContext(), Lobby.class);
                        startActivity(dash);
                        finish();
                        drawerLayout.closeDrawers();
                        break;


                    case R.id.nav_about_us:
                        Intent anIntent = new Intent(getApplicationContext(), AboutUs.class);
                        startActivity(anIntent);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_chatbot:
                        Intent aIntent = new Intent(getApplicationContext(), ChatBot.class);
                        startActivity(aIntent);
                        finish();
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_weather:
                        Intent abIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(abIntent);
                        finish();
                        drawerLayout.closeDrawers();
                        break;

                }
                return false;
            }
        });


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }


    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
