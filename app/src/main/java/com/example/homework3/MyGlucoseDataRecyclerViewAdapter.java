package com.example.homework3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.homework3.GlucoseDataFragment.OnListFragmentInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GlucoseData} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGlucoseDataRecyclerViewAdapter extends RecyclerView.Adapter<MyGlucoseDataRecyclerViewAdapter.ViewHolder> {

    private final List<GlucoseData> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGlucoseDataRecyclerViewAdapter(List<GlucoseData> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_glucosedata, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.entryDateView.setText(mValues.get(position).getEntryDate());
        holder.averageGlucoseView.setText(String.valueOf(mValues.get(position).getAverage()));
        holder.isNormalView.setChecked(mValues.get(position).isNormal());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView averageGlucoseView;
        public final TextView entryDateView;
        public GlucoseData mItem;
        public final CheckBox isNormalView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            entryDateView = (TextView) view.findViewById(R.id.entryDateItem);
            averageGlucoseView = (TextView) view.findViewById(R.id.averageGlucoseItem);
            isNormalView = (CheckBox) view.findViewById(R.id.normalCheckBoxItem);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + averageGlucoseView.getText() + "'";
        }
    }
}
