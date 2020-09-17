package com.example.anti_social.Create_Post;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.anti_social.R;

import java.util.HashMap;
import java.util.Map;

/**
 * This activity is intended to be viewed by a user who wants to create a new post
 * The activity allows the user to enter a title and body for the post and add tags
 */
public class CreatePostActivity extends AppCompatActivity implements ICreatePostView {

    private CreatePostPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Log.d("CPActivityStart","Creating CreatePostActivity");

        if(getIntent().hasExtra("userId")){
            presenter = new CreatePostPresenter(this, getIntent().getStringExtra("userId"));
            Button submitPostBtn = (Button) findViewById(R.id.submitPostBtn);
            EditText postTitleET = (EditText) findViewById(R.id.postTitleET);
            EditText bodyET = (EditText) findViewById(R.id.bodyET);
            EditText tagsET = (EditText) findViewById(R.id.tagsET);
            setOnClick(submitPostBtn, postTitleET, bodyET, tagsET, presenter.getUserId());
        }
    }

    /**
     * Sets up the on click listener for the submit post button
     * @param submitPostBtn the submit post button to set the listener to
     * @param postTitleET the edit text containing the title of the post used to create params for server request
     * @param bodyET the edit text containing the body of the post used to create params for server request
     * @param tagsET the edit text containing the ttags of the post used to create params for server request
     * @param userID the ID of the user creating the post
     */
    private void setOnClick(final Button submitPostBtn, final EditText postTitleET, final EditText bodyET, final EditText tagsET, final String userID){
        submitPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("title", postTitleET.getText().toString());
                params.put("body", bodyET.getText().toString());
                params.put("hashTag", tagsET.getText().toString());
                presenter.onPostSubmit(params);
            }
        });
    }

    @Override
    public void onSubmissionResponse(){
        presenter = null;
        finish();
    }

}