package com.devesh.trendsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import twitter4j.Status;


public class MainActivity extends ActionBarActivity{
    private OAuthConsumer consumer;
    private OAuthProvider provider;
    TextView textViewTweet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SearchTweets.searchOnKeyword("football", MainActivity.this, new TweetsReceiverDelegateListener());
        textViewTweet = (TextView)findViewById(R.id.textViewTwitter);
    //    Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
    //    try {
    //        this.consumer = new CommonsHttpOAuthConsumer(TwitterConsts.CONSUMER_KEY, TwitterConsts.CONSUMER_SECRET);
    //        this.provider = new CommonsHttpOAuthProvider(TwitterConsts.REQUEST_URL,TwitterConsts.ACCESS_URL, TwitterConsts.AUTHORIZE_URL);
    //    } catch (Exception e) {

     //  }


  //      new OAuthRequestTokenTask(this,consumer,provider).execute();

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        super.onNewIntent(intent);
        SharedPreferences prefs = getSharedPreferences(TwitterConsts.PREFERENCE_NAME, Context.MODE_PRIVATE);
        final Uri uri = intent.getData();
        if (uri != null && uri.getScheme().equals(TwitterConsts.CALLBACK_URL)) {

            new RetrieveAccessTokenTask(this,consumer,provider,prefs).execute(uri);
            finish();
        }
    }


    public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

        private Context context;
        private OAuthProvider provider;
        private OAuthConsumer consumer;
        private SharedPreferences prefs;

        public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer, OAuthProvider provider, SharedPreferences prefs) {
            this.context = context;
            this.consumer = consumer;
            this.provider = provider;
            this.prefs = prefs;
        }


        /**
         * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret
         * for future API calls.
         */
        @Override
        protected Void doInBackground(Uri... params) {
            final Uri uri = params[0];
            final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

            try {
                provider.retrieveAccessToken(consumer, oauth_verifier);

                final SharedPreferences.Editor edit = prefs.edit();
                edit.putString(TwitterConsts.ACCESS_TOKEN_PREFS_TOKEN_KEY, consumer.getToken());
                edit.putString(TwitterConsts.ACCESS_TOKEN_PREFS_SECRET_KEY, consumer.getTokenSecret());
                edit.commit();

                String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
                String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");

                consumer.setTokenWithSecret(token, secret);
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_LONG).show();
                //context.startActivity(new Intent(context,AndroidTwitterSample.class));

                //		executeAfterAccessTokenRetrieval();

                //Log.i(TAG, "OAuth - Access Token Retrieved");

            } catch (Exception e) {
                //   Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
            }

            return null;
        }
    }
    private class TweetsReceiverDelegateListener implements SearchTweets.TweetsReceiverDelegate{

        @Override
        public void onTweetsReceived(List<Status> tweetsReceived) {
            textViewTweet.setText(tweetsReceived.size()+" tweets Received");
        }
    }

}