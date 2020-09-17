package com.example.mtg_lifepoint_calculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        // Your buttons
        Button youMinusOne = (Button) findViewById(R.id.youMinusOne);
        Button youPlusOne  = (Button) findViewById(R.id.youPlusOne);
        Button youvarAdd   = (Button) findViewById(R.id.youVariableAdd);
        Button youUndo     = (Button) findViewById(R.id.youUndo);

        //oppButtons
        Button oppMinusOne = (Button) findViewById(R.id.oppMinusOne);
        Button oppPlusOne  = (Button) findViewById(R.id.oppPlusOne);
        Button oppvarAdd   = (Button) findViewById(R.id.oppVariableAdd);
        Button oppUndo     = (Button) findViewById(R.id.oppUndo);

        //Other Buttons
        Button resetButton = (Button) findViewById(R.id.resetButton);
        Button logView     = (Button) findViewById(R.id.logView);

        final int[] yourLife = {20};
        final int[] oppLife  = {20};

        final List<Integer>[] youDmgList = new List[]{new ArrayList<>()};
        final List<Integer>[] oppDmgList = new List[]{new ArrayList<>()};

        youPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView youLife = (TextView) findViewById(R.id.youLife);
                yourLife[0]++;
                youDmgList[0].add(new Integer(1));
                String resultPrint = yourLife[0] + "";
                youLife.setText(resultPrint);
            }
        });

        youMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView youLife = (TextView) findViewById(R.id.youLife);
                yourLife[0]--;
                youDmgList[0].add(new Integer(-1));
                String resultPrint = yourLife[0] + "";
                youLife.setText(resultPrint);
            }
        });

        youvarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NExxt two methods are for closing the keyboard
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                TextView youLife = (TextView) findViewById(R.id.youLife);
                EditText youLifeChange = (EditText) findViewById(R.id.youLifeChange);
                String text = youLifeChange.getText().toString();
                youLifeChange.setText("");
                int output = (text.length()>0) ? Integer.parseInt(text): 0;
                if(output != 0)
                    youDmgList[0].add(new Integer(output));

                yourLife[0]+=output;
                String resultPrint = yourLife[0] + "";
                youLife.setText(resultPrint);
            }
        });


        oppPlusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView oppLifeTV = (TextView) findViewById(R.id.oppLife);
                oppLife[0]++;
                oppDmgList[0].add(new Integer(1));
                String resultPrint = oppLife[0] + "";
                oppLifeTV.setText(resultPrint);
            }
        });

        oppMinusOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView oppLifeTV = (TextView) findViewById(R.id.oppLife);
                oppLife[0]--;
                oppDmgList[0].add(new Integer(-1));
                String resultPrint = oppLife[0] + "";
                oppLifeTV.setText(resultPrint);
            }
        });

        oppvarAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                TextView oppLifeTV = (TextView) findViewById(R.id.oppLife);
                EditText oppLifeChange = (EditText) findViewById(R.id.oppLifeChange);
                String text = oppLifeChange.getText().toString();
                oppLifeChange.setText("");
                int output = (text.length()>0) ? Integer.parseInt(text): 0;
                oppLife[0]+=output;
                if(output != 0)
                    oppDmgList[0].add(new Integer(output));
                String resultPrint = oppLife[0] + "";
                oppLifeTV.setText(resultPrint);
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView youLife = (TextView) findViewById(R.id.youLife);
                TextView oppLifeTV = (TextView) findViewById(R.id.oppLife);
                yourLife[0] = 20;
                oppLife[0]  = 20;
                String resultPrint = yourLife[0] + "";
                youLife.setText(resultPrint);
                String oppString = oppLife[0] + "";
                oppLifeTV.setText(oppString);
                youDmgList[0] = new ArrayList<>();
                oppDmgList[0] = new ArrayList<>();
            }
        });

        youUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(youDmgList[0].size() > 0) {
                    TextView youLife = (TextView) findViewById(R.id.youLife);
                    int removedDmg = youDmgList[0].remove(youDmgList[0].size() - 1).intValue();
                    yourLife[0] -= removedDmg;
                    String resultPrint = yourLife[0] + "";
                    youLife.setText(resultPrint);
                }
            }
        });

        oppUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oppDmgList[0].size() > 0) {
                    TextView oppLifeTV = (TextView) findViewById(R.id.oppLife);
                    int removedDmg = oppDmgList[0].remove(oppDmgList[0].size() - 1).intValue();
                    oppLife[0] -= removedDmg;
                    String resultPrint = oppLife[0] + "";
                    oppLifeTV.setText(resultPrint);
                }
            }
        });


        logView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dmgLogIntent = new Intent(getApplicationContext(), DamageLog.class);
                // Unwrap logged values
                int[] youDmgLog = new int[youDmgList[0].size()];
                for(int i = 0; i < youDmgLog.length; i++){
                    youDmgLog[i] = youDmgList[0].get(i).intValue();
                }
                int[] oppDmgLog = new int[oppDmgList[0].size()];
                for(int i = 0; i < oppDmgLog.length; i++){
                    oppDmgLog[i] = oppDmgList[0].get(i).intValue();
                }
                dmgLogIntent.putExtra("MainActivity.pkg.you", youDmgLog);
                dmgLogIntent.putExtra("MainActivity.pkg.opp", oppDmgLog);
                startActivity(dmgLogIntent);
            }
        });
    }
}
