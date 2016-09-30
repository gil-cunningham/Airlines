package com.example.gilcunningham.airlines;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.data.FavoriteAirlineInfo;
import com.squareup.picasso.Picasso;

/**
 * Created by gil.cunningham on 9/21/2016.
 * 
 */
public class AirlineDetailsActivity extends AppCompatActivity {

    private static String TAG = AirlineDetailsActivity.class.getSimpleName();

    public static final String AIRLINE_DETAILS_EXTRA = "AIRLINE_DETAILS_EXTRA";

    private AirlineInfo mAirlineInfo = null;
    private FavoriteAirlineInfo mFavoriteAirlineInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        mAirlineInfo = (AirlineInfo)getIntent().getParcelableExtra(AIRLINE_DETAILS_EXTRA);

        mFavoriteAirlineInfo = FavoriteAirlineInfo.getInstance(this);

        doActivityLayout();
    }

    private void doActivityLayout() {
        setContentView(R.layout.activity_airline_details);

        ImageView airlineDetails = (ImageView)findViewById(R.id.airline_details_header);
        Picasso.with(this)
                .load(mAirlineInfo.getLogo())
                .error(R.drawable.airlines_icon)
                .into(airlineDetails);

        ImageView toolbarHeader = (ImageView)findViewById(R.id.toolbar_header_img);
        Picasso.with(this)
                .load(mAirlineInfo.getLogo())
                .error(R.drawable.airlines_icon)
                .into(toolbarHeader);

        // set name
        TextView airlineName = (TextView)findViewById(R.id.airline_name);
        airlineName.setText(mAirlineInfo.getName());

        // set website
        TextView airlineWebsite = (TextView)findViewById(R.id.airline_website);
        String webSite = mAirlineInfo.getWebSite();
        webSite = (webSite == null || webSite.isEmpty()) ?
                getResources().getString(R.string.airline_website_none) :
                webSite;

        airlineWebsite.setText(webSite);
        Linkify.addLinks(airlineWebsite, Linkify.WEB_URLS);

        TextView airlinePhoneNum = (TextView)findViewById(R.id.airline_phonenum);
        String phoneNum = mAirlineInfo.getPhoneNumber();
        phoneNum = (phoneNum == null || phoneNum.isEmpty()) ?
                getResources().getString(R.string.airline_phonenum_none) :
                phoneNum;

        airlinePhoneNum.setText(phoneNum);
        Linkify.addLinks(airlinePhoneNum, Linkify.PHONE_NUMBERS);

        CheckBox airlineFavorite = (CheckBox)findViewById(R.id.airline_favorite);
        final boolean isFavorite = mFavoriteAirlineInfo.isFavorite(mAirlineInfo);
        airlineFavorite.setChecked(isFavorite);

        airlineFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b != mFavoriteAirlineInfo.isFavorite(mAirlineInfo)) {
                    mFavoriteAirlineInfo.setFavorite(mAirlineInfo, b);
                }
            }
        });
    }

    public void doFinish(View view)
    {
        finish();
    }

}
