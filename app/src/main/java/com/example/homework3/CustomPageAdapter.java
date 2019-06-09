package com.example.homework3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.homework3.dummy.DummyContent;

import java.util.List;

public class CustomPageAdapter extends PagerAdapter {

    private Context mContext;
    private List<GlucoseData> glucoseData;


    public CustomPageAdapter(Context context, List<GlucoseData> data) {
        mContext = context;
        glucoseData = data;
    }

    @Override
    public int getCount() {
        return glucoseData.size();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        GlucoseData glucoseDataItem = glucoseData.get(position);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.fragment_glucosedata, container, false);
        TextView date = (TextView) layout.findViewById(R.id.entryDateItem);
        TextView averageGlucose = (TextView) layout.findViewById(R.id.averageGlucoseItem);
        CheckBox normalCheckBox = (CheckBox) layout.findViewById(R.id.normalCheckBoxItem);
        Log.d("CustomPageAdapter", glucoseDataItem.getEntryDate());
        date.setText((glucoseDataItem.getEntryDate()));
        averageGlucose.setText(String.valueOf(glucoseDataItem.getAverage()));
        normalCheckBox.setText("Normal");
        normalCheckBox.setChecked(glucoseDataItem.isNormal());
        container.addView(layout);
        return layout;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
}
