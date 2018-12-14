package com.example.albert.ciryl;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomSong extends Activity {
    private String track_id = "";
    private static String apiKey = MainActivity.API_KEY;
    private static final String TAG = "Ciryl";
    TextView textView;
    private static RequestQueue requestQueue;

    /**
     * loads new page, creates a textview, calls on the API for a random song.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(this);
        setContentView(R.layout.activity_load_lyric_page);
        textView = (TextView) findViewById(R.id.textView);
        textView.setText("Home page" + track_id);
        call();
    }
    Random random = new Random();

    /**
     * A random number generator generates a which gives us a random track id, giving us a random song
     */
    private void call() {
        int x = random.nextInt(20000000) + 100000000;
        track_id = Integer.toString(x);
        APICall();
    }
    /**
     * Trim user search parameters to format for search function via API call.
     * @return formatted URL for query
     */
    private String getUrl() {
        String url = "https://api.musixmatch.com/ws/1.1/track.lyrics.get?fo" +
                "rmat=json&callback=callback&track_id=" + track_id + "&apikey=" + apiKey;
        return url;
    }

    /**
     * API call to display lyrics based on the button user presses. If the generated track-id doesn't
     * generate a song, a new number is generated and it tries again.
     */
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
                                textView.setText("Loading...");
                                call();
                                //textView.setText("Lyrics aren't found! Finding a different song: "+ track_id);
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
