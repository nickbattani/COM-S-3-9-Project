package com.example.darkmode;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Switch sw = (Switch) findViewById(R.id.darkSwitch);
        RelativeLayout lo = (RelativeLayout) findViewById(R.id.relativeLayout);

        setOnCLick(sw, lo);

    }
    private void setOnCLick(final Switch sw, final RelativeLayout lo){
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw.isChecked()){
                    // sw.setText("ON");
                    lo.setBackgroundColor(Color.parseColor("@color/darkMode"));
                }
                else{
                    //sw.setText("Off");
                    lo.setBackgroundColor(Color.parseColor("@color/lightMode"));
                }
            }
        });
    }
}
