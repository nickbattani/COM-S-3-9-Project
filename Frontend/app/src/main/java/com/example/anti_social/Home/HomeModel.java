package com.example.anti_social.Home;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.anti_social.app.AppController;
import com.example.anti_social.net_utils.APIFunctions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeModel {
    private ArrayList<JSONObject> posts = new ArrayList<>();
    private HomePresenter presenter;
    public HomeModel(HomePresenter presenter){
        this.presenter = presenter;
    }

    public void updatePosts() {
        RequestQueue queue = AppController.getInstance().getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, APIFunctions.GETALLPOSTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<JSONObject> newPosts = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                newPosts.add(response.getJSONObject(i));
                            }
                            posts = newPosts;
                            presenter.onModelResponse(newPosts);
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    public ArrayList<JSONObject> getPosts(){
        return posts;
    }
}
