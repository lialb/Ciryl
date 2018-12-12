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

public class LoadLyricPage extends Activity {
    private String track_id = "";
    private static String apiKey = "55209121330a9e50d5bc48132c055b8d";
    private static final String TAG = "Ciryl";
    TextView textView;
    //Button generate;
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_load_lyric_page);
        track_id = LoadSongList.getTrackId();
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Home page" + track_id);

        APICall();
    }
    private String getUrl() {
        //https://api.musixmatch.com/ws/1.1/track.lyrics.get?format=jsonp&callback=callback&track_id=123&apikey=55209121330a9e50d5bc48132c055b8d
        String url = "https://api.musixmatch.com/ws/1.1/track.lyrics.get?fo" +
                "rmat=json&callback=callback&track_id=" + track_id + "&apikey=" + apiKey;
        return url;
    }
    void APICall() {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    getUrl(),
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(final JSONObject response) {

                            textView.setText(response.toString());
                            Log.d(TAG, response.toString());
                            try {
                                textView.setText(response.toString());
                                String lyric = response.getJSONObject("message").getJSONObject("body").getJSONObject("lyrics").getString("lyrics_body");
                                textView.setText(lyric);
                                if (lyric.length() == 0) {
                                    textView.setText("No results found! Please check your search.");
                                }
                            } catch (JSONException e) {
                                textView.setText(e.toString() + "API doesn't work");
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
            textView.setText("API doesn't work 2");
        }
    }
}
