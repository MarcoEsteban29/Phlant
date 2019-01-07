package com.example.simonh07.phlant;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class Lobby extends BaseActivity {
    private TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_lobby, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);

    }
}
