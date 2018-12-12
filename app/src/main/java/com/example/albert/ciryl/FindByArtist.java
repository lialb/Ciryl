package com.example.albert.ciryl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class FindByArtist extends Activity {
    private static final String TAG = "Ciryl";
    private static String songName = "";
    private static String artistName = "";
    TextInputLayout titleInput;
    TextInputLayout artistInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_by_artist);

        final Button search = findViewById(R.id.SearchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                titleInput = findViewById(R.id.titleSearchInput);
                songName = titleInput.getEditText().getText().toString().trim();
                artistInput = findViewById(R.id.artistSearchInput);
                artistName = artistInput.getEditText().getText().toString().trim();
                Log.d(TAG, "Start API button clicked");
                openLoadSong();
            }
        });
    }
    public static String getSongTitle() {
        return songName;
    }
    public static String getSongArtist() {
        return artistName;
    }
    public void openLoadSong() {
        Intent intent = new Intent(this, LoadSongList.class);
        startActivity(intent);
    }
}
