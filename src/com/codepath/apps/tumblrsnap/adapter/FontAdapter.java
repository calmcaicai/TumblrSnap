package com.codepath.apps.tumblrsnap.adapter;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.R.id;
import com.codepath.apps.tumblrsnap.R.layout;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FontAdapter extends ArrayAdapter<Typeface> {

	public FontAdapter(Context context, Typeface[] objects) {
		super(context, 0, objects);
	}

	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.font_item, null);
        }

        Typeface typeface = getItem(position);
        TextView tvFont = (TextView) view.findViewById(R.id.tvFont);
        tvFont.setTypeface(typeface);
		return view;
	}

}
