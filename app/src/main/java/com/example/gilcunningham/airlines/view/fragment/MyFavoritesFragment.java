package com.example.gilcunningham.airlines.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gilcunningham.airlines.R;
import com.example.gilcunningham.airlines.data.AirlineInfo;

import java.util.List;

/**
 * Created by gil.cunningham on 9/22/2016.
 * Displays list of favorite airlines.
 */
public class MyFavoritesFragment extends AirlinesListFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        List<AirlineInfo> favAirlines = getDataSet();

        if (favAirlines != null && !favAirlines.isEmpty()) {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

        return inflater.inflate(R.layout.empty_list, container, false);

    }
}
