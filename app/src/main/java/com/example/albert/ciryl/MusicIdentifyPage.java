package com.example.albert.ciryl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MusicIdentifyPage extends AppCompatActivity {
    Button artistButton, songButton, lyricButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music__identify);

        artistButton = findViewById(R.id.artistSearch);
        songButton = findViewById(R.id.songSearch);
        lyricButton = findViewById(R.id.lyricSearch);

        artistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIdentifyByArtist();
            }
        });
        songButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIdentifyByNameOnly();
            }
        });
        lyricButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIdentifyByLyricOrName();
            }
        });
    }
    public void closeMusicIdentify() {
        this.finish();
    }

    public void openIdentifyByArtist() {
        Intent intent = new Intent(this, FindByArtist.class);
        startActivity(intent);
    }
    public void openIdentifyByLyricOrName() {
        Intent intent = new Intent(this, FindByLyrics.class);
        startActivity(intent);
    }
    public void openIdentifyByNameOnly() {
        Intent intent = new Intent(this, FindBySongTitle.class);
        startActivity(intent);
    }
}
