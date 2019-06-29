package com.example.homework3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListActivity extends AppCompatActivity implements MyGlucoseDataRecyclerViewAdapter.ItemClickListener {

    private MyGlucoseDataRecyclerViewAdapter adapter;
    private Toolbar mTopToolbar;
    private ArrayList<GlucoseData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        DBHelper dbHelper = new DBHelper(this);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(this, NotificationReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        data = dbHelper.getAllData();
        adapter = new MyGlucoseDataRecyclerViewAdapter(data);
        RecyclerView recyclerView = findViewById(R.id.recyclerListView);
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mTopToolbar = (Toolbar) findViewById(R.id.addToolBar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("List of data");
        boolean containsTodaysData = containsTodaysData(data);
        if (!containsTodaysData){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE, 1);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);
        }else{
            alarmManager.cancel(broadcast);
        }

    }

    private boolean containsTodaysData(ArrayList<GlucoseData> data) {
        String todaysDate = GlucoseData.getDateLabelTxt(new Date());
        for (GlucoseData glucoseData : data){
            if (glucoseData.getEntryDate().equals(todaysDate)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.cloud){
            Intent intent = new Intent(getApplicationContext(), WebActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        GlucoseData glucoseItem = data.get(position);
        intent.putExtra("glucoseItem", glucoseItem);
        startActivity(intent);
    }
}
