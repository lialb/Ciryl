package com.example.albert.ciryl;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * CS 125 Final Project made by Albert Li and Andrew Lin
 * "Ciryl" is an app which utilizes a lyric api to display lyrics of songs when they searched for
 */

public class MainActivity extends AppCompatActivity {
    public static final String API_KEY = "35f35de71ef6735af7c8bb8d2c6acb36";
    Button searchButton, random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchFunction();
            }
        });
        random = findViewById(R.id.random);
        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRandomSong();
            }
        });
    }
    /**
     * Opens up a new class and new activity page. This one gets a random song
     */
    public void openRandomSong() {
        Intent intent = new Intent(this, RandomSong.class);
        startActivity(intent);
    }

    /**
     * Opens up page for user input
     */
    public void openSearchFunction() {
        Intent intent = new Intent(this, SearchFunction.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
