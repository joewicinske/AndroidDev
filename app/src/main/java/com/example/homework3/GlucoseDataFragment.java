package com.example.homework3;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.homework3.dummy.DummyContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class GlucoseDataFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<GlucoseData> glucoseData;
    private CustomPageAdapter adapter;
    private Context context;
    private DBHelper dbHelper;
    //private MyGlucoseDataRecyclerViewAdapter adapter = new MyGlucoseDataRecyclerViewAdapter(glucoseData, mListener);;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GlucoseDataFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static GlucoseDataFragment newInstance(int columnCount) {
        GlucoseDataFragment fragment = new GlucoseDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glucosedata_list, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        // Set the adapter
        context = view.getContext();
        glucoseData = dbHelper.getAllData();
        adapter = new CustomPageAdapter(context, glucoseData );
        viewPager.setAdapter(adapter);
        return view;
    }

    public void updateList(){
        glucoseData = dbHelper.getAllData();
        Log.d(this.getClass().getSimpleName(),"List has been updated");
        if (adapter != null){
            adapter.notifyDataSetChanged();
            Log.d(this.getClass().getSimpleName(),"List has been notified");

        }
    }


    public List<GlucoseData> getList(){
        return glucoseData;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(GlucoseData item);
    }
}
