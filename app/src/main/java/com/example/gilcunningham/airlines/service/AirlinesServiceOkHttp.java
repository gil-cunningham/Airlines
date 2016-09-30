package com.example.gilcunningham.airlines.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.helper.AirlinesParser;
import com.example.gilcunningham.airlines.helper.AirlinesStorage;
import com.example.gilcunningham.airlines.network.AirlinesRestClient;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Service/Helper to initiate REST requests, receive and parse responses,
 * and return (callback) a list of AirlineInfo
 */
public class AirlinesServiceOkHttp {

    private static final String TAG = AirlinesServiceOkHttp.class.getSimpleName();

    private Context mContext;
    private AirlinesServiceCallback mCallback;

    public static AirlinesServiceOkHttp newService(Context context, AirlinesServiceCallback callback) {

        return new AirlinesServiceOkHttp(context, callback);
    }

    private AirlinesServiceOkHttp(Context context, AirlinesServiceCallback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void getAirlinesList()
    {
        mContext.registerReceiver(mReceiver, new IntentFilter(AirlinesRestClient.AIRLINES_CALLBACK_INTENT));
        new AirlinesRestClient(mContext).doGetAirlinesList();
    }

    // receiver to listen for REST client response
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String response = intent.getStringExtra(AirlinesRestClient.AIRLINES_JSON_EXTRA);

                if (AirlinesRestClient.OK_RESPONSE.equalsIgnoreCase(response)) {
                    String json = AirlinesStorage.getStorage(context).getAirlinesJson();
                    ArrayList<AirlineInfo> airlineList = AirlinesParser.parseAirlinesForResult(json);
                    Collections.sort(airlineList);
                    mCallback.onAirlinesServiceCallback(airlineList);
                }
                else {
                    mCallback.onAirlinesServiceCallback(new ArrayList<AirlineInfo>());
                }
            }
            finally {
                mContext.unregisterReceiver(mReceiver);
            }
        }
    };
}
