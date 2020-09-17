package com.example.anti_social;

import com.example.anti_social.Home.HomePresenter;
import com.example.anti_social.Home.IHomeView;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class HomePresenterTests {

    @Test
    public void test_grabHashtag() throws JSONException {
        ArrayList<JSONObject> posts = mock(ArrayList.class);
        JSONObject testTag = mock (JSONObject.class);
        when(testTag.getString("hashTag")).thenReturn("test");
        JSONObject wrongTag = mock (JSONObject.class);
        when(wrongTag.getString("hashTag")).thenReturn("Not in it");

        when(posts.size()).thenReturn(4);
        when(posts.get(0)).thenReturn(testTag);
        when(posts.get(1)).thenReturn(wrongTag);
        when(posts.get(2)).thenReturn(wrongTag);
        when(posts.get(3)).thenReturn(testTag);

        assertEquals(HomePresenter.getPostByHashtag(posts, "test").size(), 2);
    }

    @Test
    public void test_getUserId(){
        IHomeView view = mock(IHomeView.class);
        HomePresenter presenter = new HomePresenter(view, "123");
        assertEquals(presenter.getUserId(), "123");
    }
}