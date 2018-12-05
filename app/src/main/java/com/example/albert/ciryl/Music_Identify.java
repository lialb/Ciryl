package com.example.albert.ciryl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class Music_Identify extends AppCompatActivity {
    EditText text;
    Button back_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

       // text =  (EditText) findViewById(R.id.search_text);
        System.out.print("smh");
        //System.out.println(text.getText());

        back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Music_Identify.this, MainActivity.class);
                startActivity(intent);
                //openMainActivity();
            }
        });
    }
    public void openMainActivity() {
        Intent intent = new Intent(Music_Identify.this, MainActivity.class);
        startActivity(intent);
    }
}
