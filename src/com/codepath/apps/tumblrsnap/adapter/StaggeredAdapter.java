package com.codepath.apps.tumblrsnap.adapter;

import java.util.List;


import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.R.id;
import com.codepath.apps.tumblrsnap.R.layout;
import com.codepath.apps.tumblrsnap.models.Photo;
import com.codepath.apps.tumblrsnap.view.ScaleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class StaggeredAdapter extends ArrayAdapter<Photo> {

	//private ImageLoader mLoader;

	public StaggeredAdapter(Context context, List<Photo> photoList) {
		super(context,  R.layout.row_staggered_gridview, photoList);
		//mLoader = new ImageLoader(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Photo photo = this.getItem(position);
		View itemView;
		ScaleImageView ivImage;
		TextView tv ;
		ImageLoader imageLoader = ImageLoader.getInstance();
        if (convertView == null) {
    		LayoutInflater inflator = LayoutInflater.from(getContext());
    		itemView =  inflator.inflate(R.layout.row_staggered_gridview, parent, false);
    		ivImage = (ScaleImageView) itemView.findViewById(R.id.imageView1);
    		
    		itemView.setTag(ivImage);
        } else {
            itemView =  convertView;
        }
        ivImage = (ScaleImageView)itemView.getTag();// (ScaleImageView) itemView.findViewById(R.id.imageView1);
        tv = (TextView)itemView.findViewById(R.id.textView1);
        tv.setText("Position " + position);
       // mLoader.DisplayImage(getItem(position), ivImage);
        imageLoader.displayImage(photo.getPhotoUrl(), ivImage);
		return itemView;
	}

	
}
