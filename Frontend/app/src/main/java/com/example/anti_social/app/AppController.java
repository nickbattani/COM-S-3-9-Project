package com.example.anti_social.app;
//import info.vamsikrishna.volleyeg.utils.LrbuBitmapCache;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//import com.android.volley.toolbox.ImageLoader;

/**
 * used to control volley requests to the server
 */
public class AppController extends Application{

    public static final String TAG = AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
  //private ImageLoader MImageLoader;

    private static AppController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    /**
     * returns the instance
     * @return mInstance
     */
    public static synchronized AppController getInstance(){
        return mInstance;
    }

    /**
     * checks if the request queue is empty and if so creates a new one then returns ot
     * @return mRequestQueue
     */
    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     *  adds new request to the request queue
     * @param req the request to add
     * @param tag tag representing request
     */
    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    /**
     * cancels the request with the tag
     * @param tag tag representing requests to be canceled
     */
    public void cancelPendingRequests(Object tag){
        if(mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }
}

