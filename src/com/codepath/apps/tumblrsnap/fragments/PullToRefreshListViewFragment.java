package com.codepath.apps.tumblrsnap.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.support.v4.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.tumblrsnap.EndlessScrollListener;
import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.TumblrClient;
import com.codepath.apps.tumblrsnap.adapter.PhotosAdapter;
import com.codepath.apps.tumblrsnap.models.Photo;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.markupartist.android.widget.PullToRefreshListView;
import com.markupartist.android.widget.PullToRefreshListView.OnRefreshListener;

public class PullToRefreshListViewFragment extends Fragment {

	String tag = "PullToRefreshListViewFragment";
	private PullToRefreshListView pullToRefreshListView;
	private TumblrClient client;
	private ArrayList<Photo> photos;
	private PhotosAdapter photosAdapter;
	//private static int offset = 0;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_pulltorefresh_listview, container, false);
		// setHasOptionsMenu(true);
		return view;
	}

	

	// ListView lvPhotos;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Log.d("PullToRefreshListViewFragment","onActivityCreated");
		setupView();
	}

	private void setupView(){
		pullToRefreshListView = (PullToRefreshListView) getView().findViewById(
				R.id.lvPullToRefresh);

		client = ((TumblrClient) TumblrClient.getInstance(TumblrClient.class,
				getActivity()));
		photos = new ArrayList<Photo>();
		photosAdapter = new PhotosAdapter(getActivity(), photos);

		pullToRefreshListView.setAdapter(photosAdapter);
		
		pullToRefreshListView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				Log.d(tag,"totalItemsCount : "+ totalItemsCount);
				loadPhotos(getOffset());
			}		
		});
		
	}
	private int getOffset() {
		return photosAdapter.getCount()  > 0 ? photosAdapter.getCount()  : 0;
	}
	private void loadPhotos(int offset) {
		Log.d(tag,"loading photo : " + offset);
		client.getDashboardPhotos(offset,  new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(int code, JSONObject response) {
				try {
					
					JSONArray photosJson = response.getJSONObject("response")
							.getJSONArray("posts");
					//photos.addAll(Photo.fromJson(photosJson));
					//photosAdapter.clear();
					photosAdapter.addAll(Photo.fromJson(photosJson));
					pullToRefreshListView.onRefreshComplete();
				} catch (JSONException e) {
					Log.d("loadPhotos2", response.toString());
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Throwable arg0) {
				Log.d("loadPhotos", arg0.toString());
			}
		});
		
		
	}

	
}
