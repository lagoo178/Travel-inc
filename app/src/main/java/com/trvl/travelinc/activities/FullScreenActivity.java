package com.trvl.travelinc.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.trvl.travelinc.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

public class FullScreenActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);

        final String url = getIntent().getStringExtra("imageUrl");

        final ImageView image= findViewById(R.id.a_fullscreen_image);
        final TextView message = findViewById(R.id.a_fullscreen_message);

        message.setText("Loading Picture...");
        message.setVisibility(View.VISIBLE);

        Picasso.with(getApplicationContext())
                .load(url)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(image, new Callback()
                {
                    @Override
                    public void onSuccess()
                    {
                        message.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError()
                    {
                        Picasso.with(getApplicationContext())
                                .load(url)
                                .into(image, new Callback()
                                {
                                    @Override
                                    public void onSuccess()
                                    {
                                        message.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError()
                                    {
                                        message.setVisibility(View.VISIBLE);
                                        message.setText("Error: Could not load picture.");
                                    }
                                });
                    }
                });

    }
}
