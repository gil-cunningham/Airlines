package com.example.gilcunningham.airlines.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.helper.AirlinesParserVolley;
import com.example.gilcunningham.airlines.network.AirlinesHttp;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Service/Helper to initiate REST requests, receive and parse responses,
 * and return (callback) a list of AirlineInfo
 */
public class AirlinesServiceVolley {

    private static final String REQUEST_TAG = "com.example.gilcunningham.airlines.GetAirlinesList";

    public static final String TAG = AirlinesServiceVolley.class.getSimpleName();

    private static AirlinesServiceVolley mService;

    private Context mAppContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public static synchronized AirlinesServiceVolley getService(Context context) {
        if (mService == null) {
            mService = new AirlinesServiceVolley(context);
        }
        return mService;
    }

    private AirlinesServiceVolley(Context context) {
        mAppContext = context.getApplicationContext();
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url, bitmap);
            }
        });
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mAppContext);
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(tag);
        getRequestQueue().add(req);
    }

    private void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    private void doJsonArrayRequest(String url, final AirlinesServiceCallback callback){

        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<AirlineInfo> airlines = AirlinesParserVolley.parseAirlinesForResult(response);
                        // order alphabetically
                        Collections.sort(airlines);

                        callback.onAirlinesServiceCallback(airlines);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        callback.onAirlinesServiceCallback(null);
                    }
                });

        addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }


    public void getAirlinesList(AirlinesServiceCallback mCallback)
    {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme("https")
                .authority(AirlinesHttp.API_HOST_URL)
                .appendPath("h")
                .appendPath("mobileapis")
                .appendPath("directory")
                .appendPath("airlines");

        Uri uri = builder.build();

        doJsonArrayRequest(uri.toString(), mCallback);
    }
}
