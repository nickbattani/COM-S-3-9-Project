package com.example.anti_social.Home;
// Helpful link for testing with json http://myjson.com/

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.anti_social.Create_Post.CreatePostActivity;
import com.example.anti_social.HashTagPromptPop;
import com.example.anti_social.R;

import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, IHomeView {
    private ArrayList<JSONObject> posts = new ArrayList<>();
    private HomePresenter presenter;
    // Constant declaration
    private static final int SORT_BY_NEW = 0;
    private static final int SORT_BY_HASHTAG = 1;
    private static final int SUCCESS_ON_HASHTAG_PROMPT = 2;
    private static final int SUCCESS_ON_POST_SUBMISSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // Initialize Sort by spinner
        Spinner sortBySpinner = (Spinner) findViewById(R.id.sortBySpinner);
        ArrayAdapter<String> sortBySpinnerAdapter = new ArrayAdapter<String>(HomeActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.sort_By_Spinner_Names));
        sortBySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(sortBySpinnerAdapter);
        sortBySpinner.setOnItemSelectedListener(this);
        if (getIntent().hasExtra("Mainactivity.id")) {
            presenter = new HomePresenter(this, getIntent().getStringExtra("Mainactivity.id"));
            presenter.postsOutdated();
        }

        Button createPostBtn = (Button) findViewById(R.id.createPostBtn);
        createPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createPostIntent = new Intent(getApplicationContext(), CreatePostActivity.class);
                createPostIntent.putExtra("userId", presenter.getUserId());
                startActivityForResult(createPostIntent,SUCCESS_ON_POST_SUBMISSION);
            }
        });
    }

    private void initRecyclerView(ArrayList<JSONObject> posts, String userId) {
        RecyclerView recyclerView = findViewById(R.id.homePostViewRV);
        PostRecyclerViewAdapter adapter = new PostRecyclerViewAdapter(posts, this, userId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedTag = parent.getSelectedItem().toString();
        presenter.setCurrentSort(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (SUCCESS_ON_HASHTAG_PROMPT): {
                if (resultCode == Activity.RESULT_OK) {
                    presenter.onHashTagResponse(data.getStringExtra("HashtagToSortBy"));
                }
                break;
            }
            case (SUCCESS_ON_POST_SUBMISSION):{
                presenter.postsOutdated();
            }
        }
    }


    @Override
    public void renderPosts(ArrayList<JSONObject> posts, String userId) {
        initRecyclerView(posts, getIntent().getStringExtra("Mainactivity.id"));
    }

    @Override
    public void promptUserForHashtag() {
        startActivityForResult(new Intent(HomeActivity.this, HashTagPromptPop.class), SUCCESS_ON_HASHTAG_PROMPT);
    }
}
