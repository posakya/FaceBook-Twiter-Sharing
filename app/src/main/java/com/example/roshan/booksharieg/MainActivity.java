package com.example.roshan.booksharieg;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareDialog;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;


public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    LoginButton loginButton;
    private LikeView btnLike;
    Status status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        // App code
                        Toast.makeText(MainActivity.this, "Login Cancelled", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void shareLinks(View view) {

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Roshan")
                    .setContentDescription("Favourite")
                    .setContentUrl(Uri.parse("https://www.youtube.com/watch?v=1UCozTBfMEI"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    public void twitter_share(View view) {
        twitterPostWall();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void twitterPostWall() {



            //Twitter Conf.
            ConfigurationBuilder cb = new ConfigurationBuilder();
            cb.setDebugEnabled(true)
                    .setOAuthConsumerKey("4jG93bzVqxtP0nJkCKXikNmjT")
                    .setOAuthConsumerSecret("N8FeEScoAHckiYmSma2zQeKISokbbUhQeluqmLLGNmEZIyyt5z")
                    .setOAuthAccessToken("4069994594-VbBF9eGzcfvn9mIKRk25qwVGcQyeAXiZv04t50v")
                    .setOAuthAccessTokenSecret("l69u1tCQXG9Jva2ZC9KBocDPCNveLwsAUXy2plbp2unK8");
            TwitterFactory tf = new TwitterFactory(cb.build());
            final Twitter twitter = tf.getInstance();
            //  twitter.setOAuthConsumer("4jG93bzVqxtP0nJkCKXikNmjT", "N8FeEScoAHckiYmSma2zQeKISokbbUhQeluqmLLGNmEZIyyt5z");

            try {
                RequestToken requestToken = twitter.getOAuthRequestToken();

                Log.e("Request token: ", "" + requestToken.getToken());
                Log.e("Request token secret: ", "" + requestToken.getTokenSecret());
                AccessToken accessToken = null;


            } catch (IllegalStateException ie) {

                if (!twitter.getAuthorization().isEnabled()) {
                    Log.e("OAuth consumer key/secret is not set.", "");
                }
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            final String latestStatus = "Success isn't overnight. Its when everyday you get a little better than the day before. It all adds up.";

            //  Twitter twitter1 = TwitterFactory.getSingleton();


            new Thread(new Runnable() {
                public void run() {
                    try {
                        status = twitter.updateStatus(latestStatus);
                        Toast.makeText(MainActivity.this, ""+ status+ "has been updated", Toast.LENGTH_SHORT).show();
                    } catch (TwitterException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

    }
}