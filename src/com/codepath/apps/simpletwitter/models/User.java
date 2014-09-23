package com.codepath.apps.simpletwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

	private String userName;
	private long userId;
	private String location;
	private String friends;
	private String followersCount;
	private String profileImageUrl;
	private String profileBackgroundColor;

	public User(JSONObject jsonObj) {
		try {
			this.userName = jsonObj.getString("name");
			this.userId = jsonObj.getLong("id");
			this.location = jsonObj.getString("location");
			this.friends = jsonObj.getString("friends_count");
			this.followersCount = jsonObj.getString("followers_count");
			this.profileImageUrl = jsonObj.getString("profile_image_url");
			this.profileBackgroundColor = jsonObj.getString("profile_background_color");

		} catch (JSONException e) {			
			e.printStackTrace();
		}
	}
	
	public String getUserName() {
		return userName;
	}

	public long getUserId() {
		return userId;
	}

	public String getLocation() {
		return location;
	}

	public String getFriends() {
		return friends;
	}

	public String getFollowersCount() {
		return followersCount;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileBackgroundColor() {
		return profileBackgroundColor;
	}	
}
