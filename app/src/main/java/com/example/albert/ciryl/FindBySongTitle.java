package com.example.albert.ciryl;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import android.text.method.ScrollingMovementMethod;

public class FindBySongTitle extends Activity{
    TextView textView;
    private TextInputLayout userInput;
    public static String apiKey = "55209121330a9e50d5bc48132c055b8d";
    private static final String TAG = "Ciryl";
    Button nextButton;
    private int songIndex = 0;
    /** Request queue for our network requests. */
    private static RequestQueue requestQueue;

    /**
     * Run when our activity comes into view.
     * @param savedInstanceState state that was saved by the activity last time it was paused
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set up a queue for our Volley requests
        requestQueue = Volley.newRequestQueue(this);
        // Load the main layout for our activity
        setContentView(R.layout.activity_find_by_name_only);

        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        userInput = findViewById(R.id.userSearchInput);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                songIndex++;
                firstAPICall();
            }
        });
        songIndex = 0;

        // Attach the handler to our UI button
        final Button startAPICall = findViewById(R.id.SearchButton);
        startAPICall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d(TAG, "Start API button clicked");
                firstAPICall();
            }
        });
    }
    /**
     * Formats the user query to fit in the URL signature. Removes excess spaces.
     * @param input user search input
     * @return URL parameter
     */
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
    /**
     * Formats the user search query to fit in the url in the API Call
     * @return formatted url in the API call
     */
    private String getSearchTrackIDURL() {
        String name = userInput.getEditText().getText().toString().trim();
        String artist = "";
        name = trimSearch(name);
        String url = "https://api.musixmatch.com/ws/1.1/track.search?format=json&callback=callback&q_track=" + name + "&quorum_factor=1&apikey=" + apiKey;
        return url;
    }
    void firstAPICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET, getSearchTrackIDURL(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                int length = response.getJSONObject("message").getJSONObject("body").getJSONArray("track_list").length();
                                if (songIndex >= length) {
                                    songIndex = 0;
                                }
                                String track_id = response.getJSONObject("message").getJSONObject("body").getJSONArray("track_list")
                                        .getJSONObject(songIndex).getJSONObject("track").getString("track_id");
                                if (track_id.length() > 0) {
                                    textView.setText(track_id);
                                } else {
                                    textView.setText("No results found! Song may not be in the current database. Please" +
                                            " check spelling and try again.");
                                }
                                startSecondAPICall(track_id);
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
    /**
     * Make an API call.
     */
    void startSecondAPICall(String track_id) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    "https://api.musixmatch.com/ws/1.1/track.lyrics.get?fo" +
                            "rmat=json&callback=callback&track_id=" + track_id + "&apikey=" + apiKey,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            String searchParameters = userInput.getEditText().getText().toString().trim();
                            searchParameters = trimSearch(searchParameters);
                            try {
                                String lyric = response.getJSONObject("message").getJSONObject("body").getJSONObject("lyrics").getString("lyrics_body");
                                searchParameters = trimSearch(searchParameters);
                                textView.setText(lyric);
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

