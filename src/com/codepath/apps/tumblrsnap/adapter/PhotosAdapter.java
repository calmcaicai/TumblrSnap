package com.codepath.apps.tumblrsnap.adapter;

import java.util.List;

import android.content.Context;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.apps.tumblrsnap.R;
import com.codepath.apps.tumblrsnap.R.id;
import com.codepath.apps.tumblrsnap.R.layout;
import com.codepath.apps.tumblrsnap.models.Photo;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PhotosAdapter extends ArrayAdapter<Photo> {

    public PhotosAdapter(Context context, List<Photo> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) getContext().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.photo_item, null);
        }

        Photo photo = getItem(position);

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        tvName.setText(photo.getBlogName()+"::"+photo.getPhotoCount());

        TextView tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
        tvTimestamp.setText(DateUtils.getRelativeTimeSpanString(1000 * photo
                .getTimestamp()));

        ImageView ivProfile = (ImageView) view.findViewById(R.id.ivProfile);
        ivProfile.setImageResource(android.R.color.transparent);
        ImageLoader.getInstance().displayImage(photo.getAvatarUrl(), ivProfile);

        ImageView ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
        ivPhoto.setImageResource(android.R.color.transparent);

        // Set the dimensions of the ImageView, so it will be resize immediately
        // before the photo
        // finishes downloading.
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ivPhoto
                .getLayoutParams();
        lp.height = (photo.getPhotoHeight()*420)/photo.getPhotoWidth() ; // photo.getPhotoHeight() * 2;
        lp.width = 420; // photo.getPhotoWidth() * 2;
        ivPhoto.setLayoutParams(lp);
        
        ImageLoader.getInstance().displayImage(photo.getPhotoUrl(), ivPhoto);
        StringBuilder sb = new StringBuilder();
        for(String tag : photo.getTags()){
        	sb.append("#"+tag+" ");
        }
        TextView tags = (TextView) view.findViewById(R.id.tvTags);
        tags.setText(sb.toString());
        return view;
    }
}
