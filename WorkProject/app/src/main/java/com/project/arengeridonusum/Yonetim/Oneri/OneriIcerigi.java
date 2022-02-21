package com.project.arengeridonusum.Yonetim.Oneri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.project.arengeridonusum.R;

public class OneriIcerigi extends AppCompatActivity {


    String oneri_baslik, oneri_icerik;

    TextView baslik, icerik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneri_icerigi);

        baslik = findViewById(R.id.oneri_baslik);
        icerik = findViewById(R.id.oneri_icerigi);

        Intent intent = getIntent();
        oneri_baslik = intent.getStringExtra("oneri_baslik");
        oneri_icerik = intent.getStringExtra("oneri_icerik");

        baslik.setText(oneri_baslik);
        icerik.setText(oneri_icerik);
    }
}