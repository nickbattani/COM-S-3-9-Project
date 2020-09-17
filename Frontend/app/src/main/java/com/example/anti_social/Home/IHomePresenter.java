package com.example.anti_social.Home;

public interface IHomePresenter {
    void postsOutdated();

    String getUserId();

    void setCurrentSort(int sort);

    void onHashTagResponse(String hashtag);
}
