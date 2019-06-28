package com.example.madcamp_1st_week;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FullImage extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        imageView = (ImageView)findViewById(R.id.fullImage_id);

        String data = getIntent().getExtras().getString("img");

        imageView.setImageURI(Uri.parse(data));
    }
}
