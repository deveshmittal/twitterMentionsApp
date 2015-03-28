package com.devesh.trendsapp;

import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.devesh.trendsapp.utils.TextUtils;

/**
 * Created by deveshmittal1 on 28/03/15.
 */
public class TrendsApplication extends Application{

    private static TrendsApplication mInstance;

    private static String TAG = TrendsApplication.class.getName();

    private RequestQueue mRequestQueue;

    public static synchronized TrendsApplication getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag ){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        if(mRequestQueue !=null)
        mRequestQueue.add(req);
    }
    public <T> void addToRequestQueue(Request<T> req){
        req.setTag (TAG);
        if(mRequestQueue !=null)
            mRequestQueue.add(req);
    }

    public void cancelAllRequests(String tag){
        if(mRequestQueue !=null){
            mRequestQueue.cancelAll(tag);
        }
    }



}
