package com.codepath.apps.tumblrsnap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;

import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.builder.api.TumblrApi;

import org.scribe.model.OAuthBaseRequest;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class TumblrClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TumblrApi.class;
    public static final String REST_URL = "http://api.tumblr.com/v2";
    //public static final String REST_CONSUMER_KEY = "BcnUeYPIxBaVCz5sYcs4SkytRqM8azgLclb1PUpeFcknic9RYY";
    //public static final String REST_CONSUMER_SECRET = "FoiHHbknPRFeyiaBpOxizSGzbYsflp6DiFfwBi85kCYEznKGGh";
    
    public static final String REST_CONSUMER_KEY = "CQzO4BkdVYyhc9TtojvPqGnCdUmixCdubyT0vqFxwWMvcLw99a";
    
    public static final String REST_CONSUMER_SECRET ="qstsYS2BfQAP0BcCG4IMbt0a4FAiJstBmOitUFVvO1YgqxalBs";
    public static final String REST_CALLBACK_URL = "oauth://TumblrTag";

    public static final String photoTag = "";
	
    public TumblrClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET, REST_CALLBACK_URL);
       // this.editor.
    }

    public void getTaggedPhotos(AsyncHttpResponseHandler handler) {
        RequestParams params = new RequestParams();
        params.put("tag", "cptumblrsnap");
        params.put("limit", "20");
        //params.put("api_key", REST_CONSUMER_KEY);
        client.get(getApiUrl("tagged"), params, handler);
    }
    
    public void getDashboardPhotos(int offset,  AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
        params.put("type", "photo");
        params.put("offset", String.valueOf(offset));
        params.put("limit", "20");
        params.put("api_key", REST_CONSUMER_KEY);
        client.get(getApiUrl("user/dashboard"), params, handler);
    }
    
    public void getUserPosts(String hostname,  AsyncHttpResponseHandler handler) {
    	String path = "blog/"+hostname+"/posts/";
        client.get(getApiUrl(path),  handler);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        client.get(getApiUrl("user/info"), null, handler);
    }
    private OAuthRequest constructPost() {
        String url = "https://api.tumblr.com/v2/blog/calmcaicai.tumblr.com/post";
        OAuthRequest request = new OAuthRequest(Verb.POST, url);

        
        return request;
    }
    public void createTextPost(String blog, String title, String body, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
    	params.put("type", "text");
    	params.put("title", title);
    	params.put("body", body);//"<!DOCTYPE html><html><body><h1>My First Heading</h1><p>My first paragraph.</p></body></html>");
    	
    	OAuthBaseRequest request = constructPost();
		client.signRequest(request);
		client.post(request.getCompleteUrl(), params,handler);
    }
    
    public void createPhotoPost(String blog, File file, AsyncHttpResponseHandler handler) {
    	RequestParams params = new RequestParams();
    	params.put("api_key",REST_CONSUMER_KEY);
    	params.put("type", "photo");
    	params.put("tags", "cptumblrsnap");
    	try { 
			params.put("data", file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	client.post(getApiUrl(String.format("blog/%s/post?type=photo&tags=cptumblrsnap", blog)), params, handler);
    }
      
    public void createPhotoPost(String blog, Bitmap bitmap, final AsyncHttpResponseHandler handler) {   
        RequestParams params = new RequestParams();
    	params.put("type", "photo");
    	params.put("tags", "cptumblrsnap");
    	
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] bytes = stream.toByteArray();
    	params.put("data", new ByteArrayInputStream(bytes), "image.png");
    	
    	client.post(getApiUrl(String.format("blog/%s/post?type=photo&tags=cptumblrsnap", blog)), params, handler);
    }
}
