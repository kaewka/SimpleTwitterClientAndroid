package com.codepath.apps.simpletwitter;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import com.codepath.apps.simpletwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{

	public TweetsArrayAdapter(Context context, ArrayList<Tweet> tweets) {
		super(context, R.layout.item_tweet, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		Tweet tweet = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
		}
		// Lookup view for data population
		ImageView ivUserImage = (ImageView) convertView.findViewById(R.id.ivUserImage);
		TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
		TextView tvTweetText = (TextView) convertView.findViewById(R.id.tvTweetText);
		TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
		
		ivUserImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		// Populate the data into the template view using the data object
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivUserImage);
		tvUserName.setText(tweet.getUser().getUserName());
		tvTweetText.setText(tweet.getTweetText());
		tvTime.setText(tweet.getCreateTime());
	
		// Return the completed view to render on screen
		return convertView;
	}
}
