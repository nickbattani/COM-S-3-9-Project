package com.example.demoapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class nameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        if(getIntent().hasExtra("random")){
            TextView nameTextView = (TextView) findViewById(R.id.nameTextView);

            String text = getIntent().getExtras().getString("random");

            nameTextView.setText(text);
        }
    }
}
