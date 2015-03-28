package com.devesh.trendsapp.tasks;

import android.os.AsyncTask;

import twitter4j.Query;
//import twitter4j.QueryResult;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 */

public class TwitterSearchAPITask extends AsyncTask<Void, Void, QueryResult>{
    public interface SearchCompleteDelegate{
        public void onSearchCompleted(QueryResult result);
    }
    private Twitter twitter;
    private String searchString;
    private SearchCompleteDelegate listener;
    public TwitterSearchAPITask(Twitter twitter,String str , SearchCompleteDelegate listener){
        this.searchString = str;
        this.listener = listener;
        this.twitter = twitter;
    }

    @Override
    protected void onPostExecute( QueryResult result) {
        listener.onSearchCompleted(result);

    }

    @Override
    protected QueryResult doInBackground(Void... params) {
        Query query = new Query(searchString);
        query.setSince("2015-01-01");
        QueryResult result = null;
        try {
            result = twitter.search(query);

        } catch (TwitterException e) {
            e.printStackTrace();

        }


        return result;


    }
}

/*

public class TwitterSearchAPITask<T> extends AsyncTask<Void, Void , QueryResult>{
    SearchCompleteDelegate listener= null;
    QueryResult result=null;
    String searchString;
    Twitter twitter;
    private Class<T> classType;


    public TwitterSearchAPITask(Class<T> clazz,Twitter twitter,String searchString,SearchCompleteDelegate listener){
        this.classType = clazz;
        this.listener = listener;
        this.searchString =searchString;
        this.twitter = twitter;
    }

    @Override
    protected T doInBackground(Void... params) {
        Query query = new Query(searchString);
        query.setSince("2015-01-01");
        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();

        }


        return (T)result;
    }

    @Override
    protected void onPostExecute(T result) {
        listener.onSearchCompleted(result);
    }
    public interface SearchCompleteDelegate{
        public void onSearchCompleted(T result);
    }
}
*/
