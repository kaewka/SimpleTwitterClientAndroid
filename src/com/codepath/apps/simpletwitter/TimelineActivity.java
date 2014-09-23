package com.codepath.apps.simpletwitter;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.simpletwitter.ComposeTweetModel.ComposeTweetModelListener;
import com.codepath.apps.simpletwitter.models.Tweet;
import com.codepath.apps.simpletwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends FragmentActivity implements ComposeTweetModelListener {

	private static final int REQUEST_CODE = 1;
	private TwitterClient twitterClient;
	private TweetsArrayAdapter tweetArrayAdapter;
	private ArrayList<Tweet> tweetsList;
	//private ListView lvTweets;
	PullToRefreshListView lvTweets;
	private ComposeTweetModel composeTweetModel;
	private boolean isFresh;
	private long maxId = 0;
	private User currentUser = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		tweetsList = new ArrayList<Tweet>();
		tweetArrayAdapter = new TweetsArrayAdapter(this, tweetsList);

		twitterClient = TwitterApplication.getRestClient();
		//lvTweets = (ListView) findViewById(R.id.lvTweets);
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		lvTweets.setAdapter(tweetArrayAdapter);
		isFresh = true;
		//populateTimeline(); 
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your AdapterView
				customLoadMoreDataFromApi(page);  
				// or customLoadMoreDataFromApi(totalItemsCount); 
			}
		});
		
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() { 
				tweetArrayAdapter.clear();
				isFresh = true;
				maxId = 0;
				populateTimeline();		
			}
		});
	}
	
	public void customLoadMoreDataFromApi(int offset) {
		populateTimeline();
	}

	public void populateTimeline() {
		
		twitterClient.getHomeTimeLine(new JsonHttpResponseHandler(){

			@Override
			public void onSuccess(JSONArray json) {
				Log.d("debug", json.toString());

				tweetArrayAdapter.clear(); 
				tweetArrayAdapter.addAll(Tweet.parseJsonArray(json, isFresh));
				maxId = Tweet.getLowestId();
		
				isFresh = false;
				lvTweets.onRefreshComplete();
			}

			@Override
			public void onFailure(Throwable e, String s) {				
				Log.d("debug", e.toString());

			}
		}, maxId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_menu, menu);

		return super.onCreateOptionsMenu(menu); 
	}


	public void onComposeIconClick(MenuItem mi) {
		//Toast.makeText(this, "clicked compose", Toast.LENGTH_SHORT).show();
		/*
		Intent i = new Intent(this,ComposeTweetActivity.class);
		startActivityForResult(i, REQUEST_CODE);
		*/
		launchComposeDialog();
	}

	private void launchComposeDialog() {
	
		
    	TwitterClient client = TwitterApplication.getRestClient();

		client.getVerifiedCredentials(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				
				try {
					composeTweetModel = ComposeTweetModel.newInstance("Compose Tweet", json.getString("name"),  json.getString("profile_image_url"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				FragmentManager fm = getSupportFragmentManager();
		    	TwitterClient client = TwitterApplication.getRestClient();
			    composeTweetModel.show(fm, "fragment_compose_tweet");

			
			}
		});
		
	}

	@Override
	public void onFinishComposeDialog(String inputText) {
		//Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
		
		tweetArrayAdapter.clear();
		isFresh = true;
		maxId = 0;
		populateTimeline();
	}
}
