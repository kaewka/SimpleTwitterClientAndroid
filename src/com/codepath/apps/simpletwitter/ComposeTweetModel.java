package com.codepath.apps.simpletwitter;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ComposeTweetModel extends DialogFragment implements OnEditorActionListener {

	private View layoutComposeTweet;
	private TwitterClient twitterClient;
	private EditText etTweetData;
	private TextView tvNoOfChars;
	private Button btnTweetData;
	private Button btnOnCancel;
	private static String mUserName;
	private static String mProfileImageUrl; 
	
	public interface ComposeTweetModelListener {
		void onFinishComposeDialog(String inputText);
	}


	public ComposeTweetModel() {
		// Empty constructor required for DialogFragment
	}

	public static ComposeTweetModel newInstance(String title, String userName, String profileImageUrl) {
		ComposeTweetModel frag = new ComposeTweetModel();
		Bundle args = new Bundle();
		args.putString("title", title);
		frag.setArguments(args);
	
		mUserName = userName;
		mProfileImageUrl = profileImageUrl;
		
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		layoutComposeTweet = inflater.inflate(R.layout.fragement_compose_tweet, container);

		etTweetData = (EditText) layoutComposeTweet.findViewById(R.id.etTweetData);
		InputFilter[] FilterArray = new InputFilter[1];
		FilterArray[0] = new InputFilter.LengthFilter(140);
		etTweetData.setFilters(FilterArray);

		btnTweetData = (Button) layoutComposeTweet.findViewById(R.id.btnTweet);
		btnOnCancel = (Button) layoutComposeTweet.findViewById(R.id.btnCancel);
		
		btnTweetData.setEnabled(false);
		
		tvNoOfChars = (TextView) layoutComposeTweet.findViewById(R.id.tvNoofChars);

		twitterClient = TwitterApplication.getRestClient();

		String title = getArguments().getString("title", "Compose Tweet");
		getDialog().setTitle(title);
		// Show soft keyboard automatically
		etTweetData.requestFocus();
		getDialog().getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		
		ImageView ivUserImage = (ImageView) layoutComposeTweet.findViewById(R.id.ivUserImage);
		ivUserImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		
		// Populate the data into the template view using the data object
		imageLoader.displayImage(mProfileImageUrl, ivUserImage);
		
		TextView tvUserInfo = (TextView) layoutComposeTweet.findViewById(R.id.tvUserInfo);
		tvUserInfo.setText(mUserName);

		etTweetData.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
				int currentCharCount = etTweetData.getText().toString().length();
				if(currentCharCount > 0) {
					btnTweetData.setEnabled(true);
				} else {
					btnTweetData.setEnabled(false);
				}
				tvNoOfChars.setText("" + (140 - currentCharCount));			
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		btnTweetData.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				String tweetData = etTweetData.getText().toString();

				twitterClient.postNewTweet((new JsonHttpResponseHandler(){

					@Override
					public void onSuccess(String response) {
						Log.d("debug compose tweet", response);
					}

					@Override
					public void onFailure(Throwable e, String s) {				
						Log.d("debug compose tweet", e.toString());

					}
				}), tweetData);

				etTweetData.setText("");
				
				ComposeTweetModelListener listener = (ComposeTweetModelListener) getActivity();
				listener.onFinishComposeDialog("from modal tweet");
				
				dismiss();
			}
		});

		btnOnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {				
				dismiss();
			}
		});

		return layoutComposeTweet;
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		ComposeTweetModelListener listener = (ComposeTweetModelListener) getActivity();
		
		if (actionId == EditorInfo.IME_NULL) {
			
			String tweetData = etTweetData.getText().toString();
			
			twitterClient.postNewTweet((new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(String response) {
					Log.d("debug compose tweet", response);
				}

				@Override
				public void onFailure(Throwable e, String s) {				
					Log.d("debug compose tweet", e.toString());

				}
			}), tweetData);

			etTweetData.setText("");
			dismiss();
			
			listener.onFinishComposeDialog("from modal tweet");
			
			return true;
		}
		
		return false;
	}
}
