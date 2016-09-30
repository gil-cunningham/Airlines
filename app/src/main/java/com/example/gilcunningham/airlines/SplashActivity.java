package com.example.gilcunningham.airlines;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.service.AirlinesServiceCallback;
import com.example.gilcunningham.airlines.service.AirlinesServiceVolley;

import java.util.ArrayList;

/**
 * Created by gil.cunningham on 9/19/2016.
 * Displays "splash" screen while data set is retrieved
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        // call to fetch list of airlines
        AirlinesServiceVolley.getService(this).getAirlinesList(new AirlinesServiceCallback() {
            @Override
            public void onAirlinesServiceCallback(@Nullable ArrayList<AirlineInfo> airlines) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putParcelableArrayListExtra(MainActivity.AIRLINES_EXTRA, airlines);
                startActivity(i);

                finish();
            }
        });
    }
}
