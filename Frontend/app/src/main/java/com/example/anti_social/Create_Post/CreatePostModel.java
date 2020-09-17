package com.example.anti_social.Create_Post;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.anti_social.app.AppController;
import com.example.anti_social.net_utils.APIFunctions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class CreatePostModel {
    /**
     * Sends the post request to the server to add the new post to the database
     * @param userID the ID of the user creating the post
     * @param params a map containing the parameters for the post containing title body and tags
     */
    private CreatePostPresenter presenter;
    public CreatePostModel(CreatePostPresenter presenter){
        this.presenter = presenter;
    }

    public void createRequest(String userID, Map<String, String> params){
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, APIFunctions.createPost(userID), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            VolleyLog.v("Response:%n %s", response.toString(4));
                            presenter.onModelResponse();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        queue.add(request);
    }
}
