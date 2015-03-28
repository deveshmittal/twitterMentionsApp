package com.devesh.trendsapp;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by deveshmittal1 on 16/03/15.
 */
public class SearchTweets {
    public interface TweetsReceiverDelegate{
        public void onTweetsReceived(List<Status> tweetsReceived);
    }
    static TweetsReceiverDelegate listener=null;
    private static SharedPreferences mSharedPreferences;
    public static void searchOnKeyword(String str, Context context ,TweetsReceiverDelegate delegate){
        listener = delegate;
       // AccessToken access_token =null;
      //  if(( access_token =getAccessToken(context))==null){

     //   }
                ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        		configurationBuilder.setOAuthConsumerKey(TwitterConsts.CONSUMER_KEY);
        		configurationBuilder.setOAuthConsumerSecret(TwitterConsts.CONSUMER_SECRET);
                configurationBuilder.setOAuthAccessToken(TwitterConsts.ACCESS_TOKEN_TOKEN);
                configurationBuilder.setOAuthAccessTokenSecret(TwitterConsts.ACCESS_TOKEN_SECRET);
        		//Configuration configuration = configurationBuilder.build();
        TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
        Twitter twitter = tf.getInstance();
                //Twitter twitter = new TwitterFactory(configuration).getInstance();
           //     twitter.setOAuthAccessToken(access_token);

            final QueryResult queryResult;

/*

                new TwitterSearchAPITask(twitter,str,new TwitterSearchAPITask.SearchCompleteDelegate() {
                    @Override
                    public void onSearchCompleted(QueryResult result) {
                        Query query;
                        //queryResult = (QueryResult)result;
                        do {
                            List<Status> tweets = result.getTweets();
                            listener.onTweetsReceived(tweets);

                            for (Status tweet : tweets) {

                                Log.i("DEVESH", "@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                            }
                        } while ((result.nextQuery()) != null);

                    }
                }).execute();
*/

    }
    private static AccessToken getAccessToken(Context context) {
        mSharedPreferences = context.getSharedPreferences(TwitterConsts.PREFERENCE_NAME, Context.MODE_PRIVATE);
        String token = mSharedPreferences.getString("accessTokenToken", "");
        String tokenSecret = mSharedPreferences.getString("accessTokenSecret", "");
        if (token!=null && tokenSecret!=null && !"".equals(tokenSecret) && !"".equals(token)){
            return new AccessToken(token, tokenSecret);
        }
        return null;
    }
    private static void storeAccessToken(AccessToken a, Context context) {
        SharedPreferences settings = context.getSharedPreferences(TwitterConsts.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("accessTokenToken", a.getToken());
        editor.putString("accessTokenSecret", a.getTokenSecret());
        editor.commit();
    }

}
