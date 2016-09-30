package com.example.gilcunningham.airlines.service;

import android.support.annotation.Nullable;

import com.example.gilcunningham.airlines.data.AirlineInfo;

import java.util.ArrayList;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Basic callback interface for handling service responce from AirlinesService
 */
public interface AirlinesServiceCallback {

    void onAirlinesServiceCallback(@Nullable ArrayList<AirlineInfo> airlines);
}
