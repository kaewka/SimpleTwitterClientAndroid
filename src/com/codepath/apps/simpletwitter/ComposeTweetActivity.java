package com.codepath.apps.simpletwitter;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.codepath.apps.simpletwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class ComposeTweetActivity extends Activity {

	private TwitterClient twitterClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);

		twitterClient =  TwitterApplication.getRestClient();
	}	

	public void saveTweet(View v) {

		/*String data = ((EditText) findViewById(R.id.etcomposeTweet)).getText().toString();

		twitterClient.postNewTweet((new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(String response) {
				Log.d("debug", response);
			}

			@Override
			public void onFailure(Throwable e, String s) {				
				Log.d("debug", e.toString());

			}
		}), data);


		setResult(RESULT_OK); // set result code and bundle data for response
		finish(); // closes the activity, pass data to parent
*/	}
}