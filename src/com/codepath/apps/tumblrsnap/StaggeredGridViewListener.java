package com.codepath.apps.tumblrsnap;

import com.codepath.apps.tumblrsnap.view.StaggeredGridView.OnScrollListener;

import android.util.Log;
import android.view.ViewGroup;
import android.widget.AbsListView;

public abstract class StaggeredGridViewListener implements OnScrollListener {
	// The minimum amount of items to have below your current scroll position
	// before loading more.
	private int visibleThreshold = 5;
	// The current offset index of data you have loaded
	private int currentPage = 0;
	// The total number of items in the dataset after the last load
	private int previousTotalItemCount = 0;
	// True if we are still waiting for the last set of data to load.
	private boolean loading = true;
	// Sets the starting page index
	private int startingPageIndex = 0;

	private String TAG = "StaggeredGridViewListener";

	public StaggeredGridViewListener() {
	}

	public StaggeredGridViewListener(int visibleThreshold) {
		this.visibleThreshold = visibleThreshold;
	}

	public StaggeredGridViewListener(int visibleThreshold, int startPage) {
		this.visibleThreshold = visibleThreshold;
		this.startingPageIndex = startPage;
		this.currentPage = startPage;
	}

	public void onScroll(ViewGroup view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.d(TAG, "[onScroll] firstVisibleItem:" + firstVisibleItem
				+ " visibleItemCount:" + visibleItemCount + " totalItemCount:"
				+ totalItemCount);

		if (!loading && (totalItemCount < previousTotalItemCount)) {
			this.currentPage = this.startingPageIndex;
			this.previousTotalItemCount = totalItemCount;
			if (totalItemCount == 0) {
				this.loading = true;
			}
		}

		if (loading) {
			if (totalItemCount > previousTotalItemCount) {
				loading = false;
				previousTotalItemCount = totalItemCount;
				currentPage++;
			}
		}

		if (!loading
				&& (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
			onLoadMore(currentPage + 1, totalItemCount);
			loading = true;
		}

	}

	// Defines the process for actually loading more data based on page
	public abstract void onLoadMore(int page, int totalItemsCount);

	public void onScrollStateChanged(ViewGroup view, int scrollState) {
		Log.d(TAG, "[onScrollStateChanged] scrollState:" + scrollState);
		switch (scrollState) {
		case SCROLL_STATE_IDLE:
			Log.d(TAG, "SCROLL_STATE_IDLE");
			break;

		case SCROLL_STATE_FLING:
			Log.d(TAG, "SCROLL_STATE_FLING");
			break;

		case SCROLL_STATE_TOUCH_SCROLL:
			Log.d(TAG, "SCROLL_STATE_TOUCH_SCROLL");
			break;

		default:
			break;
		}

	}

}
