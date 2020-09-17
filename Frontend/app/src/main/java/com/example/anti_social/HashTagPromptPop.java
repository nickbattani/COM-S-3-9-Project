package com.example.anti_social;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class HashTagPromptPop extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_sortby_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height * .2));
        Button submitPrompt = (Button) findViewById(R.id.submitSortButton);
        submitPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText hashTagSortET = (EditText) findViewById(R.id.hashTagSortET);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("HashtagToSortBy", hashTagSortET.getText().toString());
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }

}
