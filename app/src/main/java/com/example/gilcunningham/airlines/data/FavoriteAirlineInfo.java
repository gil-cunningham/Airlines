package com.example.gilcunningham.airlines.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.gilcunningham.airlines.helper.AirlinesParser;
import com.example.gilcunningham.airlines.helper.AirlinesStorage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gil.cunningham on 9/21/2016.
 * Favorite airlines list
 * Add / remove favorites and event is broadcast to receivers
 * Stores favorites in JSON format
 */
public class FavoriteAirlineInfo {

    public static String TAG = FavoriteAirlineInfo.class.getSimpleName();
    public static String FAVORITES_UPDATED_INTENT = "FAVORITES_UPDATED_INTENT";

    private static FavoriteAirlineInfo mFavoritesInfo = null;

    private Context mContext;

    private ArrayList<AirlineInfo> mFavoritesList;
    private Map <String, Boolean> mFavoritesMap;

    public static FavoriteAirlineInfo getInstance(Context context)
    {
        if (mFavoritesInfo == null) {
            mFavoritesInfo = new FavoriteAirlineInfo(context);
        }
        return mFavoritesInfo;
    }

    private FavoriteAirlineInfo(Context context)
    {
        mContext = context;
        String json = AirlinesStorage.getStorage(context).getFavoriteAirlinesJson();
        mFavoritesList = AirlinesParser.parseAirlinesForResult(json);
        Collections.sort(mFavoritesList);

        // init lookup map
        mFavoritesMap = new HashMap<String, Boolean>();
        for (AirlineInfo ai : mFavoritesList) {
            mFavoritesMap.put(ai.getCode(), true);
        }
    }

    public ArrayList<AirlineInfo> getFavorites()
    {
        return mFavoritesList;
    }

    public void setFavorite(AirlineInfo airlineInfo, boolean isFavorite)
    {
        // new entry add to favorites list and map, store result
        if (!mFavoritesMap.containsKey(airlineInfo.getCode()) && isFavorite) {
            mFavoritesList.add(airlineInfo);
            Collections.sort(mFavoritesList);
            mFavoritesMap.put(airlineInfo.getCode(), true);

            storeFavorites();
        }
        // existing entry, no longer a favorite,
        // remove from list and map, store result
        else if (!isFavorite) {
            String code = airlineInfo.getCode();
            mFavoritesMap.remove(code);
            for (AirlineInfo ai : mFavoritesList) {
                if (ai.getCode().equals(code)) {

                    mFavoritesList.remove(ai);
                    storeFavorites();

                    break;
                }
            }
        }
    }

    public boolean isFavorite(AirlineInfo info)
    {
        return (mFavoritesMap.containsKey(info.getCode()));
    }

    private void storeFavorites()
    {
        try {
            JSONArray favAirlines = new JSONArray();

            for (AirlineInfo ai : mFavoritesList) {
                JSONObject airline = new JSONObject();
                airline.put(AirlinesParser.LOGO, ai.getLogo());
                airline.put(AirlinesParser.NAME, ai.getName());
                airline.put(AirlinesParser.WEBSITE, ai.getWebSite());
                airline.put(AirlinesParser.PHONE_NUM, ai.getPhoneNumber());
                airline.put(AirlinesParser.CODE, ai.getCode());

                favAirlines.put(airline);
            }
            // persist
            AirlinesStorage.getStorage(mContext).setFavoriteAirlinesJson(favAirlines.toString());

        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        // broadcast change in favorites
        Intent i = new Intent(FAVORITES_UPDATED_INTENT);
        i.setAction(FAVORITES_UPDATED_INTENT);
        mContext.sendBroadcast(i);
    }
}
