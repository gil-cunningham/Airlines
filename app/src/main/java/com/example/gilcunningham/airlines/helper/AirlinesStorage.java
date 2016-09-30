package com.example.gilcunningham.airlines.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by gil.cunningham on 9/20/2016.
 * Persistence for all airlines and favorite airlines list
 */
public class AirlinesStorage {

    private static final String STORAGE_FILE = "AirlinesStorage";

    private static AirlinesStorage mStorage = null;
    private Context mContext = null;

    private String mAirlinesJson = null;
    private String mFavoriteAirlinesJson = null;

    private static final String KEY_AIRLINES_JSON = "AIRLINES_JSON";
    private static final String DEFAULT_AIRLINES_JSON = null;

    private static final String KEY_FAVORITES_AIRLINES_JSON = "FAVORITES_AIRLINES_JSON";
    private static final String DEFAULT_FAVORITES_AIRLINES_JSON = null;

    public static AirlinesStorage getStorage(Context context)
    {
        if (mStorage == null) {
            mStorage = new AirlinesStorage(context);
        }

        return mStorage;
    }

    private AirlinesStorage(Context context)
    {
        mContext = context;

        SharedPreferences settings = mContext.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE);

        mAirlinesJson = settings.getString(KEY_AIRLINES_JSON, DEFAULT_AIRLINES_JSON);
        mFavoriteAirlinesJson = settings.getString(KEY_FAVORITES_AIRLINES_JSON, DEFAULT_FAVORITES_AIRLINES_JSON);
    }

    public void setAirlinesJson(String airlinesJson)
    {
        mAirlinesJson = airlinesJson;
        commit(KEY_AIRLINES_JSON, mAirlinesJson);
    }

    public String getAirlinesJson()
    {
        return mAirlinesJson;
    }

    public void setFavoriteAirlinesJson(String favoriteAirlinesJson)
    {
        mFavoriteAirlinesJson = favoriteAirlinesJson;
        commit(KEY_FAVORITES_AIRLINES_JSON, mFavoriteAirlinesJson);
    }

    public String getFavoriteAirlinesJson()
    {
        return mFavoriteAirlinesJson;
    }

    private void commit(String key, String value)
    {
        SharedPreferences.Editor editor = getEditor();
        editor.putString(key, value);
        editor.commit();
    }

    private SharedPreferences.Editor getEditor()
    {
        SharedPreferences settings = mContext.getSharedPreferences(STORAGE_FILE, Context.MODE_PRIVATE);
        return settings.edit();
    }

}
