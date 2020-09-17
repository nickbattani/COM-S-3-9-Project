package com.example.anti_social;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anti_social.app.AppController;
import com.example.anti_social.net_utils.APIFunctions;
import com.example.anti_social.view_adapters.CommentRecyclerViewAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Activity page for post content on the app, displays the title and body of the post as well as user comments
 * Allows users to create and add new comments to the post
 */
public class PostActivity extends AppCompatActivity {

    private ArrayList<String> comments = new ArrayList<>();
    private commentSocket soc;
    private CommentRecyclerViewAdapter adapter;
    private int postID;
    private final Handler postHandler = new Handler(){
        /**
         * Handler used to aid in interaction between websockets and recycler view on the UI thread
         * @param m The message to the handler in comment socket. Should always be 0
         */
        public void handleMessage(Message m){
            final int what = m.what;
            if(what == 0){
                updateComments(postID);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        if(getIntent().hasExtra("postContent") && getIntent().hasExtra("userId")){

            TextView titleTV = (TextView) findViewById(R.id.titleTV);
            TextView bodyTV = (TextView) findViewById(R.id.bodyTV);
            TextView tagsTV = (TextView) findViewById(R.id.tagsTV);
            EditText commentET = (EditText) findViewById(R.id.commentET);
            Button postCommentBtn = (Button) findViewById(R.id.postCommentBtn);
            ToggleButton upvoteBtn = (ToggleButton) findViewById(R.id.upvoteBtn);
            ToggleButton downvoteBtn = (ToggleButton) findViewById(R.id.downvoteBtn);

            String jsonString = getIntent().getStringExtra("postContent");
            Log.d("postActivity", "string is " + jsonString);
            try {
                JSONObject post = new JSONObject(jsonString);
                titleTV.setText(post.getString("title"));
                bodyTV.setText(post.getString("body"));
                tagsTV.setText(post.getString("hashTag"));
                postID = post.getInt("id");
                String userId = getIntent().getStringExtra("userId");

                soc = new commentSocket(postID, userId, postHandler);
                soc.setUpSocket();

                getComments(postID);

                setOnClick(postCommentBtn, commentET);
                ToggleButton.OnCheckedChangeListener upvoteListener = initUpvoteLister(postID, userId);
                ToggleButton.OnCheckedChangeListener downvoteListener = initDownvoteListener(postID, userId);
                upvoteBtn.setOnCheckedChangeListener(upvoteListener);
                downvoteBtn.setOnCheckedChangeListener(downvoteListener);

                getUpvotes(postID, userId, upvoteListener);
                getDownvotes(postID,userId, downvoteListener);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the comments on the post from the server. Called when the post is first created
     * @param postId The ID of the post used to get the url for the server request
     */
    private void getComments(int postId){
        comments.clear();
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, APIFunctions.getPostComments(postId),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0; i < response.length(); i++){
                            try {
                                comments.add(response.getJSONObject(i).getString("text"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        initRecyclerView();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    /**
     * Updates the comments in the recycler view.
     * Called by the handler when websocket receives a message
     * @param postId The ID of the post used in getComments()
     */
    private void updateComments(int postId){
        getComments(postId);
        adapter.notifyDataSetChanged();
    }

    /**
     * Initializes the recycler view to store and display comments on the post.
     * Called by getComments() when the post is initially created and by updateComments() when a new comment has been added
     */
    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.postCommentRV);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter = new CommentRecyclerViewAdapter(comments, this);
        recyclerView.setAdapter(adapter);
    }

    /**
     * The setOnClick listener for the post comment button, called when clicked by a user.
     * used to send a message to the websocket containing the comment to be added to the post
     * @param postCommentBtn The post comment button that was pressed
     * @param commentET The edit text containing the comment to be sent.
     */
    private void setOnClick(final Button postCommentBtn, final EditText commentET){
        postCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentET.getText().toString();
                if(!comment.equals("")){
                    soc.send(commentET.getText().toString());
                    commentET.setText("");
                }
            }
        });
    }

    /**
     * Called when the post is first initializes, and initializes the OnCheckedChangeListener for the upvote toggle button
     * @param postID the ID for the post used to call upVoteOrdownvote() within the listener
     * @param userID the ID for the user used to call upVoteOrdownvote() within the listener
     * @return The OnCheckedChangeListener for the upvote toggle button so that it can be enabled and disabled elsewhere
     */
    private ToggleButton.OnCheckedChangeListener initUpvoteLister(final int postID, final String userID){
        ToggleButton.OnCheckedChangeListener upvoteListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    upvoteOrdownvote(APIFunctions.upvotePost(userID, postID), postID, userID);
                }
                else{
                    unUpvoteOrUnDownvote(APIFunctions.unUpvotePost(userID,postID));
                }
            }
        };
        return upvoteListener;
    }

    /**
     * Called when the post is first initializes, and initializes the OnCheckedChangeListener for the downvote toggle button
     * @param postID the ID for the post used to call upVoteOrdownvote() within the listener
     * @param userID the ID for the user used to call upVoteOrdownvote() within the listener
     * @return The OnCheckedChangeListener for the downvote toggle button so that it can be enabled and disabled elsewhere
     */
    private ToggleButton.OnCheckedChangeListener initDownvoteListener(final int postID, final String userID){
        final ToggleButton.OnCheckedChangeListener downvoteListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    upvoteOrdownvote(APIFunctions.downvotePost(userID,postID), postID, userID);
                }
                else{
                    unUpvoteOrUnDownvote(APIFunctions.unDownvotePost(userID, postID));
                }
            }
        };
        return  downvoteListener;
    }

    /**
     * Called when the post is first initialized, and used to check if the user has already upvoted the post
     * in order to toggle the button initially and avoid the possibility of them upvoting more than once
     * @param postID the ID for the post used to get the url for the request to the server
     * @param userID the ID for the user used to get the url for the request to the server
     * @param upvoteListener the onCheckedChangedListener for the upvote button so that it can be disabled to prevent upvoting multiple times
     */
    private void getUpvotes(final int postID, final String userID, final ToggleButton.OnCheckedChangeListener upvoteListener){

        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        StringRequest req = new StringRequest(Request.Method.GET, APIFunctions.getUpvoteID(userID, postID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ToggleButton upvoteBtn = (ToggleButton) findViewById(R.id.upvoteBtn);
                VolleyLog.v("Response:%n %s", response.toString());
                upvoteBtn.setOnCheckedChangeListener(null);
                upvoteBtn.setChecked(true);
                upvoteBtn.setOnCheckedChangeListener(upvoteListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToggleButton upvoteBtn = (ToggleButton) findViewById(R.id.upvoteBtn);
                error.printStackTrace();
                upvoteBtn.setOnCheckedChangeListener(null);
                upvoteBtn.setChecked(false);
                upvoteBtn.setOnCheckedChangeListener(upvoteListener);
            }
        });
        queue.add(req);
    }

    /**
     * Called when the post is first initialized, and used to check if the user has already downvoted the post
     * in order to toggle the button initially and avoid the possibility of them downvoting more than once
     * @param postID the ID for the post used to get the url for the request to the server
     * @param userID the ID for the user used to get the url for the request to the server
     * @param downvoteListener the onCheckedChangedListener for the downvote button so that it can be disabled to prevent downvoting multiple times
     */
    private void getDownvotes(final int postID, final String userID, final ToggleButton.OnCheckedChangeListener downvoteListener){

        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        StringRequest req = new StringRequest(Request.Method.GET, APIFunctions.getDownvoteID(userID, postID), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ToggleButton downvoteBtn = (ToggleButton) findViewById(R.id.downvoteBtn);
                VolleyLog.v("Response:%n %s", response.toString());
                downvoteBtn.setOnCheckedChangeListener(null);
                downvoteBtn.setChecked(true);
                downvoteBtn.setOnCheckedChangeListener(downvoteListener);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToggleButton downvoteBtn = (ToggleButton) findViewById(R.id.downvoteBtn);
                error.printStackTrace();
                downvoteBtn.setOnCheckedChangeListener(null);
                downvoteBtn.setChecked(false);
                downvoteBtn.setOnCheckedChangeListener(downvoteListener);
            }
        });
        queue.add(req);
    }

    /**
     * Function called when a user checks the upvote or downvote toggle buttons
     * Handles the request to the server to add an upvote or downvote depending on the
     * url passed, as well as handling unchecking the oposite button in the event that it is
     * already pressed to avoid both upvoting and downvoting the same post
     * @param url the url for the server to determine if an upvote or downvote is being performed
     * @param postID the ID of the post used to check the url to avoid hardwireing
     * @param userID the ID of the user used to check the url to avoid hardwireing
     */
    private void upvoteOrdownvote(final String url, final int postID, final String userID){
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.v("Response:%n %s", response.toString());
                if(url.equals(APIFunctions.upvotePost(userID, postID))){
                    ToggleButton downvoteBtn = (ToggleButton) findViewById(R.id.downvoteBtn);
                    downvoteBtn.setChecked(false);
                }
                else{
                    ToggleButton upvoteBtn = (ToggleButton) findViewById(R.id.upvoteBtn);
                    upvoteBtn.setChecked(false);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);
    }

    /**
     * function called when a user unchecks the upvote or downvote toggle buttons
     * Handles the server request for removing either an upvote or a downvote
     * depending on the url passed to the function
     * @param url url for the server used to determine if an un-upvote or un-downvote is being performed
     */
    private void unUpvoteOrUnDownvote(final String url){
        RequestQueue queue = Volley.newRequestQueue(PostActivity.this);
        StringRequest req = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.v("Response:%n %s", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(req);
    }

    /**
     * Function called when activity has been exited, used to close the websocket
     * that is utilized fot comments
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        soc.close();
    }
}
