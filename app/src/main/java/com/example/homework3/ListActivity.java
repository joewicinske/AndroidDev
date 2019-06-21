package com.example.homework3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements MyGlucoseDataRecyclerViewAdapter.ItemClickListener {

    private MyGlucoseDataRecyclerViewAdapter adapter;
    private Toolbar mTopToolbar;
    private ArrayList<GlucoseData> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        DBHelper dbHelper = new DBHelper(this);
        data = dbHelper.getAllData();
        adapter = new MyGlucoseDataRecyclerViewAdapter(data);
        RecyclerView recyclerView = findViewById(R.id.recyclerListView);
        adapter.setClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        mTopToolbar = (Toolbar) findViewById(R.id.addToolBar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle("List of data");
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
