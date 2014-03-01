package com.codepath.apps.tumblrsnap.adapter;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.R.id;
import com.codepath.apps.tumblrsnap.R.layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;


public class BackgroundAdapter extends ArrayAdapter<Integer> {

	public BackgroundAdapter(Context context, Integer[] objects) {
		super(context, 0, objects);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.emoji_item, null);
        }

        Integer color = getItem(position);
        ImageView ivEmoji = (ImageView) view.findViewById(R.id.ivEmoji);
        ivEmoji.setBackgroundColor(color);
		return view;
	}

}
