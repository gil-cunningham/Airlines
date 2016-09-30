package com.example.gilcunningham.airlines.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gilcunningham.airlines.AirlineDetailsActivity;
import com.example.gilcunningham.airlines.MainActivity;
import com.example.gilcunningham.airlines.R;
import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.view.AirlineListDivider;
import com.example.gilcunningham.airlines.view.adapter.AirlinesAdapter;
import com.example.gilcunningham.airlines.view.listener.RecyclerViewTouchListener;

import java.util.List;

/**
 * Created by gil.cunningham on 9/24/2016.
 * Abstract for handling work of display list of airlines
 */
public abstract class AirlinesListFragment extends Fragment {

    private RecyclerView mAirlinesRecyclerView;
    private AirlinesAdapter mAirlinesAdapter;

    private List<AirlineInfo> mAirlines;

    @Override
    public void setArguments(Bundle args) {
        List<AirlineInfo> airlines = args.getParcelableArrayList(MainActivity.AIRLINES_EXTRA);
        mAirlines = airlines;

        // notify adapter dataset changed
        if (mAirlinesAdapter != null) {
            mAirlinesAdapter.notifyDataSetChanged();
        }
    }

    protected List<AirlineInfo> getDataSet()
    {
        return mAirlines;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mAirlinesRecyclerView = (RecyclerView)inflater.inflate(R.layout.airlines_recycler_view, container, false);

        final Context ctx = mAirlinesRecyclerView.getContext();
        mAirlinesAdapter = new AirlinesAdapter(ctx, mAirlines);
        RecyclerView.LayoutManager layoutMgr = new LinearLayoutManager(ctx);

        mAirlinesRecyclerView.setHasFixedSize(false);
        mAirlinesRecyclerView.setLayoutManager(layoutMgr);
        // adds divider between card views
        mAirlinesRecyclerView.addItemDecoration(new AirlineListDivider(ctx));
        mAirlinesRecyclerView.setAdapter(mAirlinesAdapter);

        // on airline tap/click, load details for airline
        mAirlinesRecyclerView.addOnItemTouchListener(new RecyclerViewTouchListener(ctx,
                mAirlinesRecyclerView, new RecyclerViewTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AirlineInfo info = mAirlines.get(position);

                Intent i = new Intent(ctx, AirlineDetailsActivity.class);
                i.putExtra(AirlineDetailsActivity.AIRLINE_DETAILS_EXTRA, info);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        return mAirlinesRecyclerView;
    }
}
