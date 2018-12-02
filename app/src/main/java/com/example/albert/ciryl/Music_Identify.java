package com.example.albert.ciryl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class Music_Identify extends AppCompatActivity {
    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music__identify);

        text =  (EditText) findViewById(R.id.search_text);

        System.out.println(text.getText());
    }
}
