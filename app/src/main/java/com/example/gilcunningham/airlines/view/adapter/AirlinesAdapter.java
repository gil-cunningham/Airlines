package com.example.gilcunningham.airlines.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.gilcunningham.airlines.R;
import com.example.gilcunningham.airlines.data.AirlineInfo;
import com.example.gilcunningham.airlines.service.AirlinesServiceVolley;

import java.util.List;

/**
 * Created by gil.cunningham on 9/20/2016.
 * View Holder for airlines cardview
 */
public class AirlinesAdapter extends RecyclerView.Adapter<AirlinesAdapter.ViewHolder> {

    private static Integer LT_LT_GREY;

    private Context mContext;
    private List<AirlineInfo> mDataset;

    // Provide a reference to the views for each data item
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mAirlineName;
        public ImageView mAirlineIcon;

        public ViewHolder(View v) {
            super(v);

            mAirlineName = (TextView)v.findViewById(R.id.airline_name);
            mAirlineIcon = (ImageView)v.findViewById(R.id.airline_icon);
        }
    }

    public AirlinesAdapter(Context context, List<AirlineInfo> dataset) {
        mContext = context;
        mDataset = dataset;

        int c = R.color.airlines_listitem;

        if (LT_LT_GREY == null) {
            LT_LT_GREY = ContextCompat.getColor(mContext, R.color.airlines_listitem);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AirlinesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.airline_row, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        AirlineInfo ai = mDataset.get(position);

        holder.mAirlineName.setText(ai.getName());

        ImageLoader loader = AirlinesServiceVolley.getService(mContext).getImageLoader();
        loader.get(ai.getLogo(), new ImageLoader.ImageListener() {

            // display default logo
            @Override
            public void onErrorResponse(VolleyError error) {
                holder.mAirlineIcon.setImageResource(R.drawable.airlines_icon);
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.mAirlineIcon.setImageBitmap(response.getBitmap());
            }
        });
    }

    // Return the size of dataset
    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        }
        return 0;
    }
}
