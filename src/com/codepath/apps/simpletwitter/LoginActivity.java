package com.codepath.apps.simpletwitter;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.codepath.apps.simpletwitter.R;
import com.codepath.apps.simpletwitter.models.User;
import com.codepath.oauth.OAuthLoginActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

public class LoginActivity extends OAuthLoginActivity<TwitterClient> {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
	}

	// Inflate the menu; this adds items to the action bar if it is present.
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	// OAuth authenticated successfully, launch primary authenticated activity
	// i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
    	// Intent i = new Intent(this, PhotosActivity.class);
    	// startActivity(i);
    	
    	Intent timelineActivity = new Intent(this, TimelineActivity.class);
    	startActivity(timelineActivity);
    	

				
				
				Intent i = new Intent(LoginActivity.this, TimelineActivity.class);
				startActivity(i);
		
    	//Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }
    
    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }
    
    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

}
