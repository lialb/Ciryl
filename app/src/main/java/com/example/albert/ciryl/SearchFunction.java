package com.example.albert.ciryl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.w3c.dom.Text;

/**
 * Creates a page to get user input
 */

public class SearchFunction extends Activity {
    private static final String TAG = "Ciryl";
    private static String songName = "";
    private static String artistName = "";
    private static String lyricName = "";
    TextInputLayout titleInput;
    TextInputLayout artistInput;
    TextInputLayout lyricInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_function);
        final Button search = findViewById(R.id.SearchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                titleInput = findViewById(R.id.titleSearchInput);
                songName = titleInput.getEditText().getText().toString().trim();
                artistInput = findViewById(R.id.artistSearchInput);
                artistName = artistInput.getEditText().getText().toString().trim();
                lyricInput = findViewById(R.id.lyricSearchInput);
                lyricName = lyricInput.getEditText().getText().toString().trim();
                Log.d(TAG, "Start API button clicked");
                openLoadSong();
            }
        });
    }

    /**
     * Helper function to get song title
     * @return song title
     */
    public static String getSongTitle() {
        return songName;
    }

    /**
     * Helper function to get song artist
     * @return song artist
     */
    public static String getSongArtist() {
        return artistName;
    }

    /**
     * Helper function to get song artist
     * @return song artist
     */
    public static String getSongLyric() {
        return lyricName;
    }
    /**
     * Opens up new page
     */
    public void openLoadSong() {
        Intent intent = new Intent(this, LoadSongList.class);
        startActivity(intent);
    }
}
