package com.codepath.apps.tumblrsnap.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

public class TumblrEditView extends EditText {
 
    public TumblrEditView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
 
    }
 
    public TumblrEditView(Context context, AttributeSet attrs) {
        super(context, attrs);
 
    }
 
    public TumblrEditView(Context context) {
        super(context);
 
    }
 
 
     @Override  
     protected void onSelectionChanged(int start, int end) { 
    	 Log.d("On selected change ",start + " : " + end);
    	 
        //Toast.makeText(getContext(), "selStart is " + selStart + "selEnd is " + selEnd, Toast.LENGTH_LONG).show();
         } 
}
