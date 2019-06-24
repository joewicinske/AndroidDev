package com.example.homework3;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListActivity extends AppCompatActivity implements MyGlucoseDataRecyclerViewAdapter.ItemClickListener {

    private MyGlucoseDataRecyclerViewAdapter adapter;
    private Toolbar mTopToolbar;
    private ArrayList<GlucoseData> data;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        dbHelper = new DBHelper(this);
        data = dbHelper.getAllData();
        adapter = new MyGlucoseDataRecyclerViewAdapter(data);
        RecyclerView recyclerView = findViewById(R.id.recyclerListView);
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mTopToolbar = (Toolbar) findViewById(R.id.addToolBar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("List of data");
        startBroadcast();

    }

    private void startBroadcast() {
        // start notification
        // go to broadcast receiver
        // then you are forward to activity
        // Builds your notification
        if (!containsDailyGlucoseReads()){
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 22);
            cal.set(Calendar.MINUTE, 26);
            Intent i  = new Intent(getApplicationContext(), MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), 1000 * 60, pendingIntent);
        }

    }

    private boolean containsDailyGlucoseReads() {
        String todaysDate = GlucoseData.getDateLabelTxt(new Date());
        for(GlucoseData d: data){
            if (d.getEntryDate().equals(todaysDate)){
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add){
            Intent intent = new Intent(getApplicationContext(), AddModifyGlucoseActivity.class);
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
        Intent intent = new Intent(getApplicationContext(), AddModifyGlucoseActivity.class);
        GlucoseData glucoseItem = data.get(position);
        intent.putExtra("glucoseItem", glucoseItem);
        startActivity(intent);
    }
}
