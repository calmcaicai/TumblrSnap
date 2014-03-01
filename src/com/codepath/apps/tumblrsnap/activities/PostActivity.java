package com.codepath.apps.tumblrsnap.activities;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.TumblrClient;
import com.codepath.apps.tumblrsnap.adapter.BackgroundAdapter;
import com.codepath.apps.tumblrsnap.adapter.EmojiAdapter;
import com.codepath.apps.tumblrsnap.adapter.FontAdapter;
import com.codepath.apps.tumblrsnap.models.Photo;
import com.codepath.apps.tumblrsnap.models.User;
import com.codepath.apps.tumblrsnap.view.TumblrEditView;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lucasr.twowayview.TwoWayView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.view.*;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;
import android.widget.RelativeLayout.LayoutParams;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.DisplayMetrics;
import android.util.Log;


public class PostActivity extends FragmentActivity implements EmojiconGridFragment.OnEmojiconClickedListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener  {
	private static final String LOGCAT = "PostActivity";
	private EmojiconEditText editPanel;
	private TumblrClient client;
	private TabHost tabHost ;
	//private int emojiCount;
	SpannableStringBuilder ssb ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//emojiCount = 0;
		ssb = new SpannableStringBuilder();
		setContentView(R.layout.activity_post);
		editPanel = (EmojiconEditText) findViewById(R.id.editPanel);
		OnTouchListener focusHandler = new OnTouchListener() {
		    @Override
		    public boolean onTouch(View view, MotionEvent event) {
		    	Log.d(LOGCAT,"request focus..");
		        view.requestFocusFromTouch();
		        return true;
		    }
		};

		editPanel.setOnTouchListener(focusHandler); 
		//editPanel1.setOnTouchListener(focusHandler); 
		tabHost = (TabHost) findViewById(R.id.tabHost);
		setButtomTabs();
		

	}

	public void postText(View view) {
		String body = editPanel.getText().toString();
		client = ((TumblrClient) TumblrClient.getInstance(TumblrClient.class,getApplicationContext()));
		client.createTextPost(User.currentUser().getBlogHostname(), "Test", body, new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(int code, JSONObject response) {
				Log.d("AfterPost", code + ":" + response.toString());
			}});
	 }
	private void setButtomTabs() {
		
		tabHost.setup();

		initEmojiTab();
		initBgTab();
		initTypeface();
		
		TabSpec spec1 = tabHost.newTabSpec("EmojiTab");
		spec1.setContent(R.id.tab1);
		spec1.setIndicator("Emoji");

		TabSpec spec2 = tabHost.newTabSpec("BackgroundTab");
		spec2.setIndicator("Background");
		spec2.setContent(R.id.tab2);

		TabSpec spec3 = tabHost.newTabSpec("FontTab");
		spec3.setContent(R.id.tab3);
		spec3.setIndicator("Font");
		tabHost.addTab(spec1);
		tabHost.addTab(spec2);
		tabHost.addTab(spec3);
	}

	private void initEmojiTab() {
		Bitmap[] emojiItems = new Bitmap[20];
		for (int i = 0; i < 20; i++) {
			emojiItems[i] = getBitmapFromAsset(getApplicationContext(),
					"one_emot.jpg");
		}
		EmojiAdapter emojiAdapter = new EmojiAdapter(getApplicationContext(),
				emojiItems);
		TwoWayView emojiTwoWayView = (TwoWayView) findViewById(R.id.tvvEmoji);
		emojiTwoWayView.setAdapter(emojiAdapter);
		emojiTwoWayView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				if (position == 0) {
					addEmoji();
				}
			}
		});
	}

	private void initBgTab() {
		Integer[] bgItems = new Integer[12];

		bgItems[0] = Color.BLACK;
		bgItems[1] = Color.BLUE;
		bgItems[2] = Color.CYAN;
		bgItems[3] = Color.DKGRAY;
		bgItems[4] = Color.GRAY;
		bgItems[5] = Color.GREEN;
		bgItems[6] = Color.LTGRAY;
		bgItems[7] = Color.MAGENTA;
		bgItems[8] = Color.RED;
		bgItems[9] = Color.TRANSPARENT;
		bgItems[10] = Color.WHITE;
		bgItems[11] = Color.YELLOW;
		final BackgroundAdapter backgroundAdapter = new BackgroundAdapter(
				getApplicationContext(), bgItems);
		TwoWayView bgTwoWayView = (TwoWayView) findViewById(R.id.tvvBg);
		bgTwoWayView.setAdapter(backgroundAdapter);
		bgTwoWayView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
					editPanel.setBackgroundColor(backgroundAdapter.getItem(position));
			}
		});
	}

	
	private void initTypeface(){
		Typeface[] typefaceItems = new Typeface[5];

		typefaceItems[0] = Typeface.DEFAULT;
		typefaceItems[1] = Typeface.DEFAULT_BOLD;
		typefaceItems[2] = Typeface.MONOSPACE;
		typefaceItems[3] = Typeface.SANS_SERIF;
		typefaceItems[4] = Typeface.SERIF;
		
		FontAdapter fontAdapter = new FontAdapter(
				getApplicationContext(), typefaceItems);
		TwoWayView fontTwoWayView = (TwoWayView) findViewById(R.id.tvvFont);
		fontTwoWayView.setAdapter(fontAdapter);
		fontTwoWayView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

			}
		});
	}
	public static Bitmap getBitmapFromAsset(Context context, String strName) {
		AssetManager assetManager = context.getAssets();

		InputStream istr;
		Bitmap bitmap = null;
		try {

			istr = assetManager.open(strName);
			bitmap = BitmapFactory.decodeStream(istr);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return bitmap;
	}

	
	
	private void addEmoji() {
		ssb = new SpannableStringBuilder(editPanel.getText() + " ");
		Bitmap smiley = getBitmapFromAsset(getApplicationContext(),
				"one_emot.jpg");
		Log.d("SpanLength", "Before" + ssb.length());
		ssb.setSpan(new ImageSpan(getApplicationContext(), smiley), editPanel
				.getText().length() - 1 , editPanel.getText().length() ,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		Log.d("SpanLength", "After" + ssb.length());
		editPanel.setText(ssb, BufferType.SPANNABLE);
	}

	@Override
	public void onEmojiconBackspaceClicked(View v) {
		EmojiconsFragment.backspace(editPanel);
	}

	@Override
	public void onEmojiconClicked(Emojicon emojicon) { 
		 EmojiconsFragment.input(editPanel, emojicon);
	}
}
