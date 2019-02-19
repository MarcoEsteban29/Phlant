package com.example.simonh07.phlant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Lobby extends BaseActivity {
    private TextView mTextMessage;
    Button button;
    ShareDialog shareDialog;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatebase;
    DatabaseReference mRef;
    float temp;
    float temps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_lobby, contentFrameLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        SharedPreferences sharedPreferences = getSharedPreferences("Temperature", MODE_PRIVATE);

        shareDialog = new ShareDialog(this);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFirebaseDatebase = FirebaseDatabase.getInstance();
        temps = sharedPreferences.getFloat("Temp", 20.0f);
        Log.d("Temp", String.valueOf(temp));
        if (temps > 19) {

            mRef = mFirebaseDatebase.getReference("19");
            Log.d("Condish", mRef.getKey());
        } else {
            mRef = mFirebaseDatebase.getReference("18");
            Log.d("Condish", mRef.getKey());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, Lobby.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Model, ViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Model, ViewHolder>(
                        Model.class,
                        R.layout.row,
                        ViewHolder.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(final ViewHolder viewHolder, final Model model, final int position) {
                        viewHolder.setDetails(getApplicationContext(), model.getTitle(), model.getDescription(), model.getImage());

                        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (ShareDialog.canShow(ShareLinkContent.class)) {
                                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                                            .setQuote(model.getTitle())
                                            .setContentUrl(Uri.parse(model.getURL()))
                                            .build();
                                    shareDialog.show(linkContent);  // Show facebook ShareDialog
                                }
                            }
                        });
                    }


                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

}
