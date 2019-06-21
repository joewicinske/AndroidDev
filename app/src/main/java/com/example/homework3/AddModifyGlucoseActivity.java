package com.example.homework3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * View Pager Addition . V1.1
 */
public class AddModifyGlucoseActivity extends AppCompatActivity implements GlucoseDataFragment.OnListFragmentInteractionListener {

    private EditText fastingInput, breakfastInput, lunchInput, dinnerInput, notesInput;
    private TextView resultsView, dateLabel;
    private Button clearButton, historyButton, submitButton;
    private CheckBox normalCheckBox;
    private DBHelper dbHelper;
    private GlucoseDataFragment myFrag = new GlucoseDataFragment();
    private String[] notes = new String[] {"", "", "", ""};
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String TAG = this.getClass().getSimpleName();
    private String addTitle = "Add Glucose Information";
    private String deleteTitle = "Delete Glucose Information";
    private Toolbar mTopToolbar;
    private boolean deleteData = false;
    private Bundle b;
    private GlucoseData incomingGlucoseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        b = getIntent().getExtras();
        if ( b != null) {
            Object obj = b.getSerializable("glucoseItem");
            if (obj != null){
                incomingGlucoseData = (GlucoseData) obj;
                deleteData = true;
            }else {
                deleteData = false;
                Log.e(getClass().getName(), "Error in receiving object");
            }
        }else {
            // assume we are just adding a blank glucuose Item
            deleteData = false;
        }
        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        getSupportActionBar().setTitle(deleteData ? deleteTitle : addTitle);
        initializeViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (deleteData && item.getItemId() == R.id.delete) {
            dbHelper.deleteGlucose(incomingGlucoseData.getId());
            Toast.makeText(this, "Item has been deleted", Toast.LENGTH_SHORT).show();
            getHistory();
        } else if (deleteData && item.getItemId() == R.id.upload){
            Toast.makeText(this, "Item has been uploaded", Toast.LENGTH_SHORT).show();
        }
            return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (deleteData) getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    private void initializeViews() {
        resultsView = (TextView) findViewById(R.id.resultLabel);
        fastingInput = (EditText) findViewById(R.id.fastingInput);
        View.OnFocusChangeListener  focusChangeListener =new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()){
                    case R.id.fastingInput:
                        if (!hasFocus && !fastingInput.getText().toString().isEmpty()){
                            // user just entered or modified a value
                            int fastingNum = Integer.parseInt(fastingInput.getText().toString());
                            notes[0] = evaluateResult("Fasting:", fastingNum);
                            rewriteNotes();
                        }else if (!hasFocus && fastingInput.getText().toString().isEmpty()) {
                    notes[0] = "";
                         }

                        break;
                    case R.id.lunchInput:
                        if (!hasFocus && !lunchInput.getText().toString().isEmpty()){
                            int lunchNum = Integer.parseInt(lunchInput.getText().toString());
                            notes[1] = evaluateResult("Lunch:", lunchNum);
                            rewriteNotes();
                        }else if (!hasFocus && lunchInput.getText().toString().isEmpty()) {
                    notes[1] = "";
                        }
                        break;
                    case R.id.dinnerInput:
                        if (!hasFocus && !dinnerInput.getText().toString().isEmpty()){
                            int dinnerNum = Integer.parseInt(dinnerInput.getText().toString());
                            notes[2] = evaluateResult("Dinner:", dinnerNum);
                            rewriteNotes();

                        }else if (!hasFocus && dinnerInput.getText().toString().isEmpty()) {
                            notes[2] = "";
                        }
                        break;
                    case R.id.breakfastInput:
                        if (!hasFocus && !breakfastInput.getText().toString().isEmpty()){
                            int breakfastNum = Integer.parseInt(breakfastInput.getText().toString());
                            notes[3] = evaluateResult("Breakfast:", breakfastNum);
                            rewriteNotes();

                        }else if (!hasFocus && breakfastInput.getText().toString().isEmpty()){
                            notes[3] = "";
                        }
                        break;
                    default:
                        Log.e(this.getClass().getSimpleName(), "Error in Actions of Buttons");
                }
            }
        };

        breakfastInput = (EditText) findViewById(R.id.breakfastInput);
        lunchInput = (EditText) findViewById(R.id.lunchInput);
        dinnerInput = (EditText) findViewById(R.id.dinnerInput);
        notesInput = (EditText) findViewById(R.id.notesInput);

        lunchInput.setOnFocusChangeListener(focusChangeListener);
        dinnerInput.setOnFocusChangeListener(focusChangeListener);
        breakfastInput.setOnFocusChangeListener(focusChangeListener);
        fastingInput.setOnFocusChangeListener(focusChangeListener);

        clearButton = (Button) findViewById(R.id.clearButton);
        historyButton = (Button) findViewById(R.id.historyButton);
        normalCheckBox = (CheckBox) findViewById(R.id.normalCheckBoxItem);
        dateLabel = (TextView) findViewById(R.id.dateLabel);
       // dateLabel.setText(getDateLabelTxt(new Date()));
        dateLabel.setText("Select a Date:");
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar cal =   Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                cal.set(Calendar.MONTH, month);
                dateLabel.setText(GlucoseData.getDateLabelTxt(cal.getTime()));
            }
        };

        dateLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int day = cal.get(Calendar.DAY_OF_MONTH);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddModifyGlucoseActivity.this, android.R.style.Theme_Black, dateSetListener, year, month ,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        submitButton = (Button) findViewById(R.id.submitButton);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.clearButton:
                        clearInputs();
                        break;
                    case R.id.historyButton:
                        getHistory();
                        break;
                    case R.id.submitButton:
                        submitData();
                        break;
                    default:
                        Log.e(this.getClass().getSimpleName(), "Error in Actions of Buttons");
                }
            }
        };

        if (deleteData) {
            breakfastInput.setEnabled(false);
            dinnerInput.setEnabled(false);
            lunchInput.setEnabled(false);
            fastingInput.setEnabled(false);
            notesInput.setEnabled(false);
            dateLabel.setEnabled(false);
            breakfastInput.setText(String.valueOf(incomingGlucoseData.getBreakfast()));
            fastingInput.setText(String.valueOf(incomingGlucoseData.getFasting()));
            lunchInput.setText(String.valueOf(incomingGlucoseData.getLunch()));
            dinnerInput.setText(String.valueOf(incomingGlucoseData.getDinner()));
            notesInput.setText(String.valueOf(incomingGlucoseData.getNotes()));
            dateLabel.setText(incomingGlucoseData.getEntryDate());
            normalCheckBox.setChecked(incomingGlucoseData.isNormal());
            submitButton.setVisibility(View.GONE);
        }else {
            clearButton.setOnClickListener(listener);
            historyButton.setOnClickListener(listener);
            submitButton.setOnClickListener(listener);
        }

    }

    private String evaluateResult(String s, int glucoseLevel) {
        if (glucoseLevel < 70){
            return String.format("[%s] HYPOGLYCEMIC", s);
        }else if(glucoseLevel >= 70 && glucoseLevel < 100){
            return String.format("[%s] NORMAL", s);
        }else{
            return String.format("[%s] ABNORMAL", s);
        }
    }

    private void rewriteNotes() {
        String newNote ="";
        for (String  note: notes){
            newNote += note;
        }
        resultsView.setText(newNote);
        String fastingText = fastingInput.getText().toString();
        String breakfastText = breakfastInput.getText().toString();
        String lunchText = lunchInput.getText().toString();
        String dinnerText = dinnerInput.getText().toString();
        String notesText = notesInput.getText().toString();
        String entryDateText = dateLabel.getText().toString();
        int fastingNum = Integer.parseInt(fastingText.isEmpty() ? "0": fastingText);
        int breakfastNum = Integer.parseInt(breakfastText.isEmpty() ? "0": breakfastText);
        int lunchNum = Integer.parseInt(lunchText.isEmpty() ? "0": lunchText);
        int dinnerNum = Integer.parseInt(dinnerText.isEmpty() ? "0": dinnerText);
        GlucoseData glucoseData  = new GlucoseData(fastingNum, breakfastNum, lunchNum, dinnerNum);
        normalCheckBox.setChecked(glucoseData.isNormal());
    }

    private void submitData() {
        String fastingText = fastingInput.getText().toString();
        String breakfastText = breakfastInput.getText().toString();
        String lunchText = lunchInput.getText().toString();
        String dinnerText = dinnerInput.getText().toString();
        String notesText = notesInput.getText().toString();
        String entryDateText = dateLabel.getText().toString();
        if (fastingText.isEmpty() || breakfastText.isEmpty() || lunchText.isEmpty() || entryDateText.isEmpty() || dinnerText.isEmpty()){
            Toast.makeText(this, "Please fill in all the data. Only Notes can be left empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        int fastingNum = Integer.parseInt(fastingText);
        int breakfastNum = Integer.parseInt(breakfastText);
        int lunchNum = Integer.parseInt(lunchText);
        int dinnerNum = Integer.parseInt(dinnerText);

        GlucoseData glucoseData = new GlucoseData();
        glucoseData.setBreakfast(breakfastNum);
        glucoseData.setLunch(lunchNum);
        glucoseData.setDinner(dinnerNum);
        glucoseData.setFasting(fastingNum);
        glucoseData.setEntryDate(entryDateText);
        glucoseData.setNotes(notesText);
        onListFragmentInteraction(glucoseData);
        clearInputs();
    }


    private void clearInputs() {
        resultsView.setText("");
        fastingInput.setText("");
        breakfastInput.setText("");
        lunchInput.setText("");
        dinnerInput.setText("");
        notesInput.setText("");
        normalCheckBox.setText("Normal");
        dateLabel.setText("Select a Date");
    }

    private void getHistory(){
        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onListFragmentInteraction(GlucoseData item) {
        if (item == null){
            System.err.println("Item is nlull");
        }else{
            dbHelper.insertGlucose(item);
            myFrag.updateList();
            getHistory();
        }

    }


    private class AsyncNetworkTask extends AsyncTask<GlucoseData, Void, String> {

        // url http://u.arizona.edu/~lxu/cscv381/local_glucose.php
        @Override
        protected String doInBackground(GlucoseData... dataItem) {
            GlucoseData item = dataItem [0];
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
