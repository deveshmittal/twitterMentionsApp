package com.devesh.trendsapp.tasks;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.devesh.trendsapp.TwitterConsts;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;

/**
 * Created by deveshmittal1 on 17/03/15.
 */
    public class OAuthRequestTokenTask extends AsyncTask<Void, Void, Void> {
    final String TAG = getClass().getName();
    private Context context;
    private OAuthProvider provider;
    private OAuthConsumer consumer;

    /**
     * We pass the OAuth consumer and provider.
     *
     * @param context  Required to be able to start the intent to launch the browser.
     * @param provider The OAuthProvider object
     * @param consumer The OAuthConsumer object
     */
    public OAuthRequestTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider) {
        this.context = context;
        this.consumer = consumer;
        this.provider = provider;
    }

    /**
     * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
     */
    @Override
    protected Void doInBackground(Void... params) {

        try {
            Log.i(TAG, "Retrieving request token from Google servers");

            final String url = provider.retrieveRequestToken(consumer, TwitterConsts.CALLBACK_URL);
            Log.i(TAG, "Popping a browser with the authorize URL : " + url);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
            context.startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error during OAUth retrieve request token", e);
        }

        return null;
    }

}