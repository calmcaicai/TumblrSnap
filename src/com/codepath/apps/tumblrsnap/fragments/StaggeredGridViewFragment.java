package com.codepath.apps.tumblrsnap.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.StaggeredGridViewListener;
import com.codepath.apps.tumblrsnap.TumblrClient;
import com.codepath.apps.tumblrsnap.activities.MainActivity;
import com.codepath.apps.tumblrsnap.adapter.StaggeredAdapter;
import com.codepath.apps.tumblrsnap.models.Photo;
import com.codepath.apps.tumblrsnap.view.StaggeredGridView;
import com.codepath.apps.tumblrsnap.view.StaggeredGridView.OnScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.codepath.apps.tumblrsnap.EndlessScrollListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

public class StaggeredGridViewFragment extends Fragment {

	StaggeredGridView dashboard;
	TumblrClient client;
	ArrayList<Photo> photoItems;
	StaggeredAdapter adapter;
	//OnScrollListener mScrollListener;
	
	
	final String TAG = "StaggeredGridViewFragment";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_stagger_gridview,
				container, false);
		// setHasOptionsMenu(true);
		return view;
	}

	// ListView lvPhotos;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupView();
		fillDashboard(getOffset());
	}

	private void fillDashboard(int offset) {
		client.getDashboardPhotos(0, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int code, JSONObject response) {
				try {

					JSONArray photosJson = response.getJSONObject("response")
							.getJSONArray("posts");
					//adapter.clear();
					adapter.addAll(Photo.fromJson(photosJson));
					// pullToRefreshListView.onRefreshComplete();
				} catch (JSONException e) {
					Log.d("fillDashboard", response.toString());
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("fillDashboard", arg0.toString());
			}
		});

	}

	private int getOffset() {
		return (adapter == null || adapter.getCount() < 0) ? 0 :adapter.getCount();
	}
	private void setupView() {
		dashboard = (StaggeredGridView) getView().findViewById(
				R.id.sgvDashboard);
		client = ((TumblrClient) TumblrClient.getInstance(TumblrClient.class,
				getActivity()));
		photoItems = new ArrayList<Photo>();

		int margin = getResources().getDimensionPixelSize(R.dimen.margin);
		dashboard.setItemMargin(margin); // set the GridView margin
		dashboard.setPadding(margin, 0, margin, 0); // have the margin on the
													// sides as well
		adapter = new StaggeredAdapter(getActivity(), photoItems);
		dashboard.setAdapter(adapter);
		
	
		dashboard.setOnScrollListener(new StaggeredGridViewListener(){
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// TODO Auto-generated method stub
				fillDashboard(getOffset());
			}
		});
		
		//dashboard
	}
	
}
