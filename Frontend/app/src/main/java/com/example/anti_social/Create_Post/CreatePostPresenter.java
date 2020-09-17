package com.example.anti_social.Create_Post;

import java.util.Map;

public class CreatePostPresenter {
    private ICreatePostView view;
    private CreatePostModel model;
    private String userId;

    public CreatePostPresenter(ICreatePostView view, String userId){
        this.view = view;
        this.userId = userId;
        model = new CreatePostModel(this);
    }

    public String getUserId(){
        return userId;
    }

    public void onPostSubmit(Map<String, String> params){
        model.createRequest(userId, params);
    }

    public void onModelResponse(){
        view.onSubmissionResponse();
    }
}
