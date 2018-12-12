package com.example.albert.ciryl;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.widget.TableLayout;
import android.widget.TableRow;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoadSongList extends Activity{
    private static RequestQueue requestQueue;
    private static String apiKey = "55209121330a9e50d5bc48132c055b8d";
    private static final String TAG = "Ciryl";

    private String[] track_info;
    private Button[] buttons;
    private String[] track_id;
    private static String track_ID = "";
    private String title;
    private String artist;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = SearchFunction.getSongTitle();
        artist = SearchFunction.getSongArtist();
        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);
        // Load the main layout for our activity
        setContentView(R.layout.activity_load_songs_list);

        buttons = new Button[8];
        track_info = new String[buttons.length];
        track_id = new String[buttons.length];
        APICall();
    }

    /**
     * This function creates a table of new buttons for user to press that shows the track title
     * and artist of the song.
     */
    private void createButtons() {
        TableLayout table = (TableLayout) findViewById(R.id.tableForButtons);
        for (int i = 0; i < buttons.length; i++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f));
            table.addView(tableRow);

            final int newButton = i;
            Button button = new Button(this);
            button.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT,
                    1.0f));

            button.setText("" + track_info[i]);
            // Make text not clip on small buttons
            button.setPadding(0, 0, 0, 0);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    track_ID = track_id[newButton];
                    buttonClicked();
                }
            });
            tableRow.addView(button);
            buttons[i] = button;
        }
    }

    /**
     * Opens up new page to display the lyrics the user has chosen.
     */
    private void buttonClicked() {
        lockButtonSizes();
        Intent intent = new Intent(this, LoadLyricPage.class);
        startActivity(intent);
    }

    /**
     * helper function to get the track_ID variable
     * @return
     */
    public static String getTrackId() {
        return track_ID;
    }

    /**
     * self-explanatory
     */
    private void lockButtonSizes() {
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];

            int width = button.getWidth();
            button.setMinWidth(width);
            button.setMaxWidth(width);

            int height = button.getHeight();
            button.setMinHeight(height);
            button.setMaxHeight(height);
        }
    }
    /**
     * Formats the user search query to fit in the url in the API Call
     * @return formatted url in the API call
     */
    private String getSearchTrackIDURL() {
        String songTitle = title.trim();
        String songArtist = artist.trim();
        songArtist = trimSearch(songArtist);
        songTitle = trimSearch(songTitle);
        String url = "https://api.musixmatch.com/ws/1.1/track.search?format=json&callback=callback&q_track=" + songTitle + "&q_artist=" + songArtist + "&quorum_factor=1&apikey=" + apiKey;
        return url;
    }
    private String trimSearch(String input) {
        String url = "";
        String[] arr = input.split(" ");
        ArrayList<String> list = new ArrayList<String>(Arrays.asList(arr));
        for (int i  = 0; i < list.size(); i++) {
            if (list.get(i).length() > 0) {
                url += list.get(i);
                if (i < list.size() - 1) {
                    url += "%20";
                }
            }
        }
        return url;
    }
    void APICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, getSearchTrackIDURL(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                for (int i = 0; i < track_info.length; i++) {
                                    String track_name = response.getJSONObject("message").getJSONObject("body").getJSONArray("track_list")
                                            .getJSONObject(i).getJSONObject("track").getString("track_name");
                                    String artist_name = response.getJSONObject("message").getJSONObject("body").getJSONArray("track_list")
                                            .getJSONObject(i).getJSONObject("track").getString("artist_name");
                                    track_id[i] = response.getJSONObject("message").getJSONObject("body").getJSONArray("track_list")
                                            .getJSONObject(i).getJSONObject("track").getString("track_id");
                                    track_info[i] = track_name + "\n" + artist_name;
                                }
                                createButtons();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(final VolleyError error) {
                    Log.w(TAG, error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("apiKey", "55209121330a9e50d5bc48132c055b8d");
                    Log.d(TAG, params.toString());
                    return params;
                }
            };
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
