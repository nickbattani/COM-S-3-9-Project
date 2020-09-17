package com.example.mtg_lifepoint_calculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DamageLog extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_log);
        if(getIntent().hasExtra("MainActivity.pkg.you")){
            //Populate you values
            TextView youDmgLogTV = (TextView) findViewById(R.id.youDmgLog);
            TextView youHpLogTV = (TextView) findViewById(R.id.youHPLog);
            TextView oppDmgLogTV = (TextView) findViewById(R.id.oppDmgLog);
            TextView oppHpLogTV  = (TextView) findViewById(R.id.oppHPLog);
            int[] youDmgLog = getIntent().getExtras().getIntArray("MainActivity.pkg.you");
            int[] oppDmgLog = getIntent().getExtras().getIntArray("MainActivity.pkg.opp");
            // Logic for your Log
            String youDmgLogString = "";
            String youHPLogString  = "";
            int youLife = 20;
            youDmgLogString += "\n";
            youHPLogString  += youLife + "\n";
            for(int i = 0; i < youDmgLog.length; i++){
                youLife += youDmgLog[i];
                youHPLogString  += youLife + "\n";
                youDmgLogString += youDmgLog[i] + "\n";
            }
            youDmgLogTV.setText(youDmgLogString);
            youHpLogTV.setText(youHPLogString);

            // Logic for opp Log
            String oppDmgLogString = "";
            String oppHPLogString  = "";
            int oppLife = 20;
            oppDmgLogString += "\n";
            oppHPLogString  += oppLife + "\n";
            for(int i = 0; i < oppDmgLog.length; i++){
                oppLife += oppDmgLog[i];
                oppHPLogString  += oppLife + "\n";
                oppDmgLogString += oppDmgLog[i] + "\n";
            }
            oppDmgLogTV.setText(oppDmgLogString);
            oppHpLogTV.setText(oppHPLogString);

        }
    }
}
