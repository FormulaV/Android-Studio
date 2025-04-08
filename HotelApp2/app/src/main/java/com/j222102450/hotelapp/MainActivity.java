package com.j222102450.hotelapp;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView _imageView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        _imageView1 = (ImageView) findViewById(R.id.imageView1);

        String imageUrl = "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEi8ZJCZptTRqPqyHtkVr_9DleDikVa5g2lIsLZPhtceF8W0jT6J8_5F8nnIgnvMpuYH5R-3aGH7u99Q7v0vZryclAHTLMC9_vs5_z0vRWWFRycIMRK3rn-9zTwZ8tu7Ubo9WRRkGX6ZYtc/s1600/Hotel+Tepi+Pantai+Nusa+Dua+Bali.jpg";
        Picasso.with(this).load(imageUrl).into(_imageView1);
    }
}
