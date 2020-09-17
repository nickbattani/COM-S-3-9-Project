package com.example.demoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = (Button)findViewById(R.id.loginButton);
        EditText nameText = (EditText)findViewById(R.id.nameEditText);

        setOnClick(loginButton, nameText);

    }

    private void setOnClick(final Button btn, final EditText str){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePage = new Intent(getApplicationContext(), nameActivity.class);
                changePage.putExtra("random", str.getText().toString());
                startActivity(changePage);
            }
        });
    }
}
