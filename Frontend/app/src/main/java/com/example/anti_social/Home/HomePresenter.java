package com.example.anti_social.Home;

import com.example.anti_social.comparators.JSONObjectDateCompare;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class HomePresenter implements IHomePresenter {
    private HomeModel model;
    private IHomeView view;
    private String userId;
    private String currentHashTag = null;
    private int currentSort;

    private static final int SORT_BY_NEW = 0;
    private static final int SORT_BY_HASHTAG = 1;

    public HomePresenter(IHomeView view, String userId){
        this.view = view;
        this.userId = userId;
        model = new HomeModel(this);
        currentSort = SORT_BY_NEW;
    }

    @Override
    public void postsOutdated() {
        model.updatePosts();
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setCurrentSort(int sort) {
        if(sort == SORT_BY_HASHTAG || sort != currentSort){
            currentSort = sort;
            postsOutdated();
        }
    }

    @Override
    public void onHashTagResponse(String hashtag) {
        currentHashTag = hashtag;
        ArrayList<JSONObject> postsToRender = getPostByHashtag(model.getPosts(), hashtag);
        Collections.sort(postsToRender, new JSONObjectDateCompare());
        view.renderPosts(postsToRender, userId);
    }

    public static ArrayList<JSONObject> getPostByHashtag(ArrayList<JSONObject> posts, String sortByHashtag) {
        ArrayList<JSONObject> postsToRender = new ArrayList<>();
        for (int i = 0; i < posts.size(); i++) {
            try {
                String hashtag = posts.get(i).getString("hashTag");
                if (hashtag.equals(sortByHashtag))
                    postsToRender.add(posts.get(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return postsToRender;
    }

    public void onModelResponse(ArrayList<JSONObject> posts){
        int sortingMethod = currentSort;
        if (sortingMethod == SORT_BY_HASHTAG && currentHashTag == null) {
            view.promptUserForHashtag();
        }
        else if(sortingMethod == SORT_BY_HASHTAG){
            ArrayList<JSONObject> postsToRender = getPostByHashtag(posts, currentHashTag);
            Collections.sort(postsToRender, new JSONObjectDateCompare());
            view.renderPosts(postsToRender, userId);
        }
        else {
            currentHashTag = null;
            if (sortingMethod == SORT_BY_NEW) {
                Collections.sort(posts, new JSONObjectDateCompare());
            }
            view.renderPosts(posts, userId);
        }
    }


}
