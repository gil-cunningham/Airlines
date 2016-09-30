package com.example.gilcunningham.airlines.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.gilcunningham.airlines.helper.AirlinesStorage;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Client for making calls Kayak REST service
 * Stores successful responses due to String size restrictions in passed Intent
 */
public class AirlinesRestClient extends AsyncTask<String, Void, String> {

        private static final String TAG = "AirlinesRestClient";

        public static final String BAD_RESPONSE = "BadResponse";
        public static final String OK_RESPONSE = "OkReponse";

        /** Intents for broadcast receivers **/
        public static final String AIRLINES_CALLBACK_INTENT = "AIRLINES_CALLBACK_INTENT";

        // HTTP response Intent extra
        public static final String AIRLINES_JSON_EXTRA = "AIRLINES_JSON_EXTRA";

        private Context mContext;

        public AirlinesRestClient(Context context)
        {
            mContext = context;
        }

        public void doGetAirlinesList() {

            HttpUrl.Builder urlBuilder = HttpUrl.parse(AirlinesHttp.API_BASE_URL).newBuilder();
            urlBuilder.addPathSegment(AirlinesHttp.API_AIRLINES_PATH);
            String url = urlBuilder.build().toString();

            execute(url);
        }

        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .build();

            try (Response response = okClient.newCall(request).execute()) {
                return response.body().string();
            } catch (Exception ioe) {
                Log.e(TAG, ioe.getMessage());
            }

            return BAD_RESPONSE;
        }

        @Override
        protected void onPostExecute(String res)
        {
            Intent i = new Intent();
            i.setAction(AirlinesRestClient.AIRLINES_CALLBACK_INTENT);
            i.putExtra(AIRLINES_JSON_EXTRA, BAD_RESPONSE);

            // response String can be larger than supported by Intent
            // to be safe, use SharedPreferences to persist list
            if (!res.equals(BAD_RESPONSE)) {
                AirlinesStorage.getStorage(mContext).setAirlinesJson(res);
                i.putExtra(AIRLINES_JSON_EXTRA, OK_RESPONSE);
            }

            // broadcast the completion
            mContext.sendBroadcast(i);
        }
}
