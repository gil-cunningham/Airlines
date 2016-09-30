package com.example.gilcunningham.airlines.helper;

import android.util.Log;
import android.webkit.URLUtil;

import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.network.AirlinesHttp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gil.cunningham on 9/20/2016.
 * Helper class for parsing airlines JSON and returning ArrayList of AirlineInfo
 */
public class AirlinesParser {

    public static final String TAG = AirlinesParser.class.getSimpleName();

    public static final String LOGO = "logoURL";
    public static final String NAME = "name";
    public static final String WEBSITE = "site";
    public static final String PHONE_NUM = "phone";
    public static final String CODE = "code";

    private AirlinesParser() {}

    public static ArrayList<AirlineInfo> parseAirlinesForResult(String json)
    {
        ArrayList<AirlineInfo> airlines = new ArrayList<AirlineInfo>();

        if (json != null && !json.equals("")) {

            try {

                JSONArray airlinesArray = new JSONArray(json);

                for (int i = 0; i < airlinesArray.length(); i++) {
                    JSONObject airline = airlinesArray.getJSONObject(i);

                    AirlineInfo.Builder builder = AirlineInfo.newBuilder();
                    String logo = airline.getString(LOGO);
                    builder.setLogo((URLUtil.isValidUrl(logo) ? logo : AirlinesHttp.API_BASE_URL + logo));
                    builder.setName(airline.getString(NAME));
                    builder.setWebSite(airline.getString(WEBSITE));
                    builder.setPhoneNumber(airline.getString(PHONE_NUM));
                    builder.setCode(airline.getString(CODE));

                    airlines.add(builder.build());
                }

            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return airlines;
    }
}
