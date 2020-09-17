package com.example.anti_social.Home;

import org.json.JSONObject;

import java.util.ArrayList;

public interface IHomeView {
    void renderPosts(ArrayList<JSONObject> posts, String userId);

    void promptUserForHashtag();
}
